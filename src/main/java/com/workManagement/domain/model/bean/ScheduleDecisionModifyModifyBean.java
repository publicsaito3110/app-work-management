package com.workManagement.domain.model.bean;

import java.util.List;

import com.workManagement.domain.model.dto.ScheduleDayDto;
import com.workManagement.domain.model.dto.SchedulePreDayDto;
import com.workManagement.domain.model.entity.ScheduleTimeEntity;
import com.workManagement.domain.model.entity.UsersEntity;

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
public class ScheduleDecisionModifyModifyBean {

	private boolean isUpdate;
	private String year;

	private String month;

	private String day;

	private List<SchedulePreDayDto> schedulePreDayList;

	private List<ScheduleDayDto> scheduleDayList;

	private ScheduleTimeEntity scheduleTimeEntity;

	private List<UsersEntity> usersList;
}
