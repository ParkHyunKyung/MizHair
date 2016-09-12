package com.cookandroid.charm_admin.PriceList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HK on 2016-07-07.
 * 요금표를 보여주는 액티비티
 * 리스트뷰를 커스텀하기 위해 생성한 price_list_row와 PriceAdapter 연결
 * 그 후의 행은 서버에서 요금표 데이터를 가져와서 동적으로 생성
 * 수정 버튼 클릭시 발생하는 이벤트는 아직 미정
 */
public class PriceListActivity extends AppCompatActivity {

    ListView listCut,listColor,listPerm,listMagic,listClinic;

    LinearLayout btnLayout,layout_modify; //요금표 버튼 레이아웃
    PriceAdapter adapterCut,adapterColor,adapterPerm,adapterMagic,adapterClinic;

    Button btnEdit, btnAdd,btnDelete, btnOK; //수정 버튼, 추가버튼, 완료버튼
    Spinner sStyleKind;
    ArrayAdapter sAdapter;
    String selectedSpinner = "Cut/컷";
    int listviewPosition;

    EditText edtName,edtTime,edtPrice;
    /*ArrayList<String> strMenu, strPrice, strTime;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricelist);
        setTitle("이용요금표");

        layout_modify=(LinearLayout)findViewById(R.id.layout_modify);
        btnLayout = (LinearLayout) findViewById(R.id.pricelist_btnLayout);
        btnEdit = (Button) findViewById(R.id.pricelist_btnEdit);
        sStyleKind = (Spinner)findViewById(R.id.styleKind);
        sAdapter = ArrayAdapter.createFromResource(this,R.array.styleKind,android.R.layout.simple_spinner_dropdown_item);
        sStyleKind.setAdapter(sAdapter);

        sStyleKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSpinner = sAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edtName = (EditText)findViewById(R.id.edtName);
        edtTime = (EditText)findViewById(R.id.edtTime);
        edtPrice = (EditText)findViewById(R.id.edtPrice);
        btnAdd = (Button)findViewById(R.id.btn_add);
        btnDelete = (Button)findViewById(R.id.btn_delete);
        btnOK = (Button)findViewById(R.id.btn_Ok);

        addCut();
        addColor();
        addPerm();
        addMagic();
        addClinlic();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_modify.setVisibility(View.VISIBLE);
/*                listCut.setClickable(true);
                listPerm.setClickable(true);
                listMagic.setClickable(true);
                listColor.setClickable(true);
                listClinic.setClickable(true);*/
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String price = edtPrice.getText().toString();
                String time = edtTime.getText().toString();

                Toast.makeText(getApplicationContext(),"추가",Toast.LENGTH_SHORT).show();
                if(name.equals("")||price.equals("")||time.equals("")){
                    Toast.makeText(getApplicationContext(),"모두 입력해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    if(selectedSpinner.toString().equals("Cut/컷")){

                        adapterCut.addItem(name,price,time+"분");
                        setListViewHeightBasedOnChildren(listCut,adapterCut);

                    }else if(selectedSpinner.toString().equals("Color/컬러염색")){

                        adapterColor.addItem(name,price,time+"분");
                        setListViewHeightBasedOnChildren(listColor,adapterColor);

                    }else if(selectedSpinner.toString().equals("Perm/펌")){

                        adapterPerm.addItem(name,price,time+"분");
                        setListViewHeightBasedOnChildren(listPerm,adapterPerm);

                    }else if(selectedSpinner.toString().equals("Magic and Straight/매직 또는 스트레이트")){

                        adapterMagic.addItem(name,price,time+"분");
                        setListViewHeightBasedOnChildren(listMagic,adapterMagic);

                    }else if(selectedSpinner.toString().equals("Clinic/클리닉")){

                        adapterClinic.addItem(name,price,time+"분");
                        setListViewHeightBasedOnChildren(listClinic,adapterClinic);

                    }else {
                        Toast.makeText(getApplicationContext(),"저장 실패",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtName.getText().toString();
                String price = edtPrice.getText().toString();
                String time = edtTime.getText().toString();

                Toast.makeText(getApplicationContext(),"삭제",Toast.LENGTH_SHORT).show();
                if(name.equals("")||price.equals("")||time.equals("")){
                    Toast.makeText(getApplicationContext(),"모두 입력해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    if (selectedSpinner.toString().equals("Cut/컷")) {

                        adapterCut.removeItem(listviewPosition);
                        setListViewHeightBasedOnChildren(listCut, adapterCut);

                    } else if (selectedSpinner.toString().equals("Color/컬러염색")) {

                        adapterColor.removeItem(listviewPosition);
                        setListViewHeightBasedOnChildren(listColor, adapterColor);

                    } else if (selectedSpinner.toString().equals("Perm/펌")) {

                        adapterPerm.removeItem(listviewPosition);
                        setListViewHeightBasedOnChildren(listPerm, adapterPerm);

                    } else if (selectedSpinner.toString().equals("Magic and Straight/매직 또는 스트레이트")) {

                        adapterMagic.removeItem(listviewPosition);
                        setListViewHeightBasedOnChildren(listMagic, adapterMagic);

                    } else if (selectedSpinner.toString().equals("Clinic/클리닉")) {

                        adapterClinic.removeItem(listviewPosition);
                        setListViewHeightBasedOnChildren(listClinic, adapterClinic);

                    } else {
                        Toast.makeText(getApplicationContext(), "없는 정보입니다 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_modify.setVisibility(View.INVISIBLE);
/*                listCut.setClickable(false);
                listPerm.setClickable(false);
                listMagic.setClickable(false);
                listColor.setClickable(false);
                listClinic.setClickable(false);*/
            }
        });

        listCut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListItem item = (PriceListItem)adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),"클릭",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),item.getTvItemName().toString(),Toast.LENGTH_SHORT).show();

