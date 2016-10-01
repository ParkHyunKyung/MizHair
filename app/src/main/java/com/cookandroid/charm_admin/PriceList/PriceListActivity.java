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
    PriceListAdapter adapterCut,adapterColor,adapterPerm,adapterMagic,adapterClinic;

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

                        adapterCut.remove(listviewPosition);
                        setListViewHeightBasedOnChildren(listCut, adapterCut);

                    } else if (selectedSpinner.toString().equals("Color/컬러염색")) {

                        adapterColor.remove(listviewPosition);
                        setListViewHeightBasedOnChildren(listColor, adapterColor);

                    } else if (selectedSpinner.toString().equals("Perm/펌")) {

                        adapterPerm.remove(listviewPosition);
                        setListViewHeightBasedOnChildren(listPerm, adapterPerm);

                    } else if (selectedSpinner.toString().equals("Magic and Straight/매직 또는 스트레이트")) {

                        adapterMagic.remove(listviewPosition);
                        setListViewHeightBasedOnChildren(listMagic, adapterMagic);

                    } else if (selectedSpinner.toString().equals("Clinic/클리닉")) {

                        adapterClinic.remove(listviewPosition);
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
                PriceListData item = adapterCut.mPriceListData.get(i);

                edtName.setText(item.getTv_ItemName().toString());
                edtPrice.setText(item.getTv_ItemPrice().toString());
                edtTime.setText(item.getTv_ItemTime().toString());

                listviewPosition = i;

                sStyleKind.setSelection(0);
            }
        });

        listColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListData item = adapterColor.mPriceListData.get(i);

                edtName.setText(item.getTv_ItemName().toString());
                edtPrice.setText(item.getTv_ItemPrice().toString());
                edtTime.setText(item.getTv_ItemTime().toString());

                listviewPosition = i;
                sStyleKind.setSelection(1);

            }
        });

        listPerm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListData item = adapterPerm.mPriceListData.get(i);

                edtName.setText(item.getTv_ItemName().toString());
                edtPrice.setText(item.getTv_ItemPrice().toString());
                edtTime.setText(item.getTv_ItemTime().toString());

                listviewPosition = i;

                sStyleKind.setSelection(2);
            }
        });

        listMagic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListData item = adapterMagic.mPriceListData.get(i);

                edtName.setText(item.getTv_ItemName().toString());
                edtPrice.setText(item.getTv_ItemPrice().toString());
                edtTime.setText(item.getTv_ItemTime().toString());

                listviewPosition = i;

                sStyleKind.setSelection(3);
            }
        });

        listClinic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListData item = adapterClinic.mPriceListData.get(i);

                edtName.setText(item.getTv_ItemName().toString());
                edtPrice.setText(item.getTv_ItemPrice().toString());
                edtTime.setText(item.getTv_ItemTime().toString());

                listviewPosition = i;

                sStyleKind.setSelection(4);
            }
        });
    }

    private void addCut(){

        adapterCut = new PriceListAdapter();

        listCut = (ListView)findViewById(R.id.list_cut);

        listCut.setAdapter(adapterCut);  // 리스트 뷰에 adapter 를 등록한다

        connection("컷",adapterCut);
        setListViewHeightBasedOnChildren(listCut,adapterCut);
    }

    private void addColor(){

        adapterColor = new PriceListAdapter();
        listColor = (ListView)findViewById(R.id.list_color);
        listColor.setAdapter(adapterColor);  // 리스트 뷰에 adapter 를 등록한다

        connection("염색",adapterColor);

        setListViewHeightBasedOnChildren(listColor,adapterColor);

    }

    private void addPerm(){

        adapterPerm = new PriceListAdapter();
        listPerm = (ListView)findViewById(R.id.list_perm);
        listPerm.setAdapter(adapterPerm);  // 리스트 뷰에 adapter 를 등록한다

        connection("펌",adapterPerm);

        setListViewHeightBasedOnChildren(listPerm,adapterPerm);

    }

    private void addMagic(){

        adapterMagic = new PriceListAdapter();
        listMagic = (ListView)findViewById(R.id.list_magic);
        listMagic.setAdapter(adapterMagic);  // 리스트 뷰에 adapter 를 등록한다

        connection("스타일링",adapterMagic);

        setListViewHeightBasedOnChildren(listMagic,adapterMagic);

    }

    private void addClinlic(){
        adapterClinic = new PriceListAdapter();

        listClinic = (ListView)findViewById(R.id.list_clinic);
        listClinic.setAdapter(adapterClinic);  // 리스트 뷰에 adapter 를 등록한다

        connection("클리닉",adapterClinic);

        setListViewHeightBasedOnChildren(listClinic,adapterClinic);
    }

    //리스트뷰에 높이를 계산하기 위한 메소드
    public static void setListViewHeightBasedOnChildren(ListView listview, PriceListAdapter adapter) {
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        int totalHeight = 105;

        params.height = totalHeight*adapter.getCount();
        listview.setLayoutParams(params);
        listview.requestLayout();
    }

    private void connection(String styleName,PriceListAdapter adapter) {

        String LoginServer = "http://118.36.3.200/menu.php";
        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();

            JSONObject state = new JSONObject(result);
            JSONArray var = state.getJSONArray(styleName);

            for (int i=0;i<var.length();i++){
                JSONObject varTest = new JSONObject(var.get(i).toString());// 한줄
                String Name = varTest.getString("StName");// StName에 해당하는 이름 가져옴
                String Price = varTest.getString("StPrice");// StPrice에 해당하는 이름 가져옴
                String Time = varTest.getString("StTime");// StTime에 해당하는 이름 가져옴
                adapter.addItem(Name,Price,Time);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteInServer(String name) {

        String LoginServer = "http://118.36.3.200/menu.php"+name;
        URLConnector task = new URLConnector(LoginServer);
        task.start();

        try {
            task.join();
            String result = task.getResult();

            if (result.equals(Integer.toString(0))){
                Toast.makeText(getApplicationContext(),result.toString()+"성공",Toast.LENGTH_SHORT).show();
            }else if (result.equals(Integer.toString(1))){
                Toast.makeText(getApplicationContext(),result.toString()+"실패",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}