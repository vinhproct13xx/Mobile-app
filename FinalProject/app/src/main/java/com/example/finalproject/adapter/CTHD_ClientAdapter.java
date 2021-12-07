package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.model.CTHD;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class CTHD_ClientAdapter extends BaseAdapter {
    Context context;
    ArrayList<CTHD> arrCTHD;
    CTHD_ClientAdapter.ViewHolder viewHolder;

    public CTHD_ClientAdapter(Context context, ArrayList<CTHD> arrCTHD) {
        this.context = context;
        this.arrCTHD = arrCTHD;
    }

    @Override
    public int getCount() {
        return arrCTHD.size();
    }

    @Override
    public Object getItem(int position) {
        return arrCTHD.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView tvTenGioHang, tvGiaGioHang;
        public ImageView imgTenGioHang;
        public Button btnMinius, btnValue, btnPlus;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder =null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_giohang,null);
            viewHolder.tvTenGioHang = (TextView)convertView.findViewById(R.id.textview_TenGioHang);
            viewHolder.tvGiaGioHang = (TextView)convertView.findViewById(R.id.textview_GiaGioHang);
            viewHolder.imgTenGioHang = (ImageView) convertView.findViewById(R.id.imageview_TenGioHang);
            viewHolder.btnMinius = (Button) convertView.findViewById(R.id.btnminus);
            viewHolder.btnValue = (Button) convertView.findViewById(R.id.btnvalue);
            viewHolder.btnPlus = (Button) convertView.findViewById(R.id.btnplus);

            convertView.setTag(viewHolder);
        }else {
            viewHolder =(CTHD_ClientAdapter.ViewHolder)convertView.getTag();
        }

        CTHD cthd= (CTHD)getItem(position);
        viewHolder.tvTenGioHang.setText(cthd.getName());
        DecimalFormat decimalFormat =  new DecimalFormat("###,###,###");
        viewHolder.tvGiaGioHang.setText(decimalFormat.format(cthd.getPrice()-(cthd.getPrice()*cthd.getDiscount()/100))+" Đ");
        Picasso.with(context).load(cthd.getImage()).into(viewHolder.imgTenGioHang);
        viewHolder.btnValue.setText(cthd.getSoluong() + "");
        viewHolder.btnMinius.setVisibility(View.INVISIBLE);
        viewHolder.btnPlus.setVisibility(View.INVISIBLE);


        return convertView;
    }
}
