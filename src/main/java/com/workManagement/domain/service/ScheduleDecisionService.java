package com.workManagement.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workManagement.common.Const;
import com.workManagement.domain.model.bean.CmnScheduleCalendarBean;
import com.workManagement.domain.model.bean.CmnScheduleUserNameBean;
import com.workManagement.domain.model.bean.ScheduleDecisionBean;
import com.workManagement.domain.model.bean.ScheduleDecisionModifyBean;
import com.workManagement.domain.model.dto.ScheduleDayDto;
import com.workManagement.domain.model.dto.SchedulePreDayDto;
import com.workManagement.domain.model.entity.ScheduleTimeEntity;
import com.workManagement.domain.model.entity.UsersEntity;
import com.workManagement.domain.repository.ScheduleDayRepository;
import com.workManagement.domain.repository.SchedulePreDayRepository;
import com.workManagement.domain.repository.ScheduleTimeRepository;
import com.workManagement.domain.repository.UsersRepository;
import com.workManagement.domain.service.common.CmnScheduleCalendarService;
import com.workManagement.domain.service.common.CmnScheduleUserNameService;

/**
 * @author saito
 *
 */
@Service
@Transactional
public class ScheduleDecisionService extends BaseService {

	@Autowired
	private ScheduleTimeRepository scheduleTimeRepository;

	@Autowired
	private ScheduleDayRepository scheduleDayRepository;

	@Autowired
	private SchedulePreDayRepository schedulePreDayRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private CmnScheduleCalendarService cmnScheduleCalendarService;

	@Autowired
	private CmnScheduleUserNameService cmnScheduleUserNameService;


	/**
	 * [Service] 確定スケジュール表示機能画面 (/schedule-decision)
	 *
	 * @param ym 年月
	 * @return HomeBean
	 */
	public ScheduleDecisionBean scheduleDecision(String ym) {

		// CmnScheduleCalendarServiceからカレンダー, 年月, 最終日を取得
		CmnScheduleCalendarBean cmnScheduleCalendarBean = cmnScheduleCalendarService.generateCalendarYmByYm(ym);
		// CmnScheduleUserNameServiceから2次元配列の確定スケジュール, スケジュール時間区分を取得
		CmnScheduleUserNameBean cmnScheduleUserNameBean = cmnScheduleUserNameService.generateScheduleUserName(cmnScheduleCalendarBean.getYear(), cmnScheduleCalendarBean.getMonth(), cmnScheduleCalendarBean.getLastDateYmd());

		// Beanにセット
		ScheduleDecisionBean scheduleDecisionBean = new ScheduleDecisionBean();
		scheduleDecisionBean.setYear(cmnScheduleCalendarBean.getYear());
		scheduleDecisionBean.setMonth(cmnScheduleCalendarBean.getMonth());
		scheduleDecisionBean.setNowYm(cmnScheduleCalendarBean.getNowYm());
		scheduleDecisionBean.setCalendarList(cmnScheduleCalendarBean.getCalendarList());
		scheduleDecisionBean.setAfterYm(cmnScheduleCalendarBean.getAfterYm());
		scheduleDecisionBean.setBeforeYm(cmnScheduleCalendarBean.getBeforeYm());
		scheduleDecisionBean.setScheduleUserNameArray(cmnScheduleUserNameBean.getScheduleUserNameArray());
		scheduleDecisionBean.setScheduleTimeEntity(cmnScheduleUserNameBean.getScheduleTimeEntity());
		return scheduleDecisionBean;
	}


	/**
	 * [Service] 確定スケジュール修正画面 (/schedule-decision)
	 *
	 * @param ym 年月
	 * @param day 日付
	 * @return ScheduleDecisionModifyBean
	 */
	public ScheduleDecisionModifyBean scheduleDecisionModify(String ym, String day) {

		// CmnScheduleCalendarServiceからカレンダー, 年月, 最終日を取得
		CmnScheduleCalendarBean cmnScheduleCalendarBean = cmnScheduleCalendarService.generateCalendarYmByYm(ym);
		// 年, 月, 日をそれぞれ配列で取得
		String[] ymdArray = calcYmdArray(ym, day);
		// 1日分の予定スケジュールをユーザ毎に取得
		List<SchedulePreDayDto> schedulePreDayList = selectSchedulePreDay(ym, day);
		// 1日分の確定スケジュールをユーザ毎に取得
		List<ScheduleDayDto> scheduleUserList = selectScheduleDay(ym, day);
		// スケジュール時間区分を取得
		ScheduleTimeEntity scheduleTimeEntity = selectScheduleTime(cmnScheduleCalendarBean.getLastDateYmd());
		// 未退職ユーザを全て取得
		List<UsersEntity> usersDbList = selectUsersNotDelFlg();
		// 確定スケジュールに新規登録可能ユーザのみに変換
		List<UsersEntity> usersList = calcUserRecordableSchedule(scheduleUserList, usersDbList);

		// Beanにセット
		ScheduleDecisionModifyBean scheduleDecisionModifyBean = new ScheduleDecisionModifyBean();
		scheduleDecisionModifyBean.setYear(ymdArray[0]);
		scheduleDecisionModifyBean.setMonth(ymdArray[1]);
		scheduleDecisionModifyBean.setDay(ymdArray[2]);
		scheduleDecisionModifyBean.setSchedulePreDayList(schedulePreDayList);
		scheduleDecisionModifyBean.setScheduleDayList(scheduleUserList);
		scheduleDecisionModifyBean.setScheduleTimeEntity(scheduleTimeEntity);
		scheduleDecisionModifyBean.setUsersList(usersList);
		return scheduleDecisionModifyBean;
	}


