package com.workManagement.domain.repository;

import java.util.List;

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
	 * <p>
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
	 * <p>
	 * ユーザIDまたはメールアドレスと一致するユーザを取得する<br>
	 * ただし、一致するユーザーがいない場合はnullとなる
	 *
	 * @param id 取得したいユーザのユーザID
	 * @param email 取得したいユーザのメールアドレス
	 * @return UsersEntity ユーザ情報
	 */
	@Query(value = "SELECT u.* FROM users u WHERE u.id = :id OR u.email = :email", nativeQuery = true)
	public UsersEntity selectUsers(String id, String email);


	/**
	 * [Repository] ユーザ検索処理
	 *
	 * <p>
	 * 未退職ユーザを全て取得する<br>
	 * ただし、該当するユーザーがいない場合はnullとなる
	 *
	 * @param delFlg 退職フラグ
	 * @return UsersEntity 全ての未退職ユーザ
	 */
	@Query(value = "SELECT u.* FROM users u WHERE u.del_flg != :delFlg OR u.del_flg IS NULL", nativeQuery = true)
	public List<UsersEntity> selectUsersNotDelFlg(String delFlg);
}
