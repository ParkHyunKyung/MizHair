package com.cookandroid.charm_admin.PriceList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cookandroid.charm_admin.R;
import com.cookandroid.charm_admin.Server.URLConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jungminki on 2016-07-07.
 * 요금표를 보여주는 액티비티
 * 엑션바에 뒤로가기 버튼 구현
 * 테이블은 첫 행의 메뉴, 금액은 xml에 작성
 * 그 후의 행은 서버에서 요금표 데이터를 가져와서 동적으로 생성
 * 수정버튼 클릭 시 행의 객체를 TextView에서 EditText로 새로 그리게 되며
 * 수정버튼은 숨겨지고 완료버튼, 메뉴추가 버튼이 생긴다.
 * 완료버튼 클릭 시 EditText의 값을 서버로 전송한다.
 * 수정완료 시 메뉴 또는 요금에 아무런 값이 들어가 있지 않으면 메뉴가 삭제된다.
 */
public class PriceListActivity extends AppCompatActivity {

    LinearLayout btnLayout; //요금표 버튼 레이아웃
    TableLayout tableLayout; //요금표 테이블 레이아웃
    Button btnEdit; //수정 버튼
    ArrayList<String> strMenu, strFemalePrice, strMalePrice, strTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricelist);
        setTitle("이용요금표");

        btnLayout = (LinearLayout) findViewById(R.id.pricelist_btnLayout);
        tableLayout = (TableLayout) findViewById(R.id.pricelist_tableLayout);
        btnEdit = (Button) findViewById(R.id.pricelist_btnEdit);

        //getPriceListData(); //서버에서 요금표 데이터를 가져온다.

        //테스트용으로 strMenu 및 strprice에 값이 있다고 가정한다.
        strMenu = new ArrayList<String>();
        strFemalePrice = new ArrayList<String>();
        strMalePrice = new ArrayList<String>();
        strTime = new ArrayList<String>();

        strMenu.add("일반컷");
        strMenu.add("특수컷");
        strMenu.add("청소년컷");
        strMenu.add("기본펌");
        strMenu.add("특수펌");
        strFemalePrice.add("10,000");
        strFemalePrice.add("15,000");
        strFemalePrice.add("7,000");
        strFemalePrice.add("30,000");
        strFemalePrice.add("70,000");
        strMalePrice.add("10,000");
        strMalePrice.add("15,000");
        strMalePrice.add("7,000");
        strMalePrice.add("30,000");
        strMalePrice.add("70,000");
        strTime.add("30");
        strTime.add("60");
        strTime.add("30");
        strTime.add("60");
        strTime.add("70");


        //서버에서 받은 데이터 길이 만큼 setTableLayoutRow 수행
        for (int i = 0; i < strMenu.size(); i++) {
            String[] data = new String[4];
            data[0] = strMenu.get(i);
            data[1] = strFemalePrice.get(i);
            data[2] = strMalePrice.get(i);
            data[3] = strTime.get(i);
            addTableLayoutRow(tableLayout, data);
        }

        //이 위에까지가 클라이언트 기능, 이 아래부터는 admin 기능

        //수정 버튼을 클릭시 테이블 레이아웃을 초기화하고
        //서버에서 받은 데이터 길이 만큼 setTableLayoutRowEdit 수행
        //수정버튼은 감추고 완료버튼, 메뉴추가 버튼 생성
        //완료버튼 클릭 시 데이터를 서버에게 전송 및 초기화면 출력
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> strEditMenu = strMenu;
                ArrayList<String> strEditFemalePrice = strFemalePrice;
                final ArrayList<String> strEditMalePrice = strMalePrice;
                final ArrayList<String> strEditTime = strTime;
                removeTableLayout(tableLayout);
                for (int i = 0; i < strEditMenu.size(); i++) {
                    String[] data = new String[4];
                    data[0] = strEditMenu.get(i);
                    data[1] = strEditFemalePrice.get(i);
                    data[2] = strEditMalePrice.get(i);
                    data[3] = strEditTime.get(i);
                    addTableLayoutRowEdit(tableLayout, data);
                }

                btnEdit.setVisibility(View.GONE);

                final Button btnOk = new Button(getApplicationContext());
                btnOk.setText("완료");
                btnOk.setMinimumHeight(100);
                btnOk.setMinimumWidth(50);
                btnLayout.addView(btnOk);

                final Button btnMenuAdd = new Button(getApplicationContext());
                btnMenuAdd.setText("메뉴추가");
                btnMenuAdd.setMinimumHeight(100);
                btnMenuAdd.setMinimumWidth(50);
                btnLayout.addView(btnMenuAdd);

                final Button btnCencle = new Button(getApplicationContext());
                btnCencle.setText("취소");
                btnCencle.setMinimumHeight(100);
                btnCencle.setMinimumWidth(50);
                btnLayout.addView(btnCencle);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        strMenu.clear();
                        strFemalePrice.clear();
                        strMalePrice.clear();
                        strTime.clear();
                        for (int i = 1; i < tableLayout.getChildCount(); i++) {
                            TableRow tr = (TableRow) tableLayout.getChildAt(i);
                            for (int j = 0; j < tr.getChildCount(); j++) {
                                EditText et = (EditText) tr.getChildAt(j);
                                if (et.getText().toString().equals("")) {
                                    continue;
                                }
                                if (j == 0) {
                                    strMenu.add(et.getText().toString());
                                } else if (j == 1) {
                                    strFemalePrice.add(et.getText().toString());
                                } else if (j == 2) {
                                    strEditMalePrice.add(et.getText().toString());
                                } else {
                                    strEditTime.add(et.getText().toString());
                                }
                            }
                        }

                        //테스트용 코드
                        removeTableLayout(tableLayout);
                        for (int i = 0; i < strMenu.size(); i++) {
                            String[] data = new String[4];
                            data[0] = strMenu.get(i);
                            data[1] = strFemalePrice.get(i);
                            data[2] = strMalePrice.get(i);
                            data[3] = strTime.get(i);
                            addTableLayoutRow(tableLayout, data);
                        }
                        btnEdit.setVisibility(View.VISIBLE);
                        btnLayout.removeView(btnOk);
                        btnLayout.removeView(btnMenuAdd);
                        btnLayout.removeView(btnCencle);

                        //요금표 정보를 서버에 전송한다.
                        //메소드를 제작해야하나 서버 완료될 시 작성

                        //서버가 완료되면 아래코드 사용
                        //Intent intent = new Intent(getApplicationContext(), PriceListActivity.class);
                        // startActivity(intent);
                        // finish();
                    }
                });

                btnMenuAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] strTemp = new String[4];
                        strTemp[0] = "메뉴";
                        strTemp[1] = "여자가격";
                        strTemp[2] = "남자가격";
                        strTemp[3] = "소요시간";
                        addTableLayoutRowEdit(tableLayout, strTemp);
                        return;
                    }
                });

                btnCencle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeTableLayout(tableLayout);
                        for (int i = 0; i < strMenu.size(); i++) {
                            String[] data = new String[4];
                            data[0] = strMenu.get(i);
                            data[1] = strFemalePrice.get(i);
                            data[2] = strMalePrice.get(i);
                            data[3] = strTime.get(i);
                            addTableLayoutRow(tableLayout, data);
                        }
                        btnEdit.setVisibility(View.VISIBLE);
                        btnLayout.removeView(btnOk);
                        btnLayout.removeView(btnMenuAdd);
                        btnLayout.removeView(btnCencle);
                        return;
                    }
                });
            }
        });
    }

    /**
     * 서버에서 요금표를 가져오는 메소드
     * ArrayList<String> 변수에 메뉴 및 요금을 순서대로 넣는다.
     */
    private void getPriceListData() {
        String serverQuery = "서버에게 전송할 쿼리";
        URLConnector task = new URLConnector(serverQuery);
        task.start();

        try {
            strMenu = new ArrayList<String>();
            strFemalePrice = new ArrayList<String>();
            strMalePrice = new ArrayList<String>();
            strTime = new ArrayList<String>();

            task.join();
            String result = task.getResult();
            JSONObject root = new JSONObject(result); //서버 메시지
            String state = root.getString("state"); //상태 정보

            //만약 상태정보가 이상이 있을 시 바로 종료
            if (state.equals("0")) {
                return;
            }

            JSONArray list = new JSONArray("list"); //요금표 리스트

            //메뉴와 요금을 ArrayList<String> 변수에 넣는다.
            for (int i = 0; i < list.length(); i++) {
                JSONObject jsonObject = list.getJSONObject(i);
                strMenu.add(jsonObject.getString("StName")); //메뉴
                strFemalePrice.add(jsonObject.getString("StPrice")); //여자가격
                strMalePrice.add(jsonObject.getString("StmPrice")); //남자가격
                strTime.add(jsonObject.getString("StTime")); //소요시
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 테이블 레이아웃에 TextView Row를 추가하는 메소드
     * Row를 롱 클릭하면 삭제할 것 인지 창이 뜨며 확인을 누를 시 Row 삭제
     *
     * @param tableLayout Row값을 추가할 테이블 레이아웃
     * @param strarray    Row값에 들어갈 데이터
     */
    private void addTableLayoutRow(TableLayout tableLayout, String[] strarray) {
        TextView[] tv = new TextView[strarray.length];
        for (int i = 1; i < strarray.length + 1; i++) {
            tv[i - 1] = new TextView(this);
            tv[i - 1].setText(strarray[i - 1].toString());
            tv[i - 1].setGravity(Gravity.CENTER);

            if ((i != 0 && i % 4 == 0)) {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                tr.addView(tv[i - 4], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));
                tr.addView(tv[i - 3], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));
                tr.addView(tv[i - 2], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));
                tr.addView(tv[i - 1], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));
                tableLayout.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.FILL_PARENT));
            }
        }
    }

    /**
     * 테이블 레이아웃에 EditText Row를 추가하는 메소드
     * admin 기능
     *
     * @param tableLayout Row값을 추가할 테이블 레이아웃
     * @param starry    Row값에 들어갈 데이터
     */
    private void addTableLayoutRowEdit(TableLayout tableLayout, String[] starry) {
        EditText[] tv = new EditText[starry.length];
        for (int i = 1; i < starry.length + 1; i++) {
            tv[i - 1] = new EditText(this);
            tv[i - 1].setText(starry[i - 1].toString());
            tv[i - 1].setGravity(Gravity.CENTER);

            if ((i != 0 && i % 4 == 0)) {
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                tr.addView(tv[i - 4], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));
                tr.addView(tv[i - 3], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));
                tr.addView(tv[i - 2], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));
                tr.addView(tv[i - 1], new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));
                tableLayout.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.FILL_PARENT));
            }
        }
    }

    /**
     * 요금표 첫 행만 제외하고 모든 행을 삭제하는 메소드
     *
     * @param tableLayout
     */
    private void removeTableLayout(TableLayout tableLayout) {
        int count = tableLayout.getChildCount();
        for(int i = count -1; i>0;i--){
            tableLayout.removeViewAt(i);
        }
    }
}