package com.example.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.adapter.GioHang_ThanhToanAdapter;
import com.example.finalproject.ultil.CheckInternetCnn;
import com.example.finalproject.ultil.Server;

import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ThanhToan_final extends AppCompatActivity {
    int makh=1;
    ListView lvGioHang;
    static TextView tvThongBao, tvTenNguoiNhan,tvSdt,tvDiaChi;
    static TextView tvTongTien;
    Button btThanhToan, btQuayLai;
    GioHang_ThanhToanAdapter gioHangAdapter;
    //SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_final);
        init();
        NhanDuLieu();
        checkData();
        totalValueGioHang();
        if(CheckInternetCnn.haveNetworkConnection(getApplicationContext())){
            ButtonEvent();
        }else CheckInternetCnn.NotificationCnn(getApplicationContext(),"No Internet");
    }

    private  void ButtonEvent(){
        btThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tongTien1=totalValueGioHang1();
                final String nguoinhan=tvTenNguoiNhan.getText().toString().trim();
                final String sodienthoai=tvSdt.getText().toString().trim();
                final String diachigiao=tvDiaChi.getText().toString().trim();
                final String tongtien=String.valueOf(tongTien1);
                if(nguoinhan.length()>0&&sodienthoai.length()>0&&diachigiao.length()>0&&makh>0&&tongtien.length()>0){
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.urlInsertHoaDon, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String mahd) {
                            Log.d("mahd",mahd);
                            if(Integer.parseInt(mahd)>0){
                                RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                                StringRequest request=new StringRequest(Request.Method.POST, Server.urlInsertCTHD, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.mangGioHang.clear();
                                            Toast.makeText(ThanhToan_final.this,"Thanh toán thành công!!",Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(ThanhToan_final.this,"Xin tiếp tục mua hàng!!",Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(ThanhToan_final.this,"Lỗi thanh toán rồi huhu!!",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray=new JSONArray();
                                        for(int i=0;i<MainActivity.mangGioHang.size();i++){
                                            JSONObject jsonObject=new JSONObject();
                                            try {
                                                jsonObject.put("mahd",mahd);
                                                jsonObject.put("masp",MainActivity.mangGioHang.get(i).getIdsp());
                                                jsonObject.put("soluong",MainActivity.mangGioHang.get(i).getSoluong());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap=new HashMap<String,String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<String,String>();
                            hashMap.put("makh",String.valueOf(makh));
                            hashMap.put("tongtien",tongtien);
                            hashMap.put("nguoinhan",nguoinhan);
                            hashMap.put("sodienthoai",sodienthoai);
                            hashMap.put("diachigiao",diachigiao);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(ThanhToan_final.this,"Hãy kiểm tra lại dữ liệu",Toast.LENGTH_LONG).show();
                }
            }
        });
        btQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),ThanhToan.class);
                startActivity(intent);
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

    public static int totalValueGioHang1() {
        int tongTien = 0;
        for(int i = 0;i<MainActivity.mangGioHang.size();i++){
            tongTien += MainActivity.mangGioHang.get(i).getGiasp();
        }
        Log.d("kich",tongTien+"");
        return tongTien;
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
        tvTenNguoiNhan =findViewById(R.id.tv_nguoinhanfn);
        tvSdt=findViewById(R.id.tv_dienthoaifn);
        tvDiaChi=findViewById(R.id.tv_diachifn);
        lvGioHang = findViewById(R.id.lv_GioHang);
        tvThongBao = findViewById(R.id.tv_thongbaofn);
        tvTongTien = findViewById(R.id.tv_TongTien);
        btThanhToan = findViewById(R.id.bt_thanhtoanfn);
        btQuayLai = findViewById(R.id.bt_quaylaifn);
        gioHangAdapter = new GioHang_ThanhToanAdapter(ThanhToan_final.this,MainActivity.mangGioHang);
        lvGioHang.setAdapter(gioHangAdapter);

        SharedPreferences sharedPref = getSharedPreferences("MyReferences", Context.MODE_PRIVATE);
        makh = sharedPref.getInt("makh",1);
    }

//    private  void TruyenDuLieu(){
//        Intent intent = new Intent(ThanhToan_final.this,ThanhToan.class);
//        intent.putExtra("tennguoinhan",tvTenNguoiNhan.getText().toString());
//        intent.putExtra("SDT",tvSdt.getText().toString());
//        intent.putExtra("DiaChi",tvDiaChi.getText().toString());
//        startActivity(intent);
//    }

//    private void TruyenDuLieu(){
//        sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("tennguoinhan", tvTenNguoiNhan.getText().toString());
//        editor.putString("dienthoai", tvSdt.getText().toString());
//        editor.putString("diachi",tvDiaChi.getText().toString());
//        editor.apply();
//        Intent intent = new Intent(ThanhToan_final.this,ThanhToan.class);
//        startActivity(intent);
//    }

    private void NhanDuLieu(){
        tvTenNguoiNhan.setText(getIntent().getStringExtra("tennguoinhan_fn"));
        tvSdt.setText(getIntent().getStringExtra("SDT_fn"));
        tvDiaChi.setText(getIntent().getStringExtra("DiaChi_fn"));
        Log.d("tennguoinhan: ",tvTenNguoiNhan.getText()+"");
    }
}
