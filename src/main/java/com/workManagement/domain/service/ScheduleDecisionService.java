package com.workManagement.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workManagement.common.Const;
import com.workManagement.domain.model.bean.CmnScheduleCalendarBean;
import com.workManagement.domain.model.bean.CmnScheduleUserNameBean;
import com.workManagement.domain.model.bean.ScheduleDecisionBean;
import com.workManagement.domain.model.bean.ScheduleDecisionModifyBean;
import com.workManagement.domain.model.bean.ScheduleDecisionModifyModifyBean;
import com.workManagement.domain.model.dto.ScheduleDayDto;
import com.workManagement.domain.model.dto.SchedulePreDayDto;
import com.workManagement.domain.model.entity.ScheduleEntity;
import com.workManagement.domain.model.entity.ScheduleTimeEntity;
import com.workManagement.domain.model.entity.UsersEntity;
import com.workManagement.domain.repository.ScheduleDayRepository;
import com.workManagement.domain.repository.SchedulePreDayRepository;
import com.workManagement.domain.repository.ScheduleRepository;
import com.workManagement.domain.repository.ScheduleTimeRepository;
import com.workManagement.domain.repository.UsersRepository;
import com.workManagement.domain.service.common.CmnScheduleCalendarService;
import com.workManagement.domain.service.common.CmnScheduleUserNameService;
import com.workManagement.form.ScheduleDecisionModifyForm;

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
	private ScheduleRepository scheduleRepository;

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
	 * [Service] 確定スケジュール修正画面 (/schedule-decision/modify)
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
	 * [Service] 確定スケジュール修正機能 (/schedule-decision/modify/modify)
	 *
	 * @param ym 年月
	 * @param day 日付
	 * @return ScheduleDecisionModifyBean
	 */
	public ScheduleDecisionModifyModifyBean scheduleDecisionModifyModify(ScheduleDecisionModifyForm scheduleDecisionModifyForm) {

		// 確定スケジュールを更新する
		boolean isUpdate = updateSchedule(scheduleDecisionModifyForm);
		// CmnScheduleCalendarServiceからカレンダー, 年月, 最終日を取得
		CmnScheduleCalendarBean cmnScheduleCalendarBean = cmnScheduleCalendarService.generateCalendarYmByYm(scheduleDecisionModifyForm.getYm());
		// 年, 月, 日をそれぞれ配列で取得
		String[] ymdArray = calcYmdArray(scheduleDecisionModifyForm.getYm(), scheduleDecisionModifyForm.getDay());
		// 1日分の予定スケジュールをユーザ毎に取得
		List<SchedulePreDayDto> schedulePreDayList = selectSchedulePreDay(scheduleDecisionModifyForm.getYm(), scheduleDecisionModifyForm.getDay());
		// 1日分の確定スケジュールをユーザ毎に取得
		List<ScheduleDayDto> scheduleUserList = selectScheduleDay(scheduleDecisionModifyForm.getYm(), scheduleDecisionModifyForm.getDay());
		// スケジュール時間区分を取得
		ScheduleTimeEntity scheduleTimeEntity = selectScheduleTime(cmnScheduleCalendarBean.getLastDateYmd());
		// 未退職ユーザを全て取得
		List<UsersEntity> usersDbList = selectUsersNotDelFlg();
		// 確定スケジュールに新規登録可能ユーザのみに変換
		List<UsersEntity> usersList = calcUserRecordableSchedule(scheduleUserList, usersDbList);

		// Beanにセット
		ScheduleDecisionModifyModifyBean scheduleDecisionModifyBean = new ScheduleDecisionModifyModifyBean();
		scheduleDecisionModifyBean.setUpdate(isUpdate);
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


	/**
	 * [Repository] 確定スケジュール更新処理
	 *
	 * <p>
	 * scheduleDecisionModifyFormから確定スケジュールに新規でユーザとスケジュール及び更新するユーザとスケジュールを登録する<br>
	 * ただし、確定スケジュールに新規登録するユーザがいない(ユーザが指定されていないまたはスケジュールを登録していない)ときは登録されない<br>
	 * 既に確定スケジュールに登録済みのユーザはスケジュールが登録していないときでもその情報が登録される
	 *
	 * @param scheduleDecisionModifyForm Formクラス
	 * @return boolean 更新判定<br>
	 * true: 更新したとき<br>
	 * false: 更新に失敗したとき
	 */
	private boolean updateSchedule(ScheduleDecisionModifyForm scheduleDecisionModifyForm) {

		//--------------------------
		//新規確定スケジュール追加
		//--------------------------

		// 登録する日付を取得
		String ym = scheduleDecisionModifyForm.getYm();
		String day = String.format("%02d", Integer.parseInt(scheduleDecisionModifyForm.getDay()));

		// 新規登録するユーザIDとスケジュールを取得
		String addUser = scheduleDecisionModifyForm.getAddUserId();
		String[] addScheduleArray = scheduleDecisionModifyForm.getAddScheduleArray();

		// 登録するスケジュールがある(addScheduleArrayに1が含まれているかつaddUserIdが""でない)とき
		if (Arrays.asList(addScheduleArray).contains(Const.SCHEDULE_RECORDED) && !addUser.isEmpty()) {

			// 対象の年月から新規で登録するユーザの確定スケジュールを検索する
			ScheduleEntity addNewUserScheduleEntity = scheduleRepository.selectSchedule(ym, addUser);

			// 該当の年月に新規で登録するユーザが未登録のとき、値をインスタンス化して値をセットするする
			if (addNewUserScheduleEntity == null) {
				addNewUserScheduleEntity = new ScheduleEntity();
				addNewUserScheduleEntity.setYm(ym);
				addNewUserScheduleEntity.setUser(addUser);
			}

			// 新規で登録するユーザのスケジュール情報を取得
			String addSchedule = scheduleDecisionModifyForm.addScheduleArrayFormatString();

			// 登録する日付(day)に応じてスケジュール情報をセットする
			if ("01".equals(day)) {
				addNewUserScheduleEntity.setDay1(addSchedule);
			} else if ("02".equals(day)) {
				addNewUserScheduleEntity.setDay2(addSchedule);
			} else if ("03".equals(day)) {
				addNewUserScheduleEntity.setDay3(addSchedule);
			} else if ("04".equals(day)) {
				addNewUserScheduleEntity.setDay4(addSchedule);
			} else if ("05".equals(day)) {
				addNewUserScheduleEntity.setDay5(addSchedule);
			} else if ("06".equals(day)) {
				addNewUserScheduleEntity.setDay6(addSchedule);
			} else if ("07".equals(day)) {
				addNewUserScheduleEntity.setDay7(addSchedule);
			} else if ("08".equals(day)) {
				addNewUserScheduleEntity.setDay8(addSchedule);
			} else if ("09".equals(day)) {
				addNewUserScheduleEntity.setDay9(addSchedule);
			} else if ("10".equals(day)) {
				addNewUserScheduleEntity.setDay10(addSchedule);
			} else if ("11".equals(day)) {
				addNewUserScheduleEntity.setDay11(addSchedule);
			} else if ("12".equals(day)) {
				addNewUserScheduleEntity.setDay12(addSchedule);
			} else if ("13".equals(day)) {
				addNewUserScheduleEntity.setDay13(addSchedule);
			} else if ("14".equals(day)) {
				addNewUserScheduleEntity.setDay14(addSchedule);
			} else if ("15".equals(day)) {
				addNewUserScheduleEntity.setDay15(addSchedule);
			} else if ("16".equals(day)) {
				addNewUserScheduleEntity.setDay16(addSchedule);
			} else if ("17".equals(day)) {
				addNewUserScheduleEntity.setDay17(addSchedule);
			} else if ("18".equals(day)) {
				addNewUserScheduleEntity.setDay18(addSchedule);
			} else if ("19".equals(day)) {
				addNewUserScheduleEntity.setDay19(addSchedule);
			} else if ("20".equals(day)) {
				addNewUserScheduleEntity.setDay20(addSchedule);
			} else if ("21".equals(day)) {
				addNewUserScheduleEntity.setDay21(addSchedule);
			} else if ("22".equals(day)) {
				addNewUserScheduleEntity.setDay22(addSchedule);
			} else if ("23".equals(day)) {
				addNewUserScheduleEntity.setDay23(addSchedule);
			} else if ("24".equals(day)) {
				addNewUserScheduleEntity.setDay24(addSchedule);
			} else if ("25".equals(day)) {
				addNewUserScheduleEntity.setDay25(addSchedule);
			} else if ("26".equals(day)) {
				addNewUserScheduleEntity.setDay26(addSchedule);
			} else if ("27".equals(day)) {
				addNewUserScheduleEntity.setDay27(addSchedule);
			} else if ("28".equals(day)) {
				addNewUserScheduleEntity.setDay28(addSchedule);
			} else if ("29".equals(day)) {
				addNewUserScheduleEntity.setDay29(addSchedule);
			} else if ("30".equals(day)) {
				addNewUserScheduleEntity.setDay30(addSchedule);
			} else if ("31".equals(day)) {
				addNewUserScheduleEntity.setDay31(addSchedule);
			}

			// 新規で追加したスケジュールをDB更新
			scheduleRepository.save(addNewUserScheduleEntity);
		}


		//-------------------------
		// 登録済みスケジュール更新
		//-------------------------

		//既存で登録されたユーザと更新スケジュールを取得
		String[][] userArray2 = scheduleDecisionModifyForm.getUserArray();
		String[][] scheduleArray2 = scheduleDecisionModifyForm.getScheduleArray();

		// 該当する年月の全てのユーザの確定スケジュールをすべて取得
		List<ScheduleEntity> scheduleList = scheduleRepository.selectSchedule(ym);

		// 該当の年月に登録されているユーザだけループ
		for (int i = 0; i < scheduleList.size(); i++) {
			ScheduleEntity entity = scheduleList.get(i);

			for (int j = 0; j < userArray2.length; j++) {

				// 既に登録済みのユーザ取得
				String[] userArray = userArray2[j];

				// 更新するユーザが一致したとき
				if (userArray[0].equals(entity.getUser())) {

					// DBに更新するスケジュールを格納する
					String schedule = "";

					// 対象のユーザのスケジュール時間区分ごとの更新スケジュールを配列で所得
					String[] scheduleArray = scheduleArray2[j];

					// スケジュール時間区分ごとの更新スケジュールだけループ
					for (int k = 0; k < Const.SCHEDULE_RECORDABLE_MAX_DIVISION; k++) {

						if (scheduleArray.length <= k) {
							// スケジュール時間区分が存在しないとき、未登録情報を格納
							schedule += Const.SCHEDULE_NOT_RECORDED;
						} else if(!Const.SCHEDULE_RECORDED.equals(scheduleArray[k])) {
							// スケジュールが登録されていないとき、未登録情報を格納
							schedule += Const.SCHEDULE_NOT_RECORDED;
						} else {
							// スケジュールが登録されているとき、登録情報を格納
							schedule += Const.SCHEDULE_RECORDED;
						}
					}

					// 該当の日付のスケジュールを更新する
					if ("01".equals(day)) {
						entity.setDay1(schedule);
					} else if ("02".equals(day)) {
						entity.setDay2(schedule);
					} else if ("03".equals(day)) {
						entity.setDay3(schedule);
					} else if ("04".equals(day)) {
						entity.setDay4(schedule);
					} else if ("05".equals(day)) {
						entity.setDay5(schedule);
					} else if ("06".equals(day)) {
						entity.setDay6(schedule);
					} else if ("07".equals(day)) {
						entity.setDay7(schedule);
					} else if ("08".equals(day)) {
						entity.setDay8(schedule);
					} else if ("09".equals(day)) {
						entity.setDay9(schedule);
					} else if ("10".equals(day)) {
						entity.setDay10(schedule);
					} else if ("11".equals(day)) {
						entity.setDay11(schedule);
					} else if ("12".equals(day)) {
						entity.setDay12(schedule);
					} else if ("13".equals(day)) {
						entity.setDay13(schedule);
					} else if ("14".equals(day)) {
						entity.setDay14(schedule);
					} else if ("15".equals(day)) {
						entity.setDay15(schedule);
					} else if ("16".equals(day)) {
						entity.setDay16(schedule);
					} else if ("17".equals(day)) {
						entity.setDay17(schedule);
					} else if ("18".equals(day)) {
						entity.setDay18(schedule);
					} else if ("19".equals(day)) {
						entity.setDay19(schedule);
					} else if ("20".equals(day)) {
						entity.setDay20(schedule);
					} else if ("21".equals(day)) {
						entity.setDay21(schedule);
					} else if ("22".equals(day)) {
						entity.setDay22(schedule);
					} else if ("23".equals(day)) {
						entity.setDay23(schedule);
					} else if ("24".equals(day)) {
						entity.setDay24(schedule);
					} else if ("25".equals(day)) {
						entity.setDay25(schedule);
					} else if ("26".equals(day)) {
						entity.setDay26(schedule);
					} else if ("27".equals(day)) {
						entity.setDay27(schedule);
					} else if ("28".equals(day)) {
						entity.setDay28(schedule);
					} else if ("29".equals(day)) {
						entity.setDay29(schedule);
					} else if ("30".equals(day)) {
						entity.setDay30(schedule);
					} else if ("31".equals(day)) {
						entity.setDay31(schedule);
					}

					// 更新後のスケジュールをセットする
					scheduleList.set(i, entity);
				}
			}
		}

		// 更新した確定スケジュールを全てDB更新
		scheduleRepository.saveAll(scheduleList);
		return true;
	}
}
