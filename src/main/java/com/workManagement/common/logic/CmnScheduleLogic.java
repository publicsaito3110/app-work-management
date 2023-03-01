package com.workManagement.common.logic;

import java.util.List;

import com.workManagement.common.Const;
import com.workManagement.domain.model.bean.collection.ScheduleTimeBean;
import com.workManagement.domain.model.entity.ScheduleTimeEntity;

/**
 * @author saito
 *
 */
public class CmnScheduleLogic {


	/**
	 * スケジュール時間区分ごとの登録済み判別Array処理
	 *
	 * <p>
	 * scheduleからスケジュール時間区分ごとのスケジュールが登録されているかどうかをBooleanの配列に変換して返す<br>
	 * ただし、スケジュール時間区分に登録可能な数だけ判定する<br>
	 * また、scheduleは一文字ずつ取得し、スケジュール時間区分ごとに登録されているか判定される
	 *
	 * @param schedule スケジュール<br>
	 * ただし、nullまたは文字数が登録可能なスケジュール時間区分の要素数と一致していないときはスケジュール未登録と判定される
	 * @param scheduleTimeEntity 対象となるスケジュール時間区分<br>
	 * ただし、nullまたはEmptyのときはBoolean[0]:falseのみが返される
	 * @return Booelan[] スケジュール登録済みか判定したBooleanの配列<br>
	 * true: スケジュール登録済み<br>
	 * false: スケジュール未登録
	 */
	public Boolean[] toIsScheduleArray(String schedule, ScheduleTimeEntity scheduleTimeEntity) {

		// スケジュールが登録されているかどうかを判別する配列(1日ごとのスケジュールにおいて要素0 -> scheduleTimeList(0), 要素1 -> scheduleTimeList(1)...)
		Boolean[] isScheduleArray = new Boolean[Const.SCHEDULE_RECORDABLE_MAX_DIVISION];

		// スケジュール時間区分がないとき
        if (scheduleTimeEntity == null) {

            // 要素[0]にfalseをセットし、返す
        	isScheduleArray[0] = false;
            return isScheduleArray;
        }

        // スケジュール時間区分をListで取得
        List<ScheduleTimeBean> ScheduleTimeList = scheduleTimeEntity.scheduleTimeFormatList();

    	//ScheduleTimeBeanListの要素数の回数だけループ
		for (int i = 0; i < ScheduleTimeList.size(); i++) {

			// スケジュールが存在していないとき
			if (schedule == null) {
				isScheduleArray[i] = false;
				continue;
			}

			//scheduleの文字数がiより小さい(1文字取得できない)とき
			if (schedule.length() <= i) {
				isScheduleArray[i] = false;
				continue;
			}

			//ループの回数から1文字だけ取得
			String scheduleChara = String.valueOf(schedule.charAt(i));

			if (Const.SCHEDULE_RECORDED.equals(scheduleChara)) {
				//スケジュールが登録されているときtrueを代入
				isScheduleArray[i] = true;
			} else {
				//スケジュールが(scheduleValueCharaが1でない)登録されていないとき
				isScheduleArray[i] = false;
			}
		}
        return isScheduleArray;
	}
}
