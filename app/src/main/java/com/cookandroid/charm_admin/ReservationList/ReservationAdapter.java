package com.cookandroid.charm_admin.ReservationList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cookandroid.charm_admin.Notice.NoticeListData;
import com.cookandroid.charm_admin.R;

import java.util.ArrayList;

/**
 * Created by HP on 2016-09-11.
 */
public class ReservationAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ReservationData> listViewItemList = new ArrayList<ReservationData>() ;

    // ListViewAdapter의 생성자
    public ReservationAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reservation_list_row, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView reservationName = (TextView) convertView.findViewById(R.id.tvName);
        TextView reservationItem = (TextView) convertView.findViewById(R.id.tvItem);
        TextView reservationTime = (TextView) convertView.findViewById(R.id.tvTime);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ReservationData listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        reservationName.setText(listViewItem.getName());
        reservationItem.setText(listViewItem.getItem());
        reservationTime.setText(listViewItem.getTime());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String name, String style, String time) {
        ReservationData item = new ReservationData();
        item.setName(name);
        item.setItem(style);
        item.setTime(time);

        listViewItemList.add(item);
    }

}
