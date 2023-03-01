package com.workManagement.domain.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.workManagement.common.logic.CommonLogic;
import com.workManagement.domain.model.bean.collection.ScheduleTimeBean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author saito
 *
 */
@Entity
@Table(name="schedule_time")
@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleTimeEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "end_ymd")
	private String endYmd;

	@Column(name = "name1")
	private String name1;

	@Column(name = "start_hm1")
	private String startHm1;

	@Column(name = "end_hm1")
	private String endHm1;

	@Column(name = "rest_hm1")
	private String restHm1;

	@Column(name = "name2")
	private String name2;

	@Column(name = "start_hm2")
	private String startHm2;

	@Column(name = "end_hm2")
	private String endHm2;

	@Column(name = "rest_hm2")
	private String restHm2;

	@Column(name = "name3")
	private String name3;

	@Column(name = "start_hm3")
	private String startHm3;

	@Column(name = "end_hm3")
	private String endHm3;

	@Column(name = "rest_hm3")
	private String restHm3;

	@Column(name = "name4")
	private String name4;

	@Column(name = "start_hm4")
	private String startHm4;

	@Column(name = "end_hm4")
	private String endHm4;

	@Column(name = "rest_hm4")
	private String restHm4;

	@Column(name = "name5")
	private String name5;

	@Column(name = "start_hm5")
	private String startHm5;

	@Column(name = "end_hm5")
	private String endHm5;

	@Column(name = "rest_hm5")
	private String restHm5;

	@Column(name = "name6")
	private String name6;

	@Column(name = "start_hm6")
	private String startHm6;

	@Column(name = "end_hm6")
	private String endHm6;

	@Column(name = "rest_hm6")
	private String restHm6;

	@Column(name = "name7")
	private String name7;

	@Column(name = "start_hm7")
	private String startHm7;

	@Column(name = "end_hm7")
	private String endHm7;

	@Column(name = "rest_hm7")
	private String restHm7;


	/**
	 * スケジュール時間区分List取得処理
	 *
	 * <p>
	 * スケジュール時間区分をスケジュール時間区分ごとにListに変換する<br>
	 * ただし、スケジュール時間区分が登録されていない場合はEmptyとなる
	 *
	 * @param void
	 * @return List<ScheduleTimeBean> スケジュール時間区ごとに格納されたList
	 *
	 */
	public List<ScheduleTimeBean> scheduleTimeFormatList() {

		// 共通Logicクラス
		CommonLogic commonLogic = new CommonLogic();

		// スケジュール時間区分をListで格納
		List<ScheduleTimeBean> ScheduleTimeBeanList = new ArrayList<>();

		// スケジュール時間区分1が登録されているとき、スケジュール時間区分1を格納する
		if (!commonLogic.changeEmptyByNull(name1).equals("") && !commonLogic.changeEmptyByNull(startHm1).equals("") && !commonLogic.changeEmptyByNull(endHm1).equals("") && !commonLogic.changeEmptyByNull(restHm1).equals("")) {
			ScheduleTimeBean ScheduleTimeBean = new ScheduleTimeBean(name1, startHm1, endHm1, restHm1);
			ScheduleTimeBeanList.add(ScheduleTimeBean);
		}

		//スケジュール時間区分2が登録されているとき、スケジュール時間区分2を格納する
		if (!commonLogic.changeEmptyByNull(name2).equals("") && !commonLogic.changeEmptyByNull(startHm2).equals("") && !commonLogic.changeEmptyByNull(endHm2).equals("") && !commonLogic.changeEmptyByNull(restHm2).equals("")) {
			ScheduleTimeBean ScheduleTimeBean = new ScheduleTimeBean(name2, startHm2, endHm2, restHm2);
			ScheduleTimeBeanList.add(ScheduleTimeBean);
		}

		//スケジュール時間区分3が登録されているとき、スケジュール時間区分3を格納する
		if (!commonLogic.changeEmptyByNull(name3).equals("") && !commonLogic.changeEmptyByNull(startHm3).equals("") && !commonLogic.changeEmptyByNull(endHm3).equals("") && !commonLogic.changeEmptyByNull(restHm3).equals("")) {
			ScheduleTimeBean ScheduleTimeBean = new ScheduleTimeBean(name3, startHm3, endHm3, restHm3);
			ScheduleTimeBeanList.add(ScheduleTimeBean);
		}

		//スケジュール時間区分4が登録されているとき、スケジュール時間区分4を格納する
		if (!commonLogic.changeEmptyByNull(name4).equals("") && !commonLogic.changeEmptyByNull(startHm4).equals("") && !commonLogic.changeEmptyByNull(endHm4).equals("") && !commonLogic.changeEmptyByNull(restHm4).equals("")) {
			ScheduleTimeBean ScheduleTimeBean = new ScheduleTimeBean(name4, startHm4, endHm4, restHm4);
			ScheduleTimeBeanList.add(ScheduleTimeBean);
		}

		//スケジュール時間区分5が登録されているとき、スケジュール時間区分5を格納する
		if (!commonLogic.changeEmptyByNull(name5).equals("") && !commonLogic.changeEmptyByNull(startHm5).equals("") && !commonLogic.changeEmptyByNull(endHm5).equals("") && !commonLogic.changeEmptyByNull(restHm5).equals("")) {
			ScheduleTimeBean ScheduleTimeBean = new ScheduleTimeBean(name5, startHm5, endHm5, restHm5);
			ScheduleTimeBeanList.add(ScheduleTimeBean);
		}

		//スケジュール時間区分6が登録されているとき、スケジュール時間区分6を格納する
		if (!commonLogic.changeEmptyByNull(name6).equals("") && !commonLogic.changeEmptyByNull(startHm6).equals("") && !commonLogic.changeEmptyByNull(endHm6).equals("") && !commonLogic.changeEmptyByNull(restHm6).equals("")) {
			ScheduleTimeBean ScheduleTimeBean = new ScheduleTimeBean(name6, startHm6, endHm6, restHm6);
			ScheduleTimeBeanList.add(ScheduleTimeBean);
		}

		//スケジュール時間区分7が登録されているとき、スケジュール時間区分7を格納する
		if (!commonLogic.changeEmptyByNull(name7).equals("") && !commonLogic.changeEmptyByNull(startHm7).equals("") && !commonLogic.changeEmptyByNull(endHm7).equals("") && !commonLogic.changeEmptyByNull(restHm7).equals("")) {
			ScheduleTimeBean ScheduleTimeBean = new ScheduleTimeBean(name7, startHm7, endHm7, restHm7);
			ScheduleTimeBeanList.add(ScheduleTimeBean);
		}
		return ScheduleTimeBeanList;
	}
}
