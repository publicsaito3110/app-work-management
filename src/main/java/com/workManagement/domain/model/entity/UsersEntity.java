package com.workManagement.domain.model.entity;

import com.workManagement.common.Const;
import com.workManagement.common.logic.CommonLogic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author saito
 *
 */
@Entity
@Table(name="user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UsersEntity extends BaseEntity {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "nameKana")
	private String nameKana;

	@Column(name = "gender")
	private String gender;

	@Column(name = "password")
	private String password;

	@Column(name = "address")
	private String address;

	@Column(name = "tel")
	private String tel;

	@Column(name = "email")
	private String email;

	@Column(name = "note")
	private String note;

	@Column(name = "icon_kbn")
	private String iconKbn;

	@Column(name = "admin_flg")
	private String adminFlg;

	@Column(name = "del_flg")
	private String delFlg;


	/**
	 * ユーザー権限取得処理
	 *
	 * 取得したユーザの権限を判定し、権限情報を返す<br>
	 * ただし、ユーザ権限情報は必ず"ROLE_**"になる
	 *
	 * @param void
	 * @return String ユーザーの権限情報
	 */
	public String adminFlgFormatRole() {

		if (new CommonLogic().isSuccessValidation(adminFlg, Const.PATTERN_USERS_ADMIN_FLG_ROLE_ADMIN)) {

			// 管理者であるとき、管理者権限情報を返す
			return Const.ROLE_USERS_ADMIN;
		} else {

			// 一般ユーザーであるとき、一般ユーザー権限情報を返す
			return Const.ROLE_USERS_GENERAL;
		}
	}



	/**
	 * 管理者権限判定処理
	 *
	 * 取得したユーザに管理者権限があるか判定する
	 *
	 * @param void
	 * @return Boolean 管理者権限の判定<br>
	 * true: 管理者権限あり<br>
	 * false: 管理者権限なし
	 */
	public boolean adminFlgFormatTF() {

		return new CommonLogic().isSuccessValidation(adminFlg, Const.PATTERN_USERS_ADMIN_FLG_ROLE_ADMIN);
	}


	/**
	 * 退職済みユーザ判定処理
	 *
	 * 取得したユーザが退職済みユーザであるか判定する
	 *
	 * @param void
	 * @return Boolean 退職済みユーザの判定<br>
	 * true: 退職済みユーザ<br>
	 * false: 未退職済みユーザ
	 */
	public boolean delFlgFormatTF() {

		return new CommonLogic().isSuccessValidation(delFlg, Const.PATTERN_USERS_DEL_FLG);
	}
}
