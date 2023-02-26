package com.workManagement.domain.model.bean.collection;

import com.workManagement.domain.model.entity.UsersEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author saito
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBean {

	private String userId;

	private String name;

	private String nameKana;

	private boolean isAdmin;


	/**
     * [Constractor] UserEntity
     *
     * <p>
     * セッションとして保持するためのユーザ情報をUserEntityから取得し、セットする
     *
     * @param userEntity セッションとして保持したいユーザ情報
     * @return AccountBean
     */
	public AccountBean(UsersEntity usersEntity) {
		userId = usersEntity.getId();
		name = usersEntity.getName();
		nameKana = usersEntity.getNameKana();
		isAdmin = usersEntity.adminFlgFormatTF();
	}
}
