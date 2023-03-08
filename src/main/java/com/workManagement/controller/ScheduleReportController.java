package com.workManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.workManagement.common.Const;
import com.workManagement.domain.model.bean.ScheduleReportBean;
import com.workManagement.domain.model.bean.ScheduleReportSearchCountBean;
import com.workManagement.domain.model.bean.ScheduleReportSearchTimeBean;
import com.workManagement.domain.service.ScheduleReportService;

/**
 * @author saito
 *
 */
@Controller
public class ScheduleReportController extends BaseController {

	@Autowired
	private ScheduleReportService scheduleReportService;

	/**
	 * [Controller] 勤務状況表示機能画面 (/schedule-report)
	 *
	 * @param ym RequestParameter 年月
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/schedule-report")
	public String scheduleDecision(@RequestParam(value="ym",required=false) String ym, Authentication authentication, Model model) {

		// Service
		ScheduleReportBean scheduleReportBean = scheduleReportService.scheduleReport(ym);
		model.addAttribute("bean", scheduleReportBean);
		model.addAttribute("htmlColorArray", Const.HTML_CLASS_SCHEDULE_COLOR_ARRAY);
		model.addAttribute("htmlBgColorArray", Const.HTML_CLASS_SCHEDULE_BG_COLOR_ARRAY);
		// View
		return "view/schedule-report/schedule-report";
	}


	/**
	 * [Controller] 確定スケジュール勤務日数追加表示機能(非同期) (/schedule-report/search-count)
	 *
	 * @param ym RequestParameter 年月
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/schedule-report/search-count")
	public String scheduleReportSearchCount(@RequestParam(value="ym") String ym, Authentication authentication, Model model) {

		// Service
		ScheduleReportSearchCountBean scheduleReportSearchCountBean = scheduleReportService.scheduleReportSearchCount(ym);
		model.addAttribute("bean", scheduleReportSearchCountBean);
		// View
		return "view/schedule-report/schedule-report-search-count";
	}


	/**
	 * [Controller] 確定スケジュール勤務時間追加表示機能(非同期) (/schedule-report/search-time)
	 *
	 * @param ym RequestParameter 年月
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/schedule-report/search-time")
	public String scheduleDecisionReportSearchTime(@RequestParam(value="ym") String ym, Authentication authentication, Model model) {

		// Service
		ScheduleReportSearchTimeBean scheduleReportSearchTimeBean = scheduleReportService.scheduleReportSearchTime(ym);
		model.addAttribute("bean", scheduleReportSearchTimeBean);
		// View
		return "view/schedule-report/schedule-report-search-time";
	}
}
