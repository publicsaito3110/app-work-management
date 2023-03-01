package com.workManagement.common.logic;

import java.time.LocalDate;

/**
 * @author saito
 *
 */
public class CommonLogic {


	/**
	 * [共通 Logic] null空文字変換処理
	 *
	 * <p>
	 * nullのとき空文字に変換する<br>
	 * ただし、null以外のときは何もしない
	 *
	 * @param value 全ての値
	 * @return String nullのとき空文字、null以外のときは何もしない
	 */
	public String changeEmptyByNull(String value) {

		// nullのとき、空文字を返す
		if (value == null) {
			return "";
		}
		return value;
	}


	/**
	 * [共通 Logic] バリデーション判定処理
	 *
	 * <p>
	 * バリデーションを正規表現で判定し、バリデーションの結果を返す
	 * ただし、正規表現パターンと一致していないまたは正規表現に則していないときは必ず失敗する
	 *
	 * @param value 全ての値
	 * @param regex 正規表現パターン
	 * @return boolean<br>
	 * true: バリデーションが成功(正規表現に基づいている)<br>
	 * false: バリデーションが失敗(正規表現に基づいていないまたは引数が異常なとき)
	 */
	public boolean isSuccessValidation(String value, String regex) {

		try {

			// 正規表現パターンと一致したとき、trueを返す
			if (value.matches(regex)) {
				return true;
			}

			// 正規表現パターンと一致しなかったとき、falseを返す
			return false;
		} catch (Exception e) {

			//例外発生時、falseを返す
			return false;
		}
	}


	/**
	 * [共通 Logic] LocalDate変換処理
	 *
	 * <p>
	 * ymd(YYYYMMDD)をLocalDateで返す<br>
	 * ただし、ymdがYYYYMMDDでない又は存在しない日付のときはnullを返す
	 *
	 * @param ymd (YYYYMMDD)
	 * @return LocalDate ymdから変換されたLocalDate
	 */
	public LocalDate getLocalDateByYmd(String ymd) {

		try {

			// ymdがnullまたは8桁でないとき、nullを返す
			if (ymd.length() != 8 ) {
				return null;
			}

			// ymdをLocalDateに変換する
			String ymdDate = ymd.substring(0, 4) + "-" + ymd.substring(4, 6) + "-" + ymd.substring(6, 8);
			return LocalDate.parse(ymdDate);
		} catch (Exception e) {

			//例外発生時、nullを返す
			return null;
		}
	}


	/**
	 * [共通 Logic] 日付変換処理
	 *
	 * <p>
	 * 年と月から対象の年月の最終日の日付を返す<br>
	 * ただし、対象の年月が異常なときはnullを返す
	 *
	 * @param year 年
	 * @param month 月
	 * @return String 対象の年月の最終日の日付
	 */
	public String changeLastDateYmd(int year, int month) {

		try {

			// 最終日の日付を取得し、ymd(YYYYMMDD)に変換する
			LocalDate localDate = LocalDate.of(year, month, 1);
			int lastDay = localDate.lengthOfMonth();
			return String.valueOf(year) + String.format("%02d", month) + String.format("%02d", lastDay);
		} catch (Exception e) {

			//例外発生時、nullを返す
			return null;
		}
	}


	/**
	 * [共通 Logic] 年月変換処理
	 *
	 * <p>
	 * 年と月から年月(YYYYMM)に変換して返す
	 *
	 * @param year 年
	 * @param month 月
	 * @return String 年月
	 */
	public String changeYm(int year, int month) {
		return String.valueOf(year) + String.format("%02d", month);
	}
}
