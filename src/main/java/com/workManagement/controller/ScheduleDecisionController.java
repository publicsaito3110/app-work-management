package com.workManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.workManagement.common.Const;
import com.workManagement.domain.model.bean.ScheduleDecisionBean;
import com.workManagement.domain.model.bean.ScheduleDecisionModifyBean;
import com.workManagement.domain.service.ScheduleDecisionService;
import com.workManagement.form.ScheduleDecisionModifyForm;

/**
 * @author saito
 *
 */
@Controller
public class ScheduleDecisionController extends BaseController {

	@Autowired
	private ScheduleDecisionService scheduleDecisionService;

	/**
	 * [Controller] 確定スケジュール表示機能画面 (/schedule-decision)
	 *
	 * @param ym RequestParameter 年月
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/schedule-decision")
	public String scheduleDecision(@RequestParam(value="ym",required=false) String ym, Authentication authentication, Model model) {

		// Service
		ScheduleDecisionBean scheduleDecisionBean = scheduleDecisionService.scheduleDecision(ym);
		model.addAttribute("bean", scheduleDecisionBean);
		model.addAttribute("htmlColorArray", Const.HTML_CLASS_SCHEDULE_COLOR_ARRAY);
		model.addAttribute("htmlBgColorArray", Const.HTML_CLASS_SCHEDULE_BG_COLOR_ARRAY);
		// View
		return "view/schedule-decision/schedule-decision";
	}


	/**
	 * [Controller] 確定スケジュール修正画面 (/schedule-decision)
	 *
	 * @param ym RequestParameter 年月
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/schedule-decision/modify")
	public String scheduleDecisionReport(@RequestParam(value="ym") String ym, @RequestParam(value="day") String day, Authentication authentication, Model model) {

		// Service
		ScheduleDecisionModifyBean scheduleDecisionModifyBean = scheduleDecisionService.scheduleDecisionModify(ym, day);
		model.addAttribute("bean", scheduleDecisionModifyBean);
		model.addAttribute("htmlColorArray", Const.HTML_CLASS_SCHEDULE_COLOR_ARRAY);
		model.addAttribute("htmlBgColorArray", Const.HTML_CLASS_SCHEDULE_BG_COLOR_ARRAY);
		model.addAttribute("form", new ScheduleDecisionModifyForm(scheduleDecisionModifyBean.getScheduleDayList(), scheduleDecisionModifyBean.getYear(), scheduleDecisionModifyBean.getMonth(), scheduleDecisionModifyBean.getDay()));
		model.addAttribute("isModalResult", false);
		// View
		return "view/schedule-decision/schedule-decision-modify";
	}
}
