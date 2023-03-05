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
	public static final String SCHEDULE_NOT_RECORDED = "0";


	//-----------
	// Users
	//-----------
	public static final String USERS_DEL_FLG = "1";


	//-------
	// その他
	//-------

	// セッション
	public static final String SESSION_KEYWORD_ACCOUNT_BEAN = "SESSION_KEY1_ACCOUNT_BEAN";

	// ユーザー役職
	public static final String ROLE_USERS_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USERS_GENERAL = "ROLE_GENERAL";

	// 文字コード
	public static final String CHARACTER_PERCENT = "%";

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

	// schedule
	public static final String PATTERN_SCHEDULE_YM_INPUT = "^[0-9]{4}+(0[1-9]|1[0-2])$";
	public static final int PATTERN_SCHEDULE_YM_LENGTH_MIN_INPUT = 6;
	public static final int PATTERN_SCHEDULE_YM_LENGTH_MAX_INPUT = 6;
	public static final String PATTERN_SCHEDULE_DAY_INPUT = "^(0[1-9]|[12][0-9]|3[01])$";
	public static final int PATTERN_SCHEDULE_DAY_LENGTH_MIN_INPUT = 2;
	public static final int PATTERN_SCHEDULE_DAY_LENGTH_MAX_INPUT = 2;
	public static final String PATTERN_SCHEDULE_USER_INPUT_OPTIONAL = "^[A-Za-z0-9]{4}|^$";
	public static final int PATTERN_SCHEDULE_USER_LENGTH_MIN_INPUT_OPTIONAL = 0;
	public static final int PATTERN_SCHEDULE_USER_LENGTH_MAX_INPUT_OPTIONAL = 4;
}
