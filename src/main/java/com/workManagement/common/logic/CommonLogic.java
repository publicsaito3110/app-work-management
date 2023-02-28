package com.workManagement.common.logic;

/**
 * @author saito
 *
 */
public class CommonLogic {


	/**
	 * バリデーション判定処理
	 *
	 * <p>
	 * バリデーションを正規表現で判定し、バリデーションの結果を返す
	 * ただし、正規表現パターンと一致していないまたは正規表現に則していないときは必ず失敗する
	 * </p>
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
}
