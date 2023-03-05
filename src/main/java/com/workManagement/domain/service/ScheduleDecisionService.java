package com.workManagement.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workManagement.common.Const;
import com.workManagement.common.logic.CmnScheduleLogic;
import com.workManagement.domain.model.bean.CmnScheduleCalendarBean;
import com.workManagement.domain.model.bean.CmnScheduleUserNameBean;
import com.workManagement.domain.model.bean.ScheduleDecisionBean;
import com.workManagement.domain.model.bean.ScheduleDecisionModifyBean;
import com.workManagement.domain.model.dto.ScheduleDayDto;
import com.workManagement.domain.model.dto.SchedulePreDayDto;
import com.workManagement.domain.model.entity.ScheduleEntity;
import com.workManagement.domain.model.entity.ScheduleTimeEntity;
import com.workManagement.domain.model.entity.UsersEntity;
import com.workManagement.domain.repository.ScheduleDayRepository;
import com.workManagement.domain.repository.SchedulePreDayRepository;
import com.workManagement.domain.repository.ScheduleRepository;
import com.workManagement.domain.repository.ScheduleTimeRepository;
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
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ScheduleTimeRepository scheduleTimeRepository;

	@Autowired
	private ScheduleDayRepository scheduleDayRepository;

	@Autowired
	private SchedulePreDayRepository schedulePreDayRepository;

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
		List<UsersEntity> userDbList = selectUserNotDelFlg();
		// 確定スケジュールに登録済みのユーザを除くユーザListに変換
		List<UsersEntity> usersList = calcUserListForNewScheduleRecorded(scheduleUserList, userDbList);

		//Beanにセット
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
	 * スケジュール登録済み判定List取得処理
	 *
	 * <p>
	 * scheduleEntityとscheduleTimeListから登録済みのスケジュールとスケジュール時間区分を取得し、登録されているかを判別する<br>
	 * Listのエレメント(Boolean[])には1日ごとのスケジュール時間区分で登録済みかを判別する
	 *
	 * @param scheduleEntity DBから取得したscheduleEntity
	 * @param scheduleTime DBから取得したScheduleTimeEntity
	 * @return Boolean[][]<br>
	 * エレメント(Boolean[][])<br>
	 * true: スケジュール登録済み, false: スケジュール未登録<br>
	 * ただし、要素はBoolean[日付(31固定)][スケジュール時間(スケジュール登録可能数)]
	 */
	private Boolean[][] calcIsScheduleRecordedArray(ScheduleEntity scheduleEntity, ScheduleTimeEntity scheduleTime) {

		// 確定スケジュール情報があるとき、対象のスケジュールを代入
		ScheduleEntity trimScheduleEntity = new ScheduleEntity();
		if (scheduleEntity != null) {
			trimScheduleEntity = scheduleEntity;
		}

		// スケジュール登録済みかを判定する共通Logicクラス
		CmnScheduleLogic cmnScheduleLogic = new CmnScheduleLogic();

		// スケジュール登録を判別するBoolean[日付][スケジュール時間]の配列
		Boolean[][] isScheduleRecordedArray = new Boolean[31][Const.SCHEDULE_RECORDABLE_MAX_DIVISION];

		// 確定スケジュールをListで取得
		List<String> scheduleList = trimScheduleEntity.scheduleFormatScheduleDayList();

		// 確定スケジュールだけループする
		for (int i = 0; i < scheduleList.size(); i++) {

			// 1日ごとのスケジュールを判別し、配列で取得
			Boolean[] isScheduleArray = cmnScheduleLogic.toIsScheduleArray(scheduleList.get(i), scheduleTime);

			// 該当の日付に判別したスケジュールをセットする
			isScheduleRecordedArray[i] = isScheduleArray;
		}
		return isScheduleRecordedArray;
	}


	/**
	 * [Repository] 確定スケジュール検索処理
	 *
	 * <p>
	 * 年月とユーザIDと一致する確定スケジュールを取得する<br>
	 * ただし、該当のスケジュールがない場合はnullとなる
	 *
	 * @param ym 取得したいスケジュールの年月
	 * @param user 取得したいユーザのユーザID
	 * @return ScheduleEntity 確定スケジュール
	 */
	private ScheduleEntity selectSchedule(String ym, String user) {
		return scheduleRepository.selectSchedule(ym, user);
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
}
