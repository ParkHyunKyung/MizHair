package com.cookandroid.charm_admin.Notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cookandroid.charm_admin.R;

import java.util.ArrayList;

/**
 * Created by HP on 2016-09-11.
 */
public class NoticeAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<NoticeListData> listViewItemList = new ArrayList<NoticeListData>() ;

    // ListViewAdapter의 생성자
    public NoticeAdapter() {

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
            convertView = inflater.inflate(R.layout.notice_row, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.tvNoticeTitle) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.tvNoticeContent) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.Notice_txtDate) ;
        ImageView btnImageView = (ImageView)convertView.findViewById(R.id.Notice_imgBtn);
        Button btnDelete = (Button)convertView.findViewById(R.id.notice_btnDelete);
        Button btnNotify = (Button)convertView.findViewById(R.id.notice_btnNotify);
        final LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.notice_layout);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final NoticeListData listViewItem = listViewItemList.get(position);

        //버튼 클릭 이벤트 넣기
        btnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout.getVisibility() == View.GONE) {
                    layout.setVisibility(View.VISIBLE);
                } else {
                    layout.setVisibility(View.GONE);
                }
            }
        });
        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTvNoticeTitle());
        descTextView.setText(listViewItem.getTvNoticeContent());
        dateTextView.setText(listViewItem.getTvNoticeDate());

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
    public void addItem(String num, String title, String content,String Date) {
        NoticeListData item = new NoticeListData();
        item.setTvNum(num);
        item.setTvNoticeTitle(title);
        item.setTvNoticeContent(content);
        item.setTvNoticeDate(Date);

        listViewItemList.add(item);
    }
}


