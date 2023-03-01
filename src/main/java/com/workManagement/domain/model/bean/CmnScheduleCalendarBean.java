package com.workManagement.domain.model.bean;

import java.util.List;

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
public class CmnScheduleCalendarBean {

	private int year;

	private int month;

	private String lastDateYmd;

	private List<Integer> calendarList;

	private String nowYm;

	private String afterYm;

	private String beforeYm;
}