	/**
	 * 年月日付取得処理
	 *
	 * <p>
	 * 年月と日からをそれぞれ配列で取得する
	 *
	 * @param ym 年月
	 * @param day 日
	 * @return String[] 年月日が格納された配列<br>
	 * (要素: [0] 年, [1] 月, [2] 日)
	 */
	private String[] calcYmdArray(String ym, String day) {

		// 年, 月, 日をそれぞれ取得し、配列に格納
		String year = ym.substring(0, 4);
		String month = ym.substring(4, 6);
		String trimDay = String.format("%02d", Integer.parseInt(day));
		String[] ymdArray = {year, month, trimDay};
		return ymdArray;
	}


	/**
	 * 確定スケジュール登録可能ユーザ取得処理
	 *
	 * <p>
	 * scheduleUserListに登録されているユーザを除く、登録可能ユーザをすべて取得する<br>
	 * ただし、scheduleUserListがEmpty(登録済みユーザがいない)ときは何もせずuserDbListを返す
	 *
	 * @param scheduleUserList ユーザ毎の対象の日付のスケジュール
	 * @param usersDbList 全てのユーザ
	 * @return List<UserEntity> スケジュールに新規登録可能ユーザ
	 */
	private List<UsersEntity> calcUserRecordableSchedule(List<ScheduleDayDto> scheduleUserList, List<UsersEntity> usersDbList) {

		// 登録可能ユーザがいないとき、何もせず返す
		if (scheduleUserList.isEmpty()) {
			return usersDbList;
		}

		// 確定スケジュールに登録されているユーザIDを取得する
		List<String> scheduleRecordedUserList = new ArrayList<>();

		//scheduleUserListの回数だけループし、scheduleUserListに登録されているユーザIDを格納する
		for (ScheduleDayDto scheduleDayDto: scheduleUserList) {
			scheduleRecordedUserList.add(scheduleDayDto.getUserId());
		}

		//登録可能ユーザを取得するためのList
		List<UsersEntity> usersList = new ArrayList<>();

		//userDbListの回数だけループする
		for (UsersEntity usersEntity: usersDbList) {

			// スケジュールに登録されていないとき、ユーザを追加する
			if (!scheduleRecordedUserList.contains(usersEntity.getId())) {
				usersList.add(usersEntity);
			}
		}
		return usersList;
	}


