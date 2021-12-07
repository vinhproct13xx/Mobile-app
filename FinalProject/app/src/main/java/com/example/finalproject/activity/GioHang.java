package com.example.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.GioHangAdapter;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {
    ListView lvGioHang;
    TextView tvThongBao;
    static TextView tvTongTien;
    Button btnThanhToan, btnTiepTucMua;
    androidx.appcompat.widget.Toolbar toolbarGioHang;
    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        init();
        actionToolbar();
        checkData();
        totalValueGioHang();
        catchOnItemListView();
        btnEvent();
    }

    private void btnEvent() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThanhToan.class);
                startActivity(intent);
            }
        });
    }

    private void catchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc xóa sản phẩm này?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.mangGioHang.size()<=0){
                            tvThongBao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.mangGioHang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            totalValueGioHang();
                            if (MainActivity.mangGioHang.size()<=0){
                                tvThongBao.setVisibility(View.VISIBLE);
                            }else {
                                tvThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                totalValueGioHang();
                            }
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        totalValueGioHang();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void totalValueGioHang() {
        int tongTien = 0;
        for(int i = 0;i<MainActivity.mangGioHang.size();i++){
            tongTien += MainActivity.mangGioHang.get(i).getGiasp();
        }
        Log.d("kich",tongTien+"");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongTien)+ " Đ");
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkData() {
        if(MainActivity.mangGioHang.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            tvThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        }else {
            gioHangAdapter.notifyDataSetChanged();
            tvThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }


    private void init() {
        lvGioHang = findViewById(R.id.listview_GioHang);
        tvThongBao = findViewById(R.id.textview_thongbao);
        tvTongTien = findViewById(R.id.textview_TongTien);
        btnThanhToan = findViewById(R.id.btnThanhToanGioHang);
        btnTiepTucMua = findViewById(R.id.btnTiepTucMuaHang);
        toolbarGioHang = findViewById(R.id.toolbar_GioHang);
        gioHangAdapter = new GioHangAdapter(GioHang.this,MainActivity.mangGioHang);
        lvGioHang.setAdapter(gioHangAdapter);
    }
}
