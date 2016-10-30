/*
 * com.ziofront.android.contacts
 * ContactsListActivity.java
 * Jiho Park   2009. 11. 27.
 *
 * Copyright (c) 2009 ziofront.com. All Rights Reserved.
 */
package com.cookandroid.charm_admin.CustomerManagement;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.cookandroid.charm_admin.PriceList.PriceListAdapter;
import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jiho Park
 * 
 */
public class ContactsListActivity extends ListActivity {

	private String searchKeyword;

	/*
	 * 
	 * @return
	 */
	private List<CustomerAdapter> getContactsList() throws Exception {

		List<CustomerAdapter> contactsList = new ArrayList<CustomerAdapter>();

		connection(contactsList);

		return contactsList;
	}

	/*고객의 정보 리스트 추가*/
	private void addContact(List<CustomerAdapter> contactsList, String UserName,
							String UserNum, String UserId, String UserPhone,String UserGender) throws Exception {

		if (contactsList == null) {
			throw new NullPointerException("contactList가 null 입니다.");
		}

		boolean isAdd = false;

		/*검색창의 글 입력받았을 경우*/
		if (searchKeyword != null && "".equals(searchKeyword.trim()) == false) {

			String iniName = HangulUtils.getHangulInitialSound(UserName,
					searchKeyword);

			if (iniName.indexOf(searchKeyword) >= 0) {
				isAdd = true;
			}
		} else {
			isAdd = true;
		}

		if (isAdd) {
			contactsList.add(new CustomerAdapter(UserName,UserNum,UserId,UserPhone,UserGender));
		}
	}

	/*리스트뷰를 보여주는 기능
	커스텀된 리스트뷰를 통해 전달되어 화면에 보여줌*/
	private void displayList() throws Exception {

		List<CustomerAdapter> contactsList = null;

		contactsList = getContactsList();

		ContactsListAdapter<CustomerAdapter> adapter = new ContactsListAdapter<CustomerAdapter>(
				this, R.layout.customer_management_list_row, contactsList);
		setListAdapter(adapter);
	}

	/*리스트 어뎁터
	* 입력되는 값들(이름, 전화번호, 날짜)을
	* contacts_list_row의 형태로 커스텀
	* */
	private class ContactsListAdapter<T extends CustomerAdapter> extends
			ArrayAdapter<T> {

		private List<T> contactsList;

		public ContactsListAdapter(Context context, int textViewResourceId,
								   List<T> items) {
			super(context, textViewResourceId, items);
			contactsList = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.customer_management_list_row, null);
			}

			final T contacts = contactsList.get(position);
			if (contacts != null) {
				TextView viewName = (TextView) view.findViewById(R.id.tvName);
				if (viewName != null) {
					viewName.setText(contacts.getUserName());
				}

				TextView viewNumber = (TextView) view.findViewById(R.id.tvPhoneNum);
				if (viewNumber != null) {
					viewNumber.setText("전화번호 : " + contacts.getUserPhone());
				}

				TextView viewDate = (TextView) view.findViewById(R.id.tvDate);
				if (viewDate != null) {
					viewDate.setText("아이디 : " + contacts.getUserId());
				}
			}

			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					/*Toast.makeText(getApplicationContext(),contacts.getName(),Toast.LENGTH_SHORT).show();*/
					Intent customerintent = new Intent(getApplicationContext(), CustomerActivity.class);

					customerintent.putExtra("UserName",contacts.getUserName());
					customerintent.putExtra("UserPhone",contacts.getUserPhone());
					customerintent.putExtra("UserId",contacts.getUserId());
					customerintent.putExtra("UserGender",contacts.getUserGender());
					customerintent.putExtra("UserNum",contacts.getUserNum());
					startActivity(customerintent);
				}
			});
			return view;
		}
	}

	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_contactslist);
		setTitle("고객관리");

		EditText searchBox = (EditText) findViewById(R.id.EditText01);

		try {
			searchBox.addTextChangedListener(new TextWatcher() {

				public void afterTextChanged(Editable arg0) {
					// ignore
				}

				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// ignore
				}

				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

					try {
						searchKeyword = s.toString();
						displayList();
					} catch (Exception e) {
						Log.e("", e.getMessage(), e);
					}
				}

			});
			displayList();
		} catch (Exception e) {
			Log.e("", e.getMessage(), e);
		}
	}

	private void connection(List<CustomerAdapter> contactsList) {

		String LoginServer = "http://mizhair.ga/showUsers.php?Pass=12345";
		URLConnector task = new URLConnector(LoginServer);
		task.start();

		try {
			task.join();
			String result = task.getResult();
			JSONArray var = new JSONArray(result);

			for (int i=1;i<var.length();i++){
				JSONObject varTest = new JSONObject(var.get(i).toString());// 한줄
				String UserNum = varTest.getString("UserNum");
				String UserID = varTest.getString("UserID");
				String UserName = varTest.getString("UserName");
				String UserPhone = varTest.getString("UserPhone");
				String UserGender = varTest.getString("UserGender");
				addContact(contactsList, UserName, UserNum, UserID, UserPhone, UserGender);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}