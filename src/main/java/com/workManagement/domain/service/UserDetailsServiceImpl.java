package com.workManagement.domain.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workManagement.domain.model.entity.UsersEntity;
import com.workManagement.domain.repository.UsersRepository;

/**
 * @author saito
 *
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;


	/**
	 * [Spring Security] ログイン認証機能 (/login)
	 *
	 * 入力されたログイン情報と登録されているユーザ情報を認証する<br>
	 * ログイン情報と一致したユーザ情報(ユーザID, ユーザ権限)を格納し、ログイン認証を行う<br>
	 * ただし、ログイン認証に失敗したときSecurityConfugで設定したURIへリダイレクトされる
	 *
	 * @param username ログイン時に入力されたユーザIDまたはメールアドレス
	 * @return UserDetails ユーザ情報(ユーザID, パスワード, ユーザ権限)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 入力された情報からユーザ情報を検索
		UsersEntity usersEntity = usersRepository.selectUsers(username, username);

		// ユーザ情報が取得できなかったとき
		if (usersEntity == null) {
			throw new UsernameNotFoundException(username + " is not found");
		}

		//---------------------
		// UserDetailsを返却
		//---------------------

		// User(UserDetailsインターフェースの実装クラス)にセットして返却
		var grantedAuthorities = new HashSet<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(usersEntity.adminFlgFormatRole()));
		return new User(usersEntity.getId(), usersEntity.getPassword(), grantedAuthorities);
	}
}
