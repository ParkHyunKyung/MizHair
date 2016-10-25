package com.cookandroid.charm_admin.Reservation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.cookandroid.charm_admin.PriceList.PriceListAdapter;
import com.cookandroid.charm_admin.PriceList.PriceListData;
import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.ReservationList.ReservationAdapter;
import com.cookandroid.charm_admin.ReservationList.ReservationData;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by HP on 2016-09-21.
 */
public class ReservationActivity extends Activity {
    ListView listCut,listColor,listPerm,listMagic,listClinic;
    ReservationItemListAdapter adapterCut;
    ReservationItemListAdapter adapterColor;
    ReservationItemListAdapter adapterPerm;
    ReservationItemListAdapter adapterMagic;
    ReservationItemListAdapter adapterClinic;
    int listviewPosition;
    String UserName,UserNum,UserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationitemlist);
        setTitle("예약화면 테스트");
        UserName  = getIntent().getStringExtra("UserName");
        UserNum = getIntent().getStringExtra("UserNum");
        UserPhone = getIntent().getStringExtra("UserPhone");

        addCut();
        addColor();
        addPerm();
        addMagic();
        addClinlic();
        clicklist();

    }

    private void intentselectTime(String Name, String Price,String Time,String Num){
        Intent selectTimeIntent = new Intent(getApplicationContext(),ReservationTimeActivity.class);
        selectTimeIntent.putExtra("UserName",UserName);
        selectTimeIntent.putExtra("Name",Name);
        selectTimeIntent.putExtra("Price",Price);
        selectTimeIntent.putExtra("Time",Time);
        selectTimeIntent.putExtra("StNum",Num);
        selectTimeIntent.putExtra("UserNum",UserNum);
        selectTimeIntent.putExtra("UserPhone",UserPhone);
        startActivity(selectTimeIntent);
    }

    private void clicklist(){
        listCut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ReservationItemListData item = adapterCut.mReservationItemListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                                item.getTv_ItemPrice().toString(),
                                item.getTv_ItemTime().toString()
                                ,item.getTv_ItemNum());
                finish();
            }
        });

        listColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReservationItemListData item = adapterColor.mReservationItemListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                        item.getTv_ItemPrice().toString(),
                        item.getTv_ItemTime().toString()
                        ,item.getTv_ItemNum());
                finish();
            }
        });

        listPerm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReservationItemListData item = adapterPerm.mReservationItemListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                        item.getTv_ItemPrice().toString(),
                        item.getTv_ItemTime().toString()
                        ,item.getTv_ItemNum());
                finish();
            }
        });

        listMagic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReservationItemListData item = adapterMagic.mReservationItemListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                        item.getTv_ItemPrice().toString(),
                        item.getTv_ItemTime().toString()
                        ,item.getTv_ItemNum());
                finish();
            }
        });

        listClinic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReservationItemListData item = adapterClinic.mReservationItemListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                        item.getTv_ItemPrice().toString(),
                        item.getTv_ItemTime().toString()
                        ,item.getTv_ItemNum());
                finish();
            }
        });
    }

    private void addCut(){

        adapterCut = new ReservationItemListAdapter();

        listCut = (ListView)findViewById(R.id.reservation_list_cut);

        listCut.setAdapter(adapterCut);  // 리스트 뷰에 adapter 를 등록한다

        connection("컷",adapterCut);
        setListViewHeightBasedOnChildren(listCut,adapterCut);
    }

    private void addColor(){

        adapterColor = new ReservationItemListAdapter();
        listColor = (ListView)findViewById(R.id.reservation_list_color);
        listColor.setAdapter(adapterColor);  // 리스트 뷰에 adapter 를 등록한다

        connection("염색",adapterColor);

        setListViewHeightBasedOnChildren(listColor,adapterColor);

    }

    private void addPerm(){

        adapterPerm = new ReservationItemListAdapter();
        listPerm = (ListView)findViewById(R.id.reservation_list_perm);
        listPerm.setAdapter(adapterPerm);  // 리스트 뷰에 adapter 를 등록한다

        connection("펌",adapterPerm);

        setListViewHeightBasedOnChildren(listPerm,adapterPerm);

    }

    private void addMagic(){

        adapterMagic = new ReservationItemListAdapter();
        listMagic = (ListView)findViewById(R.id.reservation_list_magic);
        listMagic.setAdapter(adapterMagic);  // 리스트 뷰에 adapter 를 등록한다

        connection("스타일링",adapterMagic);

        setListViewHeightBasedOnChildren(listMagic,adapterMagic);

    }

    private void addClinlic(){
        adapterClinic = new ReservationItemListAdapter();

        listClinic = (ListView)findViewById(R.id.reservation_list_clinic);
        listClinic.setAdapter(adapterClinic);  // 리스트 뷰에 adapter 를 등록한다

        connection("클리닉",adapterClinic);

        setListViewHeightBasedOnChildren(listClinic,adapterClinic);
    }

    //리스트뷰에 높이를 계산하기 위한 메소드
    public static void setListViewHeightBasedOnChildren(ListView listview, ReservationItemListAdapter adapter) {
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        int totalHeight = 305;

        params.height = totalHeight*adapter.getCount();
        listview.setLayoutParams(params);
        listview.requestLayout();
    }

    private void connection(String styleName,ReservationItemListAdapter adapter) {

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
                String Name = varTest.getString("StName");// StName에 해당하는 값 가져옴
                String Price = varTest.getString("StPrice");// StPrice에 해당하는 값 가져옴
                String Time = varTest.getString("StTime");// StTime에 해당하는 값 가져옴
                String num = varTest.getString("StNum");//StNum에 해당하는 값 가져옴
                adapter.addItem(Name,Price,Time,num);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
