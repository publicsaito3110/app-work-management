package com.workManagement.domain.model.bean;

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
public class CmnScheduleUserNameBean {

	private ScheduleTimeEntity scheduleTimeEntity;

	private String[][] scheduleUserNameArray;
}
