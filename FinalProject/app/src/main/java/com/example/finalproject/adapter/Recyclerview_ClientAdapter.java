package com.example.finalproject.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.model.HoaDon;

import java.util.ArrayList;

public class Recyclerview_ClientAdapter extends RecyclerView.Adapter<Recyclerview_ClientAdapter.RecyclerViewHolder>{
    private ArrayList<HoaDon> arr;
    Context context;
    public Recyclerview_ClientAdapter(ArrayList<HoaDon> arr) {
        this.arr = arr;
        this.context=context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_client, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (position%2==0)
        {
            holder.llParent.setBackgroundResource(R.color.colorWhite);
        }
        else
        {
            holder.llParent.setBackgroundResource(R.color.colorPrimary);
        }
        // Get item
        HoaDon hoadon = arr.get(position);
        if (hoadon.getMahd()!=-1) {
            holder.tv_mahd.setText(String.valueOf(hoadon.getMahd()));
        }
        else holder.tv_mahd.setText("1");

        if(hoadon.getTongtien()!=-1){
            holder.tv_tongtien.setText(String.valueOf(hoadon.getTongtien()));
        }
        else holder.tv_tongtien.setText("0000");

        if(hoadon.getNgaythanhtoan()!=null){
            holder.tv_ngaythanhtoan.setText(hoadon.getNgaythanhtoan());
        }
        else holder.tv_ngaythanhtoan.setText("");
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_mahd, tv_tongtien,tv_ngaythanhtoan;
        LinearLayout llParent;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tv_mahd = (TextView) itemView.findViewById(R.id.tv_mahd_client);
            tv_tongtien = (TextView) itemView.findViewById(R.id.tv_tongtien_client);
            tv_ngaythanhtoan = (TextView) itemView.findViewById(R.id.tv_ngaythanhtoan);
            llParent = (LinearLayout) itemView.findViewById(R.id.recyclerview_client1);
        }
    }

}
