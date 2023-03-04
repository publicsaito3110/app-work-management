package com.workManagement.domain.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService extends BaseService {

	@Autowired
	private HttpSession httpSession;


	/**
	 * [Service] ログインアウト機能 (/logout)
	 *
	 * @param void
	 * @return booelan
	 */
	public boolean logout() {

		// セッションを完全に削除
		removeSession();
		return true;
	}


	/**
	 * セッション削除処理
	 *
	 * <p>
	 * 保持しているセッションを完全に削除する
	 *
	 * @param void
	 * @return Boolean セッションの削除の判定<br>
	 * true: セッションの削除に成功したとき<br>
	 * false: セッションの削除に失敗したとき
	 */
	private boolean removeSession() {

		// セッションを完全に削除
		httpSession.invalidate();
		return true;
	}
}
