package com.workManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.workManagement.common.Const;
import com.workManagement.domain.model.bean.HomeBean;
import com.workManagement.domain.service.HomeService;

/**
 * @author saito
 *
 */
@Controller
public class HomeController extends BaseController {

	@Autowired
	private HomeService homeService;

	/**
	 * [Controller] ホーム画面表示機能 (/home)
	 *
	 * @param ym RequestParameter 年月
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/home")
	public String home(@RequestParam(value="ymd",required=false) String ymd, Authentication authentication, Model model) {

		// ログインユーザのユーザIDを取得
		String loginUser = authentication.getName();

		// Service
		HomeBean homeBean = homeService.home(ymd, loginUser);
		model.addAttribute("bean", homeBean);
		model.addAttribute("htmlColorArray", Const.HTML_CLASS_SCHEDULE_COLOR_ARRAY);
		model.addAttribute("htmlBgColorArray", Const.HTML_CLASS_SCHEDULE_BG_COLOR_ARRAY);
		// View
		return "view/home/home";
	}
}
