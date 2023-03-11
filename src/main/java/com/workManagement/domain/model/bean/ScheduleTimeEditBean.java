package com.workManagement.domain.model.bean;

import java.util.List;

import com.workManagement.domain.model.entity.ScheduleTimeEntity;

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
public class ScheduleTimeEditBean {

	private int year;

	private int month;

	private String nowYm;

	private List<Integer> calendarList;

	private String[][] userScheduleAllArray;

	private String afterYm;

	private String beforeYm;

	private ScheduleTimeEntity scheduleTimeEntity;
}
