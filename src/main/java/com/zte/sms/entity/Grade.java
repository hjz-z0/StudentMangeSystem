package com.zte.sms.entity;

import java.io.Serializable;

public class Grade implements Serializable{
	
	private Integer gid;
	
	private String gname;
	
	private String gdesc;
	
	private Integer state;
	
	public Grade() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Grade(Integer gid, String gname, String gdesc, Integer state) {
		super();
		this.gid = gid;
		this.gname = gname;
		this.gdesc = gdesc;
		this.state = state;
	}



	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getGdesc() {
		return gdesc;
	}

	public void setGdesc(String gdesc) {
		this.gdesc = gdesc;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
	

}