	/**
	 * [Repository] 指定日付予定スケジュール取得処理
	 *
	 * <p>
	 * 指定した年月及び日付に該当する予定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される<br>
	 * ym=200001,day=1のとき2000年1月1日, ym=200001,day=2のとき2000年1月2日...
	 *
	 * @param ym 検索したい年月
	 * @param day 検索したい日付
	 * @return List<SchedulePreDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	private List<SchedulePreDayDto> selectSchedulePreDay(String ym, String day) {

		// 検索したい文字と文字列目を指定するためにフォーマットを"%1______%"に整える
		String schedule = Const.CHARACTER_PERCENT + Const.SCHEDULE_RECORDED + Const.CHARACTER_PERCENT;
		String schedule1 = Const.CHARACTER_PERCENT + Const.SCHEDULE_RECORDED + "______" + Const.CHARACTER_PERCENT;
		String schedule2 = Const.CHARACTER_PERCENT + "_" + Const.SCHEDULE_RECORDED + "_____" + Const.CHARACTER_PERCENT;
		String schedule3 = Const.CHARACTER_PERCENT + "__" + Const.SCHEDULE_RECORDED + "____" + Const.CHARACTER_PERCENT;
		String schedule4 = Const.CHARACTER_PERCENT + "___" + Const.SCHEDULE_RECORDED + "___" + Const.CHARACTER_PERCENT;
		String schedule5 = Const.CHARACTER_PERCENT + "____" + Const.SCHEDULE_RECORDED + "__" + Const.CHARACTER_PERCENT;
		String schedule6 = Const.CHARACTER_PERCENT + "_____" + Const.SCHEDULE_RECORDED + "_" + Const.CHARACTER_PERCENT;
		String schedule7 = Const.CHARACTER_PERCENT + "______" + Const.SCHEDULE_RECORDED + Const.CHARACTER_PERCENT;

		// 日付(DD)に変換する
		String trimDay = String.format("%02d", Integer.parseInt(day));

		// 日付に応じて取得するスケジュールの日付を変える
		List<SchedulePreDayDto> schedulePreDayDtoList = new ArrayList<>();
		if ("01".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay1(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("02".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay2(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("03".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay3(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("04".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay4(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("05".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay5(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("06".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay6(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("07".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay7(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("08".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay8(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("09".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay9(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("10".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay10(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("11".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay11(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("12".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay12(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("13".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay13(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("14".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay14(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("15".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay15(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("16".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay16(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("17".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay17(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("18".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay18(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("19".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay19(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("20".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay20(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("21".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay21(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("22".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay22(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("23".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay23(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("24".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay24(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("25".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay25(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("26".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay26(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("27".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay27(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("28".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay28(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("29".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay29(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("30".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay30(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("31".equals(trimDay)) {
			schedulePreDayDtoList = schedulePreDayRepository.selectSchedulePreDay31(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		}
		return schedulePreDayDtoList;
	}


	/**
	 * [Repository] 指定日付確定スケジュール取得処理
	 *
	 * <p>
	 * 指定した年月及び日付に該当する確定スケジュールをユーザごとに取得する<br>
	 * ただし、登録済みのスケジュールが1つもないときはEmptyとなる<br>
	 * その日付に登録されたユーザのみ取得される<br>
	 * ym=200001,day=1のとき2000年1月1日, ym=200001,day=2のとき2000年1月2日...
	 *
	 * @param ym 検索したい年月
	 * @param day 検索したい日付
	 * @return List<ScheduleDayDto> ユーザ毎の対象の日付のスケジュール
	 */
	private List<ScheduleDayDto> selectScheduleDay(String ym, String day) {

		// 検索したい文字と文字列目を指定するためにフォーマットを"%1______%"に整える
		String schedule = Const.CHARACTER_PERCENT + Const.SCHEDULE_RECORDED + Const.CHARACTER_PERCENT;
		String schedule1 = Const.CHARACTER_PERCENT + Const.SCHEDULE_RECORDED + "______" + Const.CHARACTER_PERCENT;
		String schedule2 = Const.CHARACTER_PERCENT + "_" + Const.SCHEDULE_RECORDED + "_____" + Const.CHARACTER_PERCENT;
		String schedule3 = Const.CHARACTER_PERCENT + "__" + Const.SCHEDULE_RECORDED + "____" + Const.CHARACTER_PERCENT;
		String schedule4 = Const.CHARACTER_PERCENT + "___" + Const.SCHEDULE_RECORDED + "___" + Const.CHARACTER_PERCENT;
		String schedule5 = Const.CHARACTER_PERCENT + "____" + Const.SCHEDULE_RECORDED + "__" + Const.CHARACTER_PERCENT;
		String schedule6 = Const.CHARACTER_PERCENT + "_____" + Const.SCHEDULE_RECORDED + "_" + Const.CHARACTER_PERCENT;
		String schedule7 = Const.CHARACTER_PERCENT + "______" + Const.SCHEDULE_RECORDED + Const.CHARACTER_PERCENT;

		// 日付(DD)に変換する
		String trimDay = String.format("%02d", Integer.parseInt(day));

		// 日付に応じて取得するスケジュールの日付を変える
		List<ScheduleDayDto> ScheduleDayDtoList = new ArrayList<>();
		if ("01".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay1(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("02".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay2(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("03".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay3(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("04".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay4(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("05".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay5(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("06".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay6(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("07".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay7(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("08".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay8(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("09".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay9(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("10".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay10(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("11".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay11(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("12".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay12(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("13".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay13(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("14".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay14(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("15".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay15(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("16".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay16(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("17".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay17(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("18".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay18(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("19".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay19(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("20".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay20(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("21".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay21(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("22".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay22(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("23".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay23(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("24".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay24(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("25".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay25(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("26".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay26(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("27".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay27(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("28".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay28(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("29".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay29(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("30".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay30(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		} else if ("31".equals(trimDay)) {
			ScheduleDayDtoList = scheduleDayRepository.selectScheduleDay31(schedule, schedule1, schedule2, schedule3, schedule4, schedule5, schedule6, schedule7, Const.SCHEDULE_RECORDED, ym);
		}
		return ScheduleDayDtoList;
	}


	/**
	 * [Repository] スケジュール時間区分検索処理
	 *
	 * <p>
	 * 取得したい日付(ymd)から該当するスケジュール時間区分を取得する<br>
	 * また、現在日(ymd)に該当するスケジュール時間区分が複数登録されているときは最新のスケジュール時間区分が取得される<br>
	 * ただし、スケジュール時間区分が何も登録されていないときはnullとなる
	 *
	 * @param ymd 取得したいスケジュール時間区分の日付
	 * @return ScheduleTimeEntity スケジュール時間区分
	 */
	private ScheduleTimeEntity selectScheduleTime(String ymd) {
		return scheduleTimeRepository.selectScheduleTime(ymd);
	}


	/**
	 * [Repository] ユーザ検索処理
	 *
	 * <p>
	 * 未退職ユーザを全て取得する<br>
	 * ただし、該当するユーザーがいない場合はnullとなる
	 *
	 * @param delFlg 退職フラグ
	 * @return UsersEntity 全ての未退職ユーザ
	 */
	private List<UsersEntity> selectUsersNotDelFlg() {
		return usersRepository.selectUsersNotDelFlg(Const.USERS_DEL_FLG);
	}
}
