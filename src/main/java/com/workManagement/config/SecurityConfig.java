package com.workManagement.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author saito
 *
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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

			// ログイン画面のURI
			.loginProcessingUrl("/login")
			.loginPage("/login")
			// ログイン成功時のURI
			.defaultSuccessUrl("/login/auth")
			// ログイン失敗時のURI
			.failureUrl("/login?error")
			.permitAll()
		).logout(logout -> logout

			// ログアウト時のURI
			.logoutSuccessUrl("/logout")
		).authorizeHttpRequests(authz -> authz

			// 未ログイン時のアクセス可能なURI
			.requestMatchers("/**").permitAll()
			// static内のアクセスを許容するパス
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		);
		return httpSecurity.build();
	}
}
