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
public class ReservationData {
	private String name;
	private String item;
	private String time;

/*	public ReservationData(String name, String item, String time) {
		this.name = name;
		this.item = item;
		this.date = time;
	}*/

	public String getName() {
		return name;
	}
	public String getTime(){ return time; }
	public String getItem() { return item; }

	public void setName(String name) {
		this.name = name;
	}
	public void setTime(String time){ this.time = time; }
	public void setItem(String number) {
		this.item = number;
	}

}
