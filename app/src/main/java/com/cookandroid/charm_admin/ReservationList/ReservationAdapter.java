/*
 * com.ziofront.android.contacts
 * Contact.java
 * Jiho Park    2009. 11. 27.
 *
 * Copyright (c) 2009 ziofront.com. All Rights Reserved.
 */
package com.cookandroid.charm_admin.ReservationList;

/**
 * @author Jiho Park
 * 
 */
public class ReservationAdapter {
	private String name;
	private String item;
	private String date;

	public ReservationAdapter(String name, String item, String date) {
		this.name = name;
		this.item = item;
		this.date = date;
	}

	public String getName() {
		return name;
	}
	public String getDate(){ return date; }
	public String getItem() { return item; }

	public void setName(String name) {
		this.name = name;
	}
	public void setDate(String date){ this.date = date; }
	public void setItem(String number) {
		this.item = number;
	}

}
