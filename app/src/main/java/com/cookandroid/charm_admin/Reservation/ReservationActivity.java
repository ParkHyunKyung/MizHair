package com.cookandroid.charm_admin.Reservation;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cookandroid.charm_admin.R;

import java.util.ArrayList;

/**
 * Created by HP on 2016-07-06.
 */
public class ReservationActivity extends Activity{
    private LinearLayout selectHair_layout;
    private ArrayList<String> arrCheckBox; //체크박스
    private ArrayList<Integer> arrPrise; //체크박스
    private CheckBox checkBox;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        arrCheckBox = new ArrayList<>();
        arrCheckBox = getIntent().getStringArrayListExtra("arrCheckBox");
        arrPrise = new ArrayList<>();
        arrPrise = getIntent().getIntegerArrayListExtra("arrPrise");


        for(int i=0;i<arrCheckBox.size();i++){

            String[] checkBoxName = {arrCheckBox.get(i).toString()+"("+arrPrise.get(i).toString()+")"};
            makeCheckBox(checkBoxName);
        }

    }

    int idNum = 0;
    protected void makeCheckBox(String[] strArray){
        selectHair_layout = (LinearLayout)findViewById(R.id.selectHair_layout);
        checkBox = new CheckBox(this);
        checkBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        for(int i=0;i<strArray.length;i++) {
            checkBox.setText(strArray[0].toString());
            selectHair_layout.addView(checkBox);
            checkBox.setId(idNum);
            idNum++;
        }
    }
}
