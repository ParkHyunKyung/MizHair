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
public class Contact {
	private String name;
	private String number;
	private String date;

	public Contact(String name, String number, String date) {
		this.name = name;
		this.number = number;
		this.date = date;
	}

	public String getName() {
		return name;
	}
	public String getDate(){ return date; }
	public String getNumber() { return number; }

	public void setName(String name) {
		this.name = name;
	}
	public void setDate(String date){ this.date = date; }
	public void setNumber(String number) {
		this.number = number;
	}

}
