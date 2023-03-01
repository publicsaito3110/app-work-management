package com.workManagement.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workManagement.domain.model.entity.UsersEntity;

/**
 * @author saito
 *
 */
@Repository
public interface UsersRepository extends BaseRepository<UsersEntity, String> {


	/**
	 * [Repository] ユーザ検索処理
	 *
	 * ユーザIDと一致するユーザを取得する<br>
	 * ただし、一致するユーザーがいない場合はnullとなる
	 *
	 * @param id 取得したいユーザのユーザID
	 * @return UsersEntity ユーザ情報
	 */
	@Query(value = "SELECT u.* FROM users u WHERE u.id = :id", nativeQuery = true)
	public UsersEntity selectUsers(String id);


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
	@Query(value = "SELECT u.* FROM users u WHERE u.id = :id OR u.email = :email", nativeQuery = true)
	public UsersEntity selectUsers(String id, String email);
}
