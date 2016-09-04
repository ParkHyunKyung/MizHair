package com.cookandroid.charm_admin.Reservation;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;

import java.util.ArrayList;

/**
 * Created by HP on 2016-07-06.
 */
public class ReservationActivity extends Activity{
    private ArrayList<String> arrCheckBox; //이름 배열
    private ArrayList<Integer> arrPrise; //가격 배열

    private EditText edtPrise,edtAMPM,edtDate,edtTime,edtMileage,edtMemo;
    int Prise = 0 , Time = 0, Mileage = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        edtPrise = (EditText)findViewById(R.id.edtPrise);
        edtAMPM = (EditText)findViewById(R.id.edtAMPM);
        edtDate = (EditText)findViewById(R.id.edtDate);
        edtTime = (EditText)findViewById(R.id.edtTime);
        edtMileage = (EditText)findViewById(R.id.edtMileage);
        edtMemo = (EditText)findViewById(R.id.edtMemo);

        // 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(multiple choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, items) ;

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        arrCheckBox = new ArrayList<>();
        arrCheckBox = getIntent().getStringArrayListExtra("arrCheckBox");
        arrPrise = new ArrayList<>();
        arrPrise = getIntent().getIntegerArrayListExtra("arrPrise");
        // 예상 소요 시간
        /*arrTime = new ArrayList<>();
        arrTime = getIntent().getIntegerArrayListExtra("arrPrise");*/


        for(int i=0;i<arrCheckBox.size();i++){
            // 아이템 추가.
            items.add(arrCheckBox.get(i).toString()+"("+arrPrise.get(i).toString()+")");
            // listview 갱신
            adapter.notifyDataSetChanged();
        }

        /*시술 리스트 클릭시 발생 이벤트*/
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /*Toast.makeText(getApplicationContext(),items.get(i)+"가격:"+arrPrise.get(i).toString(),Toast.LENGTH_SHORT).show();*/

                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                int count = adapter.getCount() ;

                if (checkedItems.get(i)) {
                    /*Toast.makeText(getApplicationContext(),items.get(i),Toast.LENGTH_SHORT).show();*/
                    Prise = Prise + arrPrise.get(i);
                    edtPrise.setText(Integer.toString(Prise));
                    /*edtTime.setText();*/
                    edtMileage.setText(Integer.toString(Prise/20)+"p");
                }else {
                    Prise = Prise - arrPrise.get(i);
                    edtPrise.setText(Integer.toString(Prise));
                    /*edtTime.setText();*/
                    edtMileage.setText(Integer.toString(Prise/20)+"p");
                }


            }
        });


    }
}
