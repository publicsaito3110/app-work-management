package com.workManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.workManagement.domain.model.bean.LoginAuthBean;
import com.workManagement.domain.service.LoginService;

/**
 * @author saito
 *
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private LoginService loginService;


	/**
	 * [Controller] ログイン画面 (/login)
	 *
	 * <p>
	 * ログイン画面へ遷移させる
	 *
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/login")
	public String login(Authentication authentication, Model model) {

		model.addAttribute("isAlertLoginFailed", false);
		// View
		return "view/login/login";
	}


	/**
	 * [Controller] ログイン情報設定機能 (/login/auth)
	 *
	 * <p>
	 * ログイン情報を設定する<br>
	 * ただし、ログイン不可ユーザのときは、ログイン画面へ強制的に遷移させる
	 *
	 * @param authentication Authentication ユーザ情報
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/login/auth")
	public String loginAuth(Authentication authentication, Model model) {

		// ログインユーザのユーザIDを取得
		String loginUser = authentication.getName();

		// Service
		LoginAuthBean loginAuthBean = loginService.loginAuth(loginUser);
		if (!loginAuthBean.isLogin()) {
			// ログイン可能ユーザでないとき、ログイン画面へ強制的に遷移させる
			model.addAttribute("isAlertLoginFailed", true);
			model.addAttribute("errorMassage", loginAuthBean.getErrorMassage());
			// View
			return "view/login/login";
		}

		// ログイン可能ユーザのとき、ホームへリダイレクト
		model.addAttribute("isAlertLoginFailed", false);
		// View
		return "redirect:/home";
	}


	/**
	 * [Controller] セッションエラー画面 (/login/error)
	 *
	 * <p>
	 * セッション有効期限切れによるログイン画面へ遷移させる
	 *
	 * @param authentication Authentication
	 * @param model Model ThymeleafのUI
	 * @return String Viewのパス
	 */
	@RequestMapping("/login/error")
	public String loginError(Authentication authentication, Model model) {

		model.addAttribute("isAlertLoginFailed", true);
		model.addAttribute("errorMassage", "セッションの有効期限が切れました。もう一度ログインしてください。");
		// View
		return "view/login/login";
	}
}
