package com.workManagement.form;


import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.workManagement.common.Const;
import com.workManagement.domain.model.dto.ScheduleDayDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author saito
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDecisionModifyForm {

	@NotBlank(message = "入力値が不正です")
	@Pattern(regexp = Const.PATTERN_SCHEDULE_YM_INPUT, message = "入力値が不正です")
	@Length(min = Const.PATTERN_SCHEDULE_YM_LENGTH_MIN_INPUT, max = Const.PATTERN_SCHEDULE_YM_LENGTH_MAX_INPUT, message = "入力値が不正です")
	private String ym;

	@NotBlank(message = "入力値が不正です")
	@Pattern(regexp = Const.PATTERN_SCHEDULE_DAY_INPUT, message = "入力値が不正です")
	@Length(min = Const.PATTERN_SCHEDULE_DAY_LENGTH_MIN_INPUT, max = Const.PATTERN_SCHEDULE_DAY_LENGTH_MAX_INPUT, message = "入力値が不正です")
	private String day;

	private String[][] userArray;

	private String[][] scheduleArray;

	@Pattern(regexp = Const.PATTERN_SCHEDULE_USER_INPUT_OPTIONAL, message = "入力値が不正です")
	@Length(min = Const.PATTERN_SCHEDULE_USER_LENGTH_MIN_INPUT_OPTIONAL, max = Const.PATTERN_SCHEDULE_USER_LENGTH_MAX_INPUT_OPTIONAL, message = "入力値が不正です")
	private String addUserId;

	private String[] addScheduleArray = new String[Const.SCHEDULE_RECORDABLE_MAX_DIVISION];


	/**
	 * [Constractor] ScheduleDecisionModifyForm
	 *
	 * <p>
	 * 1日のスケジュール及びスケジュールに新規登録可能ユーザの値をセットする
	 *
	 * @param scheduleDayList 1日のスケジュール
	 * @param year 登録する年
	 * @param month 登録する月
	 * @param day 登録する日
	 * @return ScheduleDecisionModifyForm
	 */
	public ScheduleDecisionModifyForm(List<ScheduleDayDto> scheduleDayList, String year, String month, String day) {

		// 登録するスケジュールの年月をセット
		ym = year + month;
		this.day = day;

		// スケジュールが存在していないとき
		if (scheduleDayList == null || scheduleDayList.isEmpty()) {
			return;
		}

		// userArrayとscheduleArrayの要素数を確定スケジュールに登録済みのユーザ数で指定
		userArray = new String[scheduleDayList.size()][2];
		scheduleArray = new String[scheduleDayList.size()][Const.SCHEDULE_RECORDABLE_MAX_DIVISION];

		//--------------------------------------------------------------------
		//登録したスケジュール通りになるようにuserScheduleArrayに値を代入する
		//--------------------------------------------------------------------

		// 確定スケジュール登録済みユーザだけループする
		for (int i = 0; i < scheduleDayList.size(); i++) {

			// 確定スケジュールに登録済みのユーザ名とIDをそれぞれ格納
			userArray[i][0] = scheduleDayList.get(i).getUserId();
			userArray[i][1] = scheduleDayList.get(i).getUserName();

			// スケジュールが登録済みか判定した配列を取得
			Boolean[] isScheduleRecordedArray = scheduleDayList.get(i).scheduleFormatTFArray();

			// スケジュール登録済み判定したスケジュール情報だけループする
			for (int j = 0; j < isScheduleRecordedArray.length; j++) {
				boolean isScheduleRecorded = isScheduleRecordedArray[j];

				if (!isScheduleRecorded) {
					// スケジュールが登録されていないとき、未登録の情報を格納する
					scheduleArray[i][j] = Const.SCHEDULE_NOT_RECORDED;
					continue;
				} else {
					//スケジュールが登録されているとき、登録済みの情報を格納する
					scheduleArray[i][j] = Const.SCHEDULE_RECORDED;
				}
			}
		}
	}


	/**
	 * 新規スケジュール文字列変換処理
	 *
	 * <p>
	 * 新規スケジュールを配列から文字列へ返還する<br>
	 * ただし、スケジュール時間区分と同じ桁数となる
	 *
	 * @param void
	 * @return String 配列から文字列に変換された新規スケジュール
	 */
	public String addScheduleArrayFormatString() {

		// スケジュールを格納
		String schedule = "";

		// スケジュールの時間区分だけループ
		for (String val: addScheduleArray) {

			if (Const.SCHEDULE_RECORDED.equals(val)) {
				// スケジュールが登録されているとき、登録情報を格納
				schedule += Const.SCHEDULE_RECORDED;
			} else {
				// スケジュールが登録されていないとき、未登録情報を格納
				schedule += Const.SCHEDULE_NOT_RECORDED;
			}
		}
		return schedule;
	}
}
