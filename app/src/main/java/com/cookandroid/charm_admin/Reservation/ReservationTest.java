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
import com.cookandroid.charm_admin.ReservationList.ReservationListActivity;
import solar.blaz.date.week.WeekDatePicker;

/**
 * Created by HP on 2016-09-21.
 */
public class ReservationTest extends Activity {
    ListView listCut,listColor,listPerm,listMagic,listClinic;
    PriceListAdapter adapterCut,adapterColor,adapterPerm,adapterMagic,adapterClinic;
    int listviewPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("예약화면 테스트");
        setContentView(R.layout.activity_reservationitemlist);

        addCut();
        addColor();
        addPerm();
        addMagic();
        addClinlic();
        clicklist();

    }

    private void intentselectTime(String Name, String Price,String Time){
        Intent selectTimeIntent = new Intent(getApplicationContext(),ReservationListActivity.class);
        selectTimeIntent.putExtra("Name",Name);
        selectTimeIntent.putExtra("Price",Price);
        selectTimeIntent.putExtra("Time",Time);
        startActivity(selectTimeIntent);
    }

    private void clicklist(){
        listCut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListData item = adapterCut.mPriceListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                                item.getTv_ItemPrice().toString(),
                                item.getTv_ItemTime().toString());

            }
        });

        listColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListData item = adapterColor.mPriceListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                        item.getTv_ItemPrice().toString(),
                        item.getTv_ItemTime().toString());

                listviewPosition = i;

            }
        });

        listPerm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListData item = adapterPerm.mPriceListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                        item.getTv_ItemPrice().toString(),
                        item.getTv_ItemTime().toString());

                listviewPosition = i;
            }
        });

        listMagic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListData item = adapterMagic.mPriceListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                        item.getTv_ItemPrice().toString(),
                        item.getTv_ItemTime().toString());

                listviewPosition = i;
            }
        });

        listClinic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PriceListData item = adapterClinic.mPriceListData.get(i);

                intentselectTime(item.getTv_ItemName().toString(),
                        item.getTv_ItemPrice().toString(),
                        item.getTv_ItemTime().toString());

                listviewPosition = i;
            }
        });
    }

    private void addCut(){

        adapterCut = new PriceListAdapter();

        listCut = (ListView)findViewById(R.id.reservation_list_cut);

        listCut.setAdapter(adapterCut);  // 리스트 뷰에 adapter 를 등록한다

        adapterCut.addItem("어린이커트","7000","30"+"분");
        adapterCut.addItem("학생커트","8000","30"+"분");
        adapterCut.addItem("남성커트","10000","30"+"분");
        adapterCut.addItem("여성커트","10000","60"+"분");
        setListViewHeightBasedOnChildren(listCut,adapterCut);
    }

    private void addColor(){

        adapterColor = new PriceListAdapter();
        listColor = (ListView)findViewById(R.id.reservation_list_color);
        listColor.setAdapter(adapterColor);  // 리스트 뷰에 adapter 를 등록한다

        adapterColor.addItem("일반염색","35000","120"+"분");
        adapterColor.addItem("왁싱","35000","60"+"분");
        adapterColor.addItem("매니큐어","35000","60"+"분");

        setListViewHeightBasedOnChildren(listColor,adapterColor);

    }

    private void addPerm(){

        adapterPerm = new PriceListAdapter();
        listPerm = (ListView)findViewById(R.id.reservation_list_perm);
        listPerm.setAdapter(adapterPerm);  // 리스트 뷰에 adapter 를 등록한다

        adapterPerm.addItem("일반펌","30000","120"+"분");
        adapterPerm.addItem("웰빙펌","30000","120"+"분");
        adapterPerm.addItem("디지털/세팅펌","45000","120"+"분");


        setListViewHeightBasedOnChildren(listPerm,adapterPerm);

    }

    private void addMagic(){

        adapterMagic = new PriceListAdapter();
        listMagic = (ListView)findViewById(R.id.reservation_list_magic);
        listMagic.setAdapter(adapterMagic);  // 리스트 뷰에 adapter 를 등록한다

        adapterMagic.addItem("일반스트레이트펌","35000","60"+"분");
        adapterMagic.addItem("일반매직","40000","60"+"분");
        adapterMagic.addItem("볼륨매직","50000","120"+"분");
        adapterMagic.addItem("클리닉매직","80000","120"+"분");

        setListViewHeightBasedOnChildren(listMagic,adapterMagic);

    }

    private void addClinlic(){
        adapterClinic = new PriceListAdapter();

        listClinic = (ListView)findViewById(R.id.reservation_list_clinic);
        listClinic.setAdapter(adapterClinic);  // 리스트 뷰에 adapter 를 등록한다

        adapterClinic.addItem("손상회복클리닉","30000","60"+"분");
        adapterClinic.addItem("두피클리닉","25000","90"+"분");

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
}