                edtName.setText(item.getTvItemName().toString());
                edtPrice.setText(item.getTvItemPrice().toString());
                edtTime.setText(item.getTvItemTime().toString());

                listviewPosition = listCut.getCheckedItemPosition();
            }
        });

        listColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListItem item = (PriceListItem)adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),"클릭",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),item.getTvItemName().toString(),Toast.LENGTH_SHORT).show();

                edtName.setText(item.getTvItemName().toString());
                edtPrice.setText(item.getTvItemPrice().toString());
                edtTime.setText(item.getTvItemTime().toString());
            }
        });

        listPerm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListItem item = (PriceListItem)adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),"클릭",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),item.getTvItemName().toString(),Toast.LENGTH_SHORT).show();

                edtName.setText(item.getTvItemName().toString());
                edtPrice.setText(item.getTvItemPrice().toString());
                edtTime.setText(item.getTvItemTime().toString());
            }
        });

        listMagic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListItem item = (PriceListItem)adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),"클릭",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),item.getTvItemName().toString(),Toast.LENGTH_SHORT).show();

                edtName.setText(item.getTvItemName().toString());
                edtPrice.setText(item.getTvItemPrice().toString());
                edtTime.setText(item.getTvItemTime().toString());
            }
        });

        listClinic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListItem item = (PriceListItem)adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),"클릭",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),item.getTvItemName().toString(),Toast.LENGTH_SHORT).show();

                edtName.setText(item.getTvItemName().toString());
                edtPrice.setText(item.getTvItemPrice().toString());
                edtTime.setText(item.getTvItemTime().toString());
            }
        });
    }

    private void addCut(){

        adapterCut = new PriceAdapter();

        listCut = (ListView)findViewById(R.id.list_cut);

        listCut.setAdapter(adapterCut);  // 리스트 뷰에 adapter 를 등록한다

        adapterCut.addItem("어린이커트","7000","30"+"분");
        adapterCut.addItem("학생커트","8000","30"+"분");
        adapterCut.addItem("남성커트","10000","30"+"분");
        adapterCut.addItem("여성커트","10000","60"+"분");
        setListViewHeightBasedOnChildren(listCut,adapterCut);
    }

    private void addColor(){

        adapterColor = new PriceAdapter();
        listColor = (ListView)findViewById(R.id.list_color);
        listColor.setAdapter(adapterColor);  // 리스트 뷰에 adapter 를 등록한다

        adapterColor.addItem("일반염색","35000","120"+"분");
        adapterColor.addItem("왁싱","35000","60"+"분");
        adapterColor.addItem("매니큐어","35000","60"+"분");

        setListViewHeightBasedOnChildren(listColor,adapterColor);

    }

    private void addPerm(){

        adapterPerm = new PriceAdapter();
        listPerm = (ListView)findViewById(R.id.list_perm);
        listPerm.setAdapter(adapterPerm);  // 리스트 뷰에 adapter 를 등록한다

        adapterPerm.addItem("일반펌","30000","120"+"분");
        adapterPerm.addItem("웰빙펌","30000","120"+"분");
        adapterPerm.addItem("디지털/세팅펌","45000","120"+"분");


        setListViewHeightBasedOnChildren(listPerm,adapterPerm);

    }

    private void addMagic(){

        adapterMagic = new PriceAdapter();
        listMagic = (ListView)findViewById(R.id.list_magic);
        listMagic.setAdapter(adapterMagic);  // 리스트 뷰에 adapter 를 등록한다

        adapterMagic.addItem("일반스트레이트펌","35000","60"+"분");
        adapterMagic.addItem("일반매직","40000","60"+"분");
        adapterMagic.addItem("볼륨매직","50000","120"+"분");
        adapterMagic.addItem("클리닉매직","80000","120"+"분");

        setListViewHeightBasedOnChildren(listMagic,adapterMagic);

    }

    private void addClinlic(){
        adapterClinic = new PriceAdapter();

        listClinic = (ListView)findViewById(R.id.list_clinic);
        listClinic.setAdapter(adapterClinic);  // 리스트 뷰에 adapter 를 등록한다

        adapterClinic.addItem("손상회복클리닉","30000","60"+"분");
        adapterClinic.addItem("두피클리닉","25000","90"+"분");

        setListViewHeightBasedOnChildren(listClinic,adapterClinic);
    }

    //리스트뷰에 높이를 계산하기 위한 메소드
    public static void setListViewHeightBasedOnChildren(ListView listview, PriceAdapter adapter) {
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        int totalHeight = 105;

        /*Toast.makeText(getApplicationContext(),Integer.toString(listCut.getHeight()),Toast.LENGTH_SHORT).show();//0*/
        /*Toast.makeText(getApplicationContext(),Integer.toString(adapterCut.getCount()),Toast.LENGTH_SHORT).show();//4
        Toast.makeText(getApplicationContext(),Integer.toString(layout_height),Toast.LENGTH_SHORT).show();//-2
        Toast.makeText(getApplicationContext(),Integer.toString(listCut.getDividerHeight()),Toast.LENGTH_SHORT).show();//2*/

        params.height = totalHeight*adapter.getCount();
        listview.setLayoutParams(params);
        listview.requestLayout();
    }
}