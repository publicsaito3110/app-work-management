package com.workManagement.domain.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.workManagement.common.Const;

import lombok.Data;

/**
 * @author saito
 *
 */
@Entity
@Data
public class SchedulePreDayDto {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id", unique = true)
	private String userId;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "schedule1", unique = true)
	private String schedule1;

	@Column(name = "schedule2", unique = true)
	private String schedule2;

	@Column(name = "schedule3", unique = true)
	private String schedule3;

	@Column(name = "schedule4", unique = true)
	private String schedule4;

	@Column(name = "schedule5", unique = true)
	private String schedule5;

	@Column(name = "schedule6", unique = true)
	private String schedule6;

	@Column(name = "schedule7", unique = true)
	private String schedule7;


	/**
	 * スケジュール登録判定処理
	 *
	 * <p>
	 * スケジュールが登録済みか判定し、Booleanの配列に格納する
	 *
	 * @param void
	 * @return Boolean[] スケジュールが登録済みか判定した配列
	 */
	public Boolean[] scheduleFormatTFArray() {

		// スケジュールが登録済みか判定
		Boolean[] isScheduleArray = new Boolean[Const.SCHEDULE_RECORDABLE_MAX_DIVISION];
		isScheduleArray[0] = Const.SCHEDULE_RECORDED.equals(schedule1);
		isScheduleArray[1] = Const.SCHEDULE_RECORDED.equals(schedule2);
		isScheduleArray[2] = Const.SCHEDULE_RECORDED.equals(schedule3);
		isScheduleArray[3] = Const.SCHEDULE_RECORDED.equals(schedule4);
		isScheduleArray[4] = Const.SCHEDULE_RECORDED.equals(schedule5);
		isScheduleArray[5] = Const.SCHEDULE_RECORDED.equals(schedule6);
		isScheduleArray[6] = Const.SCHEDULE_RECORDED.equals(schedule7);
		return isScheduleArray;
	}
}
