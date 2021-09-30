package com.javan.photoapp.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long userId;
	
	@Column(nullable=false, unique=true)
	private String userName;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	@Column(columnDefinition="tinyint(1) default 1")
	private boolean accountNonExpired;
	
	@Column(columnDefinition="tinyint(1) default 1")
	private boolean accountNonLocked;
	
	@Column(columnDefinition="tinyint(1) default 1")
	private boolean credentialsNonExpired;
	
	@Column(columnDefinition="tinyint(1) default 1")
	private boolean enabled;
	
	}
