package com.workManagement.domain.model.bean;

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
public class LoginAuthBean {

	private boolean isLogin;

	private String errorMassage;
}
