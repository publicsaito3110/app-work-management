package com.workManagement.domain.model.bean.collection;

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
public class ScheduleTimeBean {

	private String name;

	private String startHm;

	private String endHm;

	private String restHm;


	/**
	 * startHm時間フォーマット変換処理<br>
	 * 時間フォーマット(HH:MM)に変換
	 *
	 * @param void
	 * @return String 時間フォーマット(HH:MM)
	 */
	public String startHmsFormatTime() {
		return changeHmFormatTime(startHm);
	}


	/**
	 * endHm時間フォーマット変換処理<br>
	 * 時間フォーマット(HH:MM)に変換
	 *
	 * @param void
	 * @return String 時間フォーマット(HH:MM)
	 */
	public String endHmsFormatTime() {
		return changeHmFormatTime(endHm);
	}


	/**
	 * restHm時間フォーマット変換処理<br>
	 * 時間フォーマット(HH:MM)に変換
	 *
	 * @param void
	 * @return String 時間フォーマット(HH:MM)
	 */
	public String restHmsFormatTime() {
		return changeHmFormatTime(restHm);
	}


	/**
	 * [private 共通処理] 時間フォーマット変換処理<br>
	 * 時間フォーマット(HH:MM)に変換
	 *
	 * @param hm 変換したい値
	 * @return String 時間フォーマット(HH:MM)
	 */
	private String changeHmFormatTime(String hm) {

		//フォーマットをhh:mmに変換し、返す
		return hm.substring(0, 2) + ":" + hm.substring(2, 4);
	}
}
