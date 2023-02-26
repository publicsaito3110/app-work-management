package com.workManagement.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workManagement.domain.model.entity.UsersEntity;
import com.workManagement.domain.model.entity.key.PkUsersEntity;

/**
 * @author saito
 *
 */
@Repository
public interface UsersRepository extends BaseRepository<UsersEntity, PkUsersEntity>{


	/**
	 * [Repository] ユーザ検索処理
	 *
	 * ユーザIDまたはメールアドレスと一致するユーザを取得する<br>
	 * ただし、一致するユーザーがいない場合はnullとなる
	 *
	 * @param id 取得したいユーザのユーザID
	 * @param email 取得したいユーザのメールアドレス
	 * @return UsersEntity ユーザ情報
	 */
	@Query(value = "SELECT u.* FROM users u WHERE u.id = :userId OR u.email = :email;", nativeQuery = true)
	public UsersEntity selectUsers(String id, String email);
}
