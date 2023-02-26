package com.workManagement.domain.model.entity.key;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.Data;

/**
 * @author saito
 *
 */
@Data
public class PkUsersEntity implements Serializable {

	@Column(name="id")
	private String id;

	@Column(name="email")
	private String email;
}
