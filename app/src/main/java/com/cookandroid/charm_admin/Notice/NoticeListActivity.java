package com.cookandroid.charm_admin.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;

/**
 * Created by Jungminki on 2016-07-07.
 * 미구현
 */
public class NoticeListActivity extends Activity {

    private Button btnCencle, btnAdd;
    private ListView noticeList;
    private ArrayAdapter<String> noticeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticelist);
        setTitle("공지사항");

        noticeAdapter = new ArrayAdapter(getApplicationContext(),R.layout.notice_layout);

        noticeList = (ListView)findViewById(R.id.notice_list);
        noticeList.setAdapter(noticeAdapter);  // 리스트 뷰에 adapter 를 등록한다

        noticeAdapter.add("공지1");
        noticeAdapter.add("공지2");

        noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);

                /*title = noticeAdapter.getItem(i) 에 해당하는 내용을 서버에서 가져옴*/
                String content = "여기에 공지사항의 내용이 들어가 있습니다\n좀 더 길게 쓴걸 테스트 하기위해 한줄 더씀";

                /*title과 contents를 intent로 NoticeActivity로 전송*/
                intent.putExtra("Add","");
                intent.putExtra("Title",noticeAdapter.getItem(i));
                intent.putExtra("Content", content);
                startActivity(intent);
            }
        });

        btnCencle = (Button)findViewById(R.id.notice_btnCancle);
        btnCencle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd = (Button)findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                String changeName = "저장";
                intent.putExtra("Add",changeName);
                startActivity(intent);
            }
        });
    }
}