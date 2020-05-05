package com.zte.sms.entity;

import java.io.Serializable;

public class Sysuser implements Serializable {
	
	private Integer id;
	
	private String username;
	
	private String password;
	
	private String state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public Sysuser() {
		// TODO Auto-generated constructor stub
	}

	public Sysuser(Integer id, String username, String password, String state) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.state = state;
	}

	@Override
	public String toString() {
		return "Sysuser [id=" + id + ", username=" + username + ", password=" + password + ", state=" + state + "]";
	}
	
	

}
