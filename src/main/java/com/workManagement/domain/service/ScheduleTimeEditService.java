package com.workManagement.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workManagement.common.Const;
import com.workManagement.common.logic.CmnScheduleLogic;
import com.workManagement.domain.model.bean.CmnScheduleCalendarBean;
import com.workManagement.domain.model.bean.CmnScheduleUserNameBean;
import com.workManagement.domain.model.bean.HomeAllBean;
import com.workManagement.domain.model.bean.HomeBean;
import com.workManagement.domain.model.entity.ScheduleEntity;
import com.workManagement.domain.model.entity.ScheduleTimeEntity;
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
public class ScheduleTimeEditService extends BaseService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ScheduleTimeRepository scheduleTimeRepository;

	@Autowired
	private CmnScheduleCalendarService cmnScheduleCalendarService;

	@Autowired
	private CmnScheduleUserNameService cmnScheduleUserNameService;


	/**
	 * [Service] ホーム画面表示機能 (/home)
	 *
	 * @param ym 年月
	 * @param loginUser ログインしているユーザID
	 * @return HomeBean
	 */
	public HomeBean home(String ym, String loginUser) {

		// CmnScheduleCalendarServiceからカレンダー, 年月, 最終日を取得
		CmnScheduleCalendarBean cmnScheduleCalendarBean = cmnScheduleCalendarService.generateCalendarYmByYm(ym);
		// ログインユーザの1ヵ月分の確定スケジュールを取得
		ScheduleEntity scheduleEntity = selectSchedule(cmnScheduleCalendarBean.getNowYm(), loginUser);
		// スケジュール時間区分を取得
		ScheduleTimeEntity scheduleTimeEntity = selectScheduleTime(cmnScheduleCalendarBean.getLastDateYmd());
		// スケジュールから登録済みスケジュールの判定をBooleanの2次元配列で取得
		Boolean[][] isScheduleRecordedArray = calcIsScheduleRecordedArray(scheduleEntity, scheduleTimeEntity);

		// Beanにセット
		HomeBean homeBean = new HomeBean();
		homeBean.setYear(cmnScheduleCalendarBean.getYear());
		homeBean.setMonth(cmnScheduleCalendarBean.getMonth());
		homeBean.setNowYm(cmnScheduleCalendarBean.getNowYm());
		homeBean.setCalendarList(cmnScheduleCalendarBean.getCalendarList());
		homeBean.setIsScheduleRecordedArray(isScheduleRecordedArray);
		homeBean.setAfterYm(cmnScheduleCalendarBean.getAfterYm());
		homeBean.setBeforeYm(cmnScheduleCalendarBean.getBeforeYm());
		homeBean.setScheduleTimeEntity(scheduleTimeEntity);
		return homeBean;
	}


	/**
	 * [Service] 全体のスケジュール表示機能 (/home/all)
	 *
	 * @param ym 年月
	 * @return HomeBean
	 */
	public HomeAllBean homeAll(String ym) {

		// CmnScheduleCalendarServiceからカレンダー, 年月, 最終日を取得
		CmnScheduleCalendarBean cmnScheduleCalendarBean = cmnScheduleCalendarService.generateCalendarYmByYm(ym);
		// CmnScheduleUserNameServiceから2次元配列の確定スケジュール, スケジュール時間区分を取得
		CmnScheduleUserNameBean cmnScheduleUserNameBean = cmnScheduleUserNameService.generateScheduleUserName(cmnScheduleCalendarBean.getYear(), cmnScheduleCalendarBean.getMonth(), cmnScheduleCalendarBean.getLastDateYmd());

		// Beanにセット
		HomeAllBean homeAllBean = new HomeAllBean();
		homeAllBean.setYear(cmnScheduleCalendarBean.getYear());
		homeAllBean.setMonth(cmnScheduleCalendarBean.getMonth());
		homeAllBean.setNowYm(cmnScheduleCalendarBean.getNowYm());
		homeAllBean.setCalendarList(cmnScheduleCalendarBean.getCalendarList());
		homeAllBean.setAfterYm(cmnScheduleCalendarBean.getAfterYm());
		homeAllBean.setBeforeYm(cmnScheduleCalendarBean.getBeforeYm());
		homeAllBean.setScheduleUserNameArray(cmnScheduleUserNameBean.getScheduleUserNameArray());
		homeAllBean.setScheduleTimeEntity(cmnScheduleUserNameBean.getScheduleTimeEntity());
		return homeAllBean;
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
