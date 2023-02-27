package com.workManagement.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author saito
 *
 */
@Configuration
public class SecurityConfig {


	/**
	 * [Configuration] パスワード設定(Spring Security)
	 *
	 * ログイン認証及びパスワードのハッシュ化で必要なハッシュアルゴリズムを実装したクラスを設定する<br>
	 * 実装されるクラスはBCryptPasswordEncoderであり、PasswordEncoderインターフェースにセットされる
	 *
	 * @param void
	 * @return PasswordEncoder 設定済みのログイン認証情報
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	/**
	 * [Configuration] ログイン認証設定(Spring Security)
	 *
	 * ログイン認証で必要な情報を設定する
	 *
	 * @param httpSecurity ログイン認証の設定
	 * @return SecurityFilterChain 設定済みのログイン認証情報
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.formLogin(login -> login

			// ログイン画面のURL
			.loginProcessingUrl("/login")
			.loginPage("/login")
			// ログイン成功時のURL
			.defaultSuccessUrl("/login/auth")
			// ログイン失敗時のURL
			.failureUrl("/login?error")
			.permitAll()

		).logout(logout -> logout
			// ログアウト時のURL
			.logoutSuccessUrl("/logout")
		).authorizeHttpRequests(authz -> authz

			// static内のアクセスを許容するパス
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			.requestMatchers("/img/**").permitAll()
		);
		return httpSecurity.build();
	}
}
