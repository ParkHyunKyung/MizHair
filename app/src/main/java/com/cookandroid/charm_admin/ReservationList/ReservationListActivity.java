package com.cookandroid.charm_admin.ReservationList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.charm_admin.CustomerManagement.HangulUtils;
import com.cookandroid.charm_admin.R;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import solar.blaz.date.week.WeekDatePicker;

/**
 * Created by HP on 2016-09-03.
 */
public class ReservationListActivity extends ListActivity{
    private WeekDatePicker datePicker;
    private String searchDate;

    /*
     *
     * @return
     */
    private List<ReservationAdapter> getReservationsList() throws Exception {

        List<ReservationAdapter> ReservationsList = new ArrayList<ReservationAdapter>();

		/*고객의 정보*/
        addReservation(ReservationsList, "김창민", "파마", "2016-09-06");
        addReservation(ReservationsList, "김우진", "염색", "2016-09-06");
        addReservation(ReservationsList, "권동효", "반삭", "2016-09-07" );
        addReservation(ReservationsList, "나수현", "커트", "2016-09-07");
        addReservation(ReservationsList, "홍길동", "어머니를 찾아서", "2016-09-08");

        return ReservationsList;
    }


    /*고객의 정보 리스트 추가*/
    private void addReservation(List<ReservationAdapter> ReservationsList, String name,
                            String item, String date) throws Exception {

        if (ReservationsList == null) {
            throw new NullPointerException("예약리스트가 null 입니다.");
        }

        boolean isAdd = false;

		/*검색창의 글 입력받았을 경우*/
        if (searchDate != null && "".equals(searchDate.trim()) == false) {

            String iniName = HangulUtils.getHangulInitialSound(date,
                    searchDate);

            if (iniName.indexOf(searchDate) >= 0) {
                isAdd = true;
            }
        } else {
            isAdd = true;
        }

        if (isAdd) {
            ReservationsList.add(new ReservationAdapter(name, item, date));
        }
    }

    /*리스트뷰를 보여주는 기능
    커스텀된 리스트뷰를 통해 전달되어 화면에 보여줌*/
    private void displayList() throws Exception {


        TextView tv_NoneCustomer = (TextView)findViewById(R.id.tv_NoneCustomer);

        List<ReservationAdapter> ReservationsList = null;

        ReservationsList = getReservationsList();

        /*Reservation size가 0일때 예약된 고객 없음 보이기*/
        int size = ReservationsList.size();
        if(Integer.toString(size).equals("0")){
            tv_NoneCustomer.setVisibility(View.VISIBLE);
        }else {
            tv_NoneCustomer.setVisibility(View.INVISIBLE);
        }

        ReservationsListAdapter<ReservationAdapter> adapter = new ReservationsListAdapter<ReservationAdapter>(
                this, R.layout.reservation_list_row, ReservationsList);
        setListAdapter(adapter);

    }

    /*리스트 어뎁터
    * 입력되는 값들(이름, 시술, 날짜)을
    * reservation_list_row의 형태로 커스텀
    * */
    private class ReservationsListAdapter<T extends ReservationAdapter> extends
            ArrayAdapter<T> {

        private List<T> ReservationsList;

        public ReservationsListAdapter(Context context, int textViewResourceId,
                                   List<T> items) {
            super(context, textViewResourceId, items);
            ReservationsList = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.reservation_list_row, null);
            }else {
            }

            final T Reservations = ReservationsList.get(position);
            if (Reservations != null) {
                TextView viewName = (TextView) view.findViewById(R.id.tvName);
                if (viewName != null) {
                    viewName.setText(Reservations.getName());
                }

                TextView viewNumber = (TextView) view.findViewById(R.id.tvItem);
                if (viewNumber != null) {
                    viewNumber.setText("받는시술 : " + Reservations.getItem());
                }

                TextView viewDate = (TextView) view.findViewById(R.id.tvDate);
                if (viewDate != null) {
                    viewDate.setText("시술날짜 : " + Reservations.getDate());
                }
            }
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservationlist);

        datePicker = (WeekDatePicker) findViewById(R.id.date_picker);

        datePicker.setDateIndicator(LocalDate.now().plusDays(0), true);
        datePicker.setLimits(LocalDate.now().minusWeeks(0), null);


        /*날짜 클릭시 발생하는 이벤트*/
        datePicker.setOnDateSelectedListener(new WeekDatePicker.OnDateSelected() {
            @Override
            public void onDateSelected(LocalDate date) {

                try {
                    /*클릭한 날짜를 검색기능 안에 삽입*/
                    searchDate = date.toString();
                    displayList();
                } catch (Exception e) {
                    Log.e("", e.getMessage(), e);
                }
            }
        });
    }
}
