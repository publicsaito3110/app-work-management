package com.workManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workManagement.domain.service.LogoutService;

/**
 * @author saito
 *
 */
@Controller
public class LogoutController extends BaseController {

	@Autowired
	private LogoutService logoutService;

	/**
	 * [Controller] ログインアウト機能 (/logout)
	 *
	 * <p>
	 * ログインログアウト処理を実行する<br>
	 * ただし、ログアウト後はログイン画面へ強制的に遷移させる
	 *
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/logout")
	public String logout(Authentication authentication, Model model) {

		// Service
		logoutService.logout();

		model.addAttribute("isAlertLoginFailed", false);
		// View
		return "redirect:/login";
	}
}
