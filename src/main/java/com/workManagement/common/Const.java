package com.workManagement.common;

/**
 * @author saito
 *
 */
public class Const {
	private Const() {
		//インスタンス化を禁止
	}


	//-------
    // その他
    //-------

	// セッション
	public static final String SESSION_KEYWORD_ACCOUNT_BEAN = "SESSION_KEY1_ACCOUNT_BEAN";

	// ユーザー役職
	public static final String ROLE_USERS_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USERS_GENERAL = "ROLE_GENERAL";

	//-----------
    // 正規表現
    //-----------

	// users
	public static final String PATTERN_USERS_ADMIN_FLG_ROLE_ADMIN = "1";
	public static final String PATTERN_USERS_DEL_FLG = "1";
}
