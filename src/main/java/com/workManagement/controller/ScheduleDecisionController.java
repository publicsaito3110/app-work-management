package com.workManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.workManagement.common.Const;
import com.workManagement.domain.model.bean.ScheduleDecisionBean;
import com.workManagement.domain.model.bean.ScheduleDecisionModifyBean;
import com.workManagement.domain.model.bean.ScheduleDecisionModifyModifyBean;
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
	 * [Controller] 確定スケジュール修正画面 (/schedule-decision/modify)
	 *
	 * @param ym RequestParameter 年月
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/schedule-decision/modify")
	public String scheduleDecisionModify(@RequestParam(value="ym") String ym, @RequestParam(value="day") String day, Authentication authentication, Model model) {

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


	/**
	 * [Controller] 確定スケジュール修正機能 (/schedule-decision/modify/modify)
	 *
	 * @param ym RequestParameter 年月
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/schedule-decision/modify/modify")
	public String scheduleDecisionModifyModify(@Validated @ModelAttribute ScheduleDecisionModifyForm scheduleDecisionModifyForm, BindingResult bindingResult, Authentication authentication, Model model) {

		// バリデーションエラーのとき
		if (bindingResult.hasErrors()) {

			// Service
			ScheduleDecisionModifyBean scheduleDecisionModifyBean = scheduleDecisionService.scheduleDecisionModify(scheduleDecisionModifyForm.getYm(), scheduleDecisionModifyForm.getDay());
			model.addAttribute("bean", scheduleDecisionModifyBean);
			model.addAttribute("htmlColorArray", Const.HTML_CLASS_SCHEDULE_COLOR_ARRAY);
			model.addAttribute("htmlBgColorArray", Const.HTML_CLASS_SCHEDULE_BG_COLOR_ARRAY);
			model.addAttribute("form", new ScheduleDecisionModifyForm(scheduleDecisionModifyBean.getScheduleDayList(), scheduleDecisionModifyBean.getYear(), scheduleDecisionModifyBean.getMonth(), scheduleDecisionModifyBean.getDay()));
			model.addAttribute("isModalResult", true);
			model.addAttribute("modalResultTitle", "確定シフト編集結果");
			model.addAttribute("modalResultContentFailed", "シフトの更新に失敗しました");
			// View
			return "view/schedule-decision/schedule-decision-modify";
		}

		// Service
		ScheduleDecisionModifyModifyBean scheduleDecisionModifyModifyBean = scheduleDecisionService.scheduleDecisionModifyModify(scheduleDecisionModifyForm);
		model.addAttribute("bean", scheduleDecisionModifyModifyBean);
		model.addAttribute("htmlColorArray", Const.HTML_CLASS_SCHEDULE_COLOR_ARRAY);
		model.addAttribute("htmlBgColorArray", Const.HTML_CLASS_SCHEDULE_BG_COLOR_ARRAY);
		model.addAttribute("form", new ScheduleDecisionModifyForm(scheduleDecisionModifyModifyBean.getScheduleDayList(), scheduleDecisionModifyModifyBean.getYear(), scheduleDecisionModifyModifyBean.getMonth(), scheduleDecisionModifyModifyBean.getDay()));
		model.addAttribute("isModalResult", true);
		model.addAttribute("modalResultTitle", "確定シフト編集結果");
		if (scheduleDecisionModifyModifyBean.isUpdate()) {
			// 更新に成功したとき
			model.addAttribute("modalResultContentSuccess", "シフトの更新に成功しました");
		} else {
			// 更新に失敗したとき
			model.addAttribute("modalResultContentFailed", "シフトの更新に失敗しました");
		}
		// View
		return "view/schedule-decision/schedule-decision-modify";
	}
}
