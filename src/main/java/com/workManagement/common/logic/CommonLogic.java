package com.workManagement.common.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.workManagement.common.Const;

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
	 * [共通 Logic] 年月日付取得処理
	 *
	 * <p>
	 * 年月と日からをそれぞれ配列で取得する
	 *
	 * @param ym 年月
	 * @param day 日
	 * @return String[] 年月日が格納された配列<br>
	 * (要素: [0] 年, [1] 月, [2] 日)
	 */
	public String[] changeYmdArray(String ym, String day) {

		try {

			// 年, 月, 日をそれぞれ取得し、配列に格納
			String year = ym.substring(0, 4);
			String month = ym.substring(4, 6);
			String trimDay = String.format("%02d", Integer.parseInt(day));
			String[] ymdArray = {year, month, trimDay};
			return ymdArray;
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


	/**
	 * 時間フォーマット変換処理
	 *
	 * <p>
	 * ミリ秒に変換された時間から時間フォーマット(HH.M)に変換して返す<br>
	 * ex) 360000ミリ秒(1時間00分) -> 1.0, 2160000ミリ秒(1時間30分) -> 1.3
	 *
	 * @param hmMs ミリ秒換算された時間
	 * @return String 時間フォーマットに変換された値(HH.M)
	 */
	public String changeTimeHour(long hmMs) {

		// 時間換算するための数字をBigDecimalで取得
		BigDecimal num3600000Bd = new BigDecimal(String.valueOf("3600000"));
		BigDecimal num60000Bd = new BigDecimal(String.valueOf("60000"));
		BigDecimal num60Bd = new BigDecimal(String.valueOf("60"));

		// ミリ秒をBigDecimalで取得
		BigDecimal hmMsBd = new BigDecimal(String.valueOf(String.valueOf(hmMs)));

		// ミリ秒から時間に換算
		BigDecimal hourBd = hmMsBd.divide(num3600000Bd, 0, RoundingMode.DOWN);
		BigDecimal minuresBd = hmMsBd.divide(num60000Bd, 0, RoundingMode.DOWN).remainder(num60Bd);
		BigDecimal hmTimeBd = hourBd.add(minuresBd.divide(num60Bd, 1, RoundingMode.DOWN));
		return hmTimeBd.toString();
	}


	/**
	 * ミリ秒換算処理
	 *
	 * <p>
	 * 時間(HHMM)をミリ秒に換算して返す<br>
	 * ただし、時間がlongの取得可能数値を超えてミリ秒換算できないまたは引数が指定フォーマット以外のときは必ずlongの最小値(-9223372036854775808)が返される
	 *
	 * @param hour 時間
	 * @param minutes 分
	 * @return long ミリ秒換算した時間
	 */
	public long chengeHmMsByHourMinutes(String hour, String minutes) {

		try {

			// 時間フォーマット(HH:MM)に変換
			String hmTime = hour + ":" + minutes;

			// 時間(HH:mm)フォーマットの文字列をDate型に変換するクラス
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

			// それぞれの時間をDate型に変換
			Date hmDate = simpleDateFormat.parse(hmTime);

			// SimpleDateFormat標準時刻差分を差し引いた時間をミリ秒で取得し、返す
			long hmTimeMs = hmDate.getTime() - Const.SIMPLE_DATE_FORMAT_SERVER_TIME_ZONE_JP_DISTANCE;
			return hmTimeMs;
		} catch (Exception e) {

			//例外発生時、longの最小値を返す
			return Long.MIN_VALUE;
		}
	}
}
