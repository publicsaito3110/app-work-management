package com.workManagement.domain.model.bean;

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
public class ScheduleReportSearchTimeBean {

	private int year;

	private int month;

	private String[] scheduleTimeMonthArray;
}
