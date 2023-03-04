package com.workManagement.common;

/**
 * @author saito
 *
 */
public class Const {
	private Const() {
		//インスタンス化を禁止
	}

	//-----------
	// Schedule
	//-----------

	public static final int SCHEDULE_RECORDABLE_MAX_DIVISION = 7;
	public static final String SCHEDULE_RECORDED = "1";

	//-------
    // その他
    //-------

	// セッション
	public static final String SESSION_KEYWORD_ACCOUNT_BEAN = "SESSION_KEY1_ACCOUNT_BEAN";

	// ユーザー役職
	public static final String ROLE_USERS_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USERS_GENERAL = "ROLE_GENERAL";

	// HTML
	public static final String HTML_TAG_BR ="<br>";
	public static final String[] HTML_CLASS_SCHEDULE_COLOR_ARRAY = {"teal", "orange", "pink", "yellow", "purple", "cyan", "gray"};
	public static final String[] HTML_CLASS_SCHEDULE_BG_COLOR_ARRAY = {"bg-teal", "bg-orange", "bg-pink", "bg-yellow", "bg-purple", "bg-cyan", "bg-gray"};
	public static final String[] HTML_CLASS_SCHEDULE_BTN_COLOR_ARRAY = {"btn-teal", "btn-orange", "btn-pink", "btn-yellow", "btn-purple", "btn-cyan", "btn-gray"};

	//-----------
    // 正規表現
    //-----------

	// users
	public static final String PATTERN_USERS_ADMIN_FLG_ROLE_ADMIN = "1";
	public static final String PATTERN_USERS_DEL_FLG = "1";
}
