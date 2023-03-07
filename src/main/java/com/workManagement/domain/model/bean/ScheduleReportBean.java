package com.workManagement.domain.model.bean;

import java.util.List;

import com.workManagement.domain.model.dto.ScheduleCountMonthDto;
import com.workManagement.domain.model.dto.ScheduleCountYearDto;

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
public class ScheduleReportBean {

	private int year;

	private int month;

	private List<ScheduleCountMonthDto> scheduleCountMonthList;

	private List<ScheduleCountYearDto> scheduleCountYearList;

	private String[] scheduleWorkTimeMonthArray;

	private String[] scheduleWorkTimeYearArray;
}
