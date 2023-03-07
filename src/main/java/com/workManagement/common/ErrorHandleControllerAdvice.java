package com.workManagement.common;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * @author saito
 *
 */
@ControllerAdvice
@Slf4j
public class ErrorHandleControllerAdvice {

	/**
	 * [Controller Advice] エラーハンドリング処理
	 *
	 * <p>
	 * 例外発生情報(Exception)を取得してエラーログをlogファイルに出力後、エラー画面へ遷移させる<br>
	 * また、Exceptionのスーパークラスも含む<br>
	 * ただし、HttpStatusは500になる
	 *
	 * @param e Exception
	 * @return ModelAndView UIとView
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(Exception e) {

		// Exceptionクラスを取得
		String exceptionClassName = e.toString();

		// Exception発生個所を取得
		StackTraceElement[] stackTraceArray = e.getStackTrace();
		String errorClassName = stackTraceArray[0].toString();

		// HttpStatusを500に設定し、Statusコード,Statusメッセージを取得
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		String httpStatus = String.valueOf(response.getStatus());
		String httpStatusMessage = HttpStatus.INTERNAL_SERVER_ERROR.name();

		// エラーログをlogファイルに出力
		log.info("↓↓エラーログ↓↓");
		log.info("Exception: " + exceptionClassName);
		log.info("Error : " + errorClassName);

		// UIにエラー情報をセットし、エラー画面へ遷移
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exceptionClassName", exceptionClassName);
		modelAndView.addObject("errorClassName", errorClassName);
		modelAndView.addObject("httpStatus", httpStatus);
		modelAndView.addObject("httpStatusMessage", httpStatusMessage);
		modelAndView.addObject("errorMassage", "予期せぬエラーが発生しました");
		// View
		modelAndView.setViewName("error");
		return modelAndView;
	}
}