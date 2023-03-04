//package com.workManagement.common.logic;
//
//import org.apache.commons.lang3.RandomStringUtils;
//
///**
// * @author saito
// *
// */
//public class LibApacheLogic {
//
//
//	/**
//	 * ランダム英数字作成処理 {Apache Commons}
//	 *
//	 * <p>
//	 * 指定した桁数のランダム英数字を作成する<br>
//	 * ただし、指定桁数が0以下の場合はnullが返される
//	 *
//	 * @param count 作成したい文字列の桁数
//	 * @return String ランダムの英数字の文字列<br>
//	 * 5 -> a6W33p, 10 -> d9Eq4J32s0...
//	 */
//	public String getRandomAlphanumeric(int count) {
//
//		//指定桁数が0以下のとき、nullを返す
//		if (count <= 0) {
//			return null;
//		}
//
//		// 指定桁数に一致するランダムの英数字の文字列を作成する
//		return RandomStringUtils.randomAlphanumeric(count);
//	}
//
//
//	/**
//	 * ランダム数字作成処理 {Apache Commons}
//	 *
//	 * <p>
//	 * 指定した桁数のランダム数字を作成する<br>
//	 * ただし、指定桁数が0以下の場合はnullが返される
//	 *
//	 * @param count 作成したい文字列の桁数
//	 * @return String ランダムの数字の文字列<br>
//	 * 5 -> 01436, 10 -> 6920196423...
//	 */
//	public String getRandomNumeric(int count) {
//
//		//指定桁数が0以下のとき、nullを返す
//		if (count <= 0) {
//			return null;
//		}
//
//		// 指定桁数に一致するランダムの数字の文字列を作成する
//		return RandomStringUtils.randomNumeric(count);
//	}
//}
