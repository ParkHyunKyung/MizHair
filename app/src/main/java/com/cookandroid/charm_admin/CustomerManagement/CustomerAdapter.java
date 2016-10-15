/*
 * com.ziofront.android.contacts
 * Contact.java
 * Jiho Park    2009. 11. 27.
 *
 * Copyright (c) 2009 ziofront.com. All Rights Reserved.
 */
package com.cookandroid.charm_admin.CustomerManagement;

/**
 * @author Jiho Park
 * 
 */
public class CustomerAdapter {
	private String userName;
	private String userNum;
	private String userId;
	private String userPhone;
	private String userGender;

	public CustomerAdapter(String userName, String userNum, String userId,String userPhone,String userGender) {
		this.userName = userName;
		this.userNum = userNum;
		this.userId = userId;
		this.userPhone = userPhone;
		this.userGender = userGender;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
}
