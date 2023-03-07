package com.workManagement.common.logic;

import java.util.List;

import com.workManagement.domain.model.bean.collection.ScheduleTimeBean;
import com.workManagement.domain.model.entity.ScheduleTimeEntity;

/**
 * @author saito
 *
 */
public class CmnScheduleTimeLogic {


	/**
	 * スケジュール時間区分勤務時間変換処理
	 *
	 * <p>
	 * スケジュールとスケジュール時間区分からそれぞれのスケジュール時間区分の勤務時間を算出し、Longの配列に格納して返す<br>
	 * それぞれのスケジュール時間区分の勤務時間は"勤務終了時間 - 勤務開始時間 - 休憩時間"で計算される<br>
	 * ただし、スケジュールまたはスケジュール時間区分が1つも登録されていないときは0が返される
	 *
	 * @param scheduleTimeEntity スケジュール時間区分<br>
	 * ただし、nullまたはスケジュール時間区分が1つも登録されていないときは0が返される
	 * @return Long[] それぞれのスケジュール時間区分の労働時間
	 */
	public Long[] calcWorkTimeMsArray(ScheduleTimeEntity scheduleTimeEntity) {

		// スケジュール時間区分がないとき
		if (scheduleTimeEntity == null) {

			// スケジュール時間区分の労働時間に0を格納し、返す
			Long[] workTime0MsArray = new Long[1];
			workTime0MsArray[0] = 0L;
			return workTime0MsArray;
		}

		// スケジュール時間区分をListで取得
		List<ScheduleTimeBean> scheduleTimeList = scheduleTimeEntity.scheduleTimeFormatList();

		// スケジュール時間区分が1つも登録されていないとき
		if (scheduleTimeList.isEmpty()) {

			// スケジュール時間区分の労働時間に0を格納し、返す
			Long[] workTime0MsArray = new Long[1];
			workTime0MsArray[0] = 0L;
			return workTime0MsArray;
		}

		// 共通Logicクラス
		CommonLogic commonLogic = new CommonLogic();

		// スケジュール時間区分ごとの労働時間(ミリ秒)を格納する配列
		Long[] workTimeMsArray = new Long[scheduleTimeList.size()];

		// スケジュール時間区分だけループする
		for (int i = 0; i < scheduleTimeList.size(); i++) {
			ScheduleTimeBean scheduleTimeBean = scheduleTimeList.get(i);

			// 登録されているそれぞれの時間をミリ秒で取得
			long startHmMs = commonLogic.chengeHmMsByHourMinutes(scheduleTimeBean.startHmFormatHour(), scheduleTimeBean.startHmFormatMinutes());
			long endHmMs = commonLogic.chengeHmMsByHourMinutes(scheduleTimeBean.endHmFormatHour(), scheduleTimeBean.endHmFormatMinutes());
			long restHmMs = commonLogic.chengeHmMsByHourMinutes(scheduleTimeBean.restHmFormatHour(), scheduleTimeBean.restHmFormatMinutes());

			// 勤務時間を算出し、該当のスケジュール時間区分に格納
			long workTimeMs = endHmMs - startHmMs - restHmMs;
			workTimeMsArray[i] = workTimeMs;
		}
		return workTimeMsArray;
	}
}
