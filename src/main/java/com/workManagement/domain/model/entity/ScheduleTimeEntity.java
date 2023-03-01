package com.workManagement.domain.model.entity;

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
}
