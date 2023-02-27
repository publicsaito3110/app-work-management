package com.workManagement.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workManagement.common.CommonLogic;
import com.workManagement.common.Const;
import com.workManagement.domain.model.bean.LoginAuthBean;
import com.workManagement.domain.model.bean.collection.AccountBean;
import com.workManagement.domain.model.entity.UsersEntity;
import com.workManagement.domain.repository.UsersRepository;

import jakarta.servlet.http.HttpSession;

/**
 * @author saito
 *
 */
@Service
@Transactional
public class LoginService extends BaseService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private HttpSession httpSession;

	// フィールド
	private String errorMassage;

	/**
	 * [Service] ログイン情報設定機能 (/login/auth)
	 *
	 * @param loginUser ログインしているユーザID
	 * @return LoginAuthBean
	 */
	public LoginAuthBean loginAuth(String loginUser) {

		// ユーザIDからログインユーザを取得する
		UsersEntity usersEntity = selectUserByUserId(loginUser);
		// 取得したユーザがログイン可能ユーザかどうか判定する
		boolean isLogin = isCheckLoginUser(usersEntity);
		// ログイン可能ユーザのとき、セッションをセットする
		if (isLogin) {
			generateSession(usersEntity);
		}

		// Beanにセット
		LoginAuthBean loginAuthBean = new LoginAuthBean(isLogin, errorMassage);
		return loginAuthBean;
	}


	/**
	 * セッション処理
	 *
	 * <p>
	 * ログイン情報からログイン済みを識別するセッションを作成する<br>
	 * ただし、ユーザ情報がないときはセッションをセットしない
	 *
	 * @param userEntity DBから取得したUserEntity
	 * @return boolean<br>
	 * true: セッションを作成したとき
	 * false: セッションを作成できなかったとき
	 */
	private boolean generateSession(UsersEntity usersEntity) {

		// セッションを完全に削除
		httpSession.invalidate();

		// アカウント情報をセッションをセット
		AccountBean accountBean = new AccountBean(usersEntity);
		httpSession.setAttribute(Const.SESSION_KEYWORD_ACCOUNT_BEAN, accountBean);
		return true;
	}


	/**
	 * ログイン可能ユーザ判定処理
	 *
	 * <p>
	 * ユーザ情報からログイン可能ユーザであるか判定する<br>
	 * 一致するユーザーがいないまたは退職済みのときはログインが不可になる<br>
	 * また、ログインが不可であるときはエラーメッセージをフィールド(errorMassage)にセットする
	 *
	 * @param usersEntity ユーザ情報
	 * @return boolean<br>
	 * true: ログイン情報から一致するユーザーかつ退職済みでないユーザであるとき<br>
	 * false: ログイン情報から一致するユーザがいないまたは退職済みであるとき
	 */
	private boolean isCheckLoginUser(UsersEntity usersEntity) {

		// ログイン情報からユーザーを取得できなかったとき
		if (usersEntity == null) {

			// フィールドにセットし、falseを返す
			errorMassage = "IDまたはパスワードが違います";
			return false;
		}

		// 退職済みユーザだったとき
		if (new CommonLogic().isSuccessValidation(usersEntity.getDelFlg(), Const.PATTERN_USERS_DEL_FLG)) {

			// フィールドにセットし、falseを返す
			errorMassage = "このユーザーは現在ログインできません";
			return false;
		}

		return true;
	}


	/**
	 * [Repository] ユーザ検索処理
	 *
	 * <p>
	 * userIdと一致するユーザIDのユーザを取得する<br>
	 * ただし、一致するユーザーがいないときはnullとなる
	 *
	 * @param userId 取得したいユーザ情報のユーザID
	 * @return UserEntity ユーザ情報
	 */
	private UsersEntity selectUserByUserId(String id) {
		return usersRepository.selectUsers(id);
	}
}
