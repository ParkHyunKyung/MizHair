package com.cookandroid.charm_admin.Reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cookandroid.charm_admin.R;

import java.util.ArrayList;

/**
 * Created by HP on 2016-09-20.
 */
public class ReservationItemListAdapter extends BaseAdapter {
    private Context mContext = null;
    public ArrayList<ReservationItemListData> mReservationItemListData = new ArrayList<ReservationItemListData>();

    public ReservationItemListAdapter() {
/*        super();
        this.mContext = mContext;*/
    }

    @Override
    public int getCount() {
        return mReservationItemListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mReservationItemListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String ItemName, String ItemPrice, String ItemTime, String ItemNum){
        ReservationItemListData addInfo = new ReservationItemListData();
        addInfo.setTv_ItemName(ItemName);
        addInfo.setTv_ItemPrice(ItemPrice);
        addInfo.setTv_ItemTime(ItemTime);
        addInfo.setTv_ItemNum(ItemNum);
        mReservationItemListData.add(addInfo);
    }

    public void remove(int position){
        mReservationItemListData.remove(position);
        dataChange();
    }
/*

        public void sort(){
            Collections.sort(mPriceListData, PriceListData.ALPHA_COMPARATOR);
            dataChange();
        }
*/

    public void dataChange(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reservation_itemlist_row, parent, false);

            holder.tv_ItemName = (TextView) convertView.findViewById(R.id.tv_ItemName);
            holder.tv_ItemPrice = (TextView) convertView.findViewById(R.id.tv_ItemPrice);
            holder.tv_ItemTime = (TextView) convertView.findViewById(R.id.tv_ItemTime);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ReservationItemListData mData = mReservationItemListData.get(position);

        holder.tv_ItemName.setText(mData.getTv_ItemName());
        holder.tv_ItemPrice.setText(mData.getTv_ItemPrice());
        holder.tv_ItemTime.setText(mData.getTv_ItemTime());

        return convertView;
    }

    private class ViewHolder {
        public TextView tv_ItemName;

        public TextView tv_ItemPrice;

        public TextView tv_ItemTime;
    }
}
