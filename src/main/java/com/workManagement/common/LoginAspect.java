package com.workManagement.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.workManagement.domain.model.bean.collection.AccountBean;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * @author saito
 *
 */
@Aspect
@Component
public class LoginAspect {

	@Autowired
	private HttpSession httpSession;


	/**
     * セッション判定処理 (AOP)
     *
     * <p>
     * Controller実行前にセッション及びURI制限を行う<br>
     * セッションが未保持のとき、<br>
     * 　・セッションの未保持が許容されていないURI: ログイン画面へ強制的に遷移<br>
     * 　・セッションの未保持が許容されているURI: そのまま実行<br>
     * 　・存在しないURI: 404ページへ遷移<br>
     * セッションが保持されているとき、<br>
     * 　・ログイン関連のURI: 強制的にホーム画面へ遷移<br>
     * 　・ログイン関連以外のURI: そのまま実行
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     */
	@Around("execution(* *..*Controller.*(..))")
	public Object executeSession(ProceedingJoinPoint joinPoint) throws Throwable {

		// リクエストを受け取ったControllerを取得
		Object controllerObj = joinPoint.getTarget();

		// エラーハンドリングするControllerのとき、Controllerの処理を実行
		if (controllerObj instanceof BasicErrorController) {
			return joinPoint.proceed();
		}

		// 共通ロジッククラス
		CommonLogic commonLogic = new CommonLogic();

		// 現在のURIを取得
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String nowUri = request.getRequestURI();

		// ログインセッションを取得
		AccountBean accountBean = (AccountBean)httpSession.getAttribute(Const.SESSION_KEYWORD_ACCOUNT_BEAN);

		// ログインセッションが存在しないとき
		if (accountBean == null) {

			// ログインセッションの未保持を許容するURI
			String patternSessionIgnoreUri =  "/|/logout|/error|/login|^/login/.*$";

			// 現在のURIがセッション未保持を許容するURIに含まれていないとき、ログイン画面へ強制的に遷移
			if (!commonLogic.isSuccessValidation(nowUri, patternSessionIgnoreUri)) {
				return "redirect:/login/error";
			}

			// URIの処理を実行
			return joinPoint.proceed();
		}

		// ログインに関するURI
		String patternLoginUri = "/login|^/login/.*$";

		// ログインに関するURIのとき、ホーム画面へ強制的に遷移
		if (commonLogic.isSuccessValidation(nowUri, patternLoginUri)) {
			return "redirect:/home";
		}

		// URIの処理を実行
		return joinPoint.proceed();
	}
}