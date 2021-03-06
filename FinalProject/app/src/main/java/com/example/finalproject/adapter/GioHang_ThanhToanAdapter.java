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
import com.example.finalproject.activity.MainActivity;
import com.example.finalproject.model.GioHang;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.text.DecimalFormat;
import java.util.ArrayList;
public class GioHang_ThanhToanAdapter extends BaseAdapter{
    Context context;
    ArrayList<GioHang> arrGioHang;
    ViewHolder viewHolder;

    public GioHang_ThanhToanAdapter(Context context, ArrayList<GioHang> arrGioHang) {
        this.context = context;
        this.arrGioHang = arrGioHang;
    }

    @Override
    public int getCount() {
        return arrGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrGioHang.get(position);
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
        viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_giohang, null);
            viewHolder.tvTenGioHang = (TextView) convertView.findViewById(R.id.textview_TenGioHang);
            viewHolder.tvGiaGioHang = (TextView) convertView.findViewById(R.id.textview_GiaGioHang);
            viewHolder.imgTenGioHang = (ImageView) convertView.findViewById(R.id.imageview_TenGioHang);
            viewHolder.btnValue = (Button) convertView.findViewById(R.id.btnvalue);
            viewHolder.btnMinius = (Button) convertView.findViewById(R.id.btnminus);
            viewHolder.btnPlus = (Button) convertView.findViewById(R.id.btnplus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.tvTenGioHang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaGioHang.setText(decimalFormat.format(gioHang.getGiasp()) + " ??");
        Picasso.with(context).load(gioHang.getHinhsp()).into(viewHolder.imgTenGioHang);
        viewHolder.btnValue.setText(gioHang.getSoluong() + "");


        int sl = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if(sl>=10){
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinius.setVisibility(View.VISIBLE);
        }else if(sl<=1){
            viewHolder.btnMinius.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinius.setVisibility(View.VISIBLE);
        }
        //B???t s??? ki???n cho n??t plus
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slMoiNhat = Integer.parseInt(viewHolder.btnValue.getText().toString())+1;
                int slHienTai = MainActivity.mangGioHang.get(position).getSoluong();
                int giaHienTai = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluong(slMoiNhat);
                int giaMoiNhat = (giaHienTai * slMoiNhat)/slHienTai;
                MainActivity.mangGioHang.get(position).setGiasp(giaMoiNhat);
                DecimalFormat decimalFormat =  new DecimalFormat("###,###,###");
                viewHolder.tvGiaGioHang.setText(decimalFormat.format(giaMoiNhat)+" ??");
                com.example.finalproject.activity.GioHang.totalValueGioHang();
                if(slMoiNhat>=10){
                    viewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    viewHolder.btnMinius.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(slMoiNhat));
                }else {
                    viewHolder.btnPlus.setVisibility(View.VISIBLE);
                    viewHolder.btnMinius.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(slMoiNhat));
                }
            }
        });
        // B???t s??? ki???n cho n??t -
        viewHolder.btnMinius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slMoiNhat = Integer.parseInt(viewHolder.btnValue.getText().toString())-1;
                int slHienTai = MainActivity.mangGioHang.get(position).getSoluong();
                int giaHienTai = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluong(slMoiNhat);
                int giaMoiNhat = (giaHienTai * slMoiNhat)/slHienTai;
                MainActivity.mangGioHang.get(position).setGiasp(giaMoiNhat);
                DecimalFormat decimalFormat =  new DecimalFormat("###,###,###");
                viewHolder.tvGiaGioHang.setText(decimalFormat.format(giaMoiNhat)+" ??");
                com.example.finalproject.activity.GioHang.totalValueGioHang();
                if(slMoiNhat<=1){
                    viewHolder.btnMinius.setVisibility(View.INVISIBLE);
                    viewHolder.btnPlus.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(slMoiNhat));
                }else {
                    viewHolder.btnPlus.setVisibility(View.VISIBLE);
                    viewHolder.btnMinius.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(slMoiNhat));
                }
            }
        });
        viewHolder.btnPlus.setVisibility(View.INVISIBLE);
        viewHolder.btnMinius.setVisibility(View.INVISIBLE);
        return convertView;
    }
}
