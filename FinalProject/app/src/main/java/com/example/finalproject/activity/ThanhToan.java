package com.example.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.ultil.CheckInternetCnn;
import com.example.finalproject.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ThanhToan extends AppCompatActivity {
    int makh=1;
    Button bt_tieptuc,bt_quaylai;
    static TextView tvTenNguoiNhan,tvSdt,tvDiaChi;
    boolean limitData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        init();
        ButtonEvent();
        GetCustomerAddress();
        //NhanDuLieu();
    }

    private void ButtonEvent(){
        bt_quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
            }
        });
        bt_tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TruyenDuLieu();
            }
        });
    }
    private void init(){
        tvTenNguoiNhan =findViewById(R.id.et_nguoinhan);
        tvSdt=findViewById(R.id.et_dienthoai);
        tvDiaChi=findViewById(R.id.et_diachi);
        bt_tieptuc=findViewById(R.id.bt_tieptuc);
        bt_quaylai=findViewById(R.id.bt_quaylai);

        SharedPreferences sharedPref = getSharedPreferences("MyReferences",Context.MODE_PRIVATE);
        makh = sharedPref.getInt("makh",1);
    }
//    private static void TruyenDuLieu1(){
//        if(ThanhToan_final.tvTenNguoiNhan.getText()!=null)
//            tvTenNguoiNhan.setText("tvten != null");
//        //tvTenNguoiNhan.setText(ThanhToan_final.tvTenNguoiNhan.getText());
//        else tvTenNguoiNhan.setText("tvten = null");
//    }
    private void TruyenDuLieu(){
        Intent intent = new Intent(ThanhToan.this,ThanhToan_final.class);
        intent.putExtra("tennguoinhan_fn",tvTenNguoiNhan.getText().toString());
        intent.putExtra("SDT_fn",tvSdt.getText().toString());
        intent.putExtra("DiaChi_fn",tvDiaChi.getText().toString());
        startActivity(intent);
    }

//    private void NhanDuLieu(){
//        tvTenNguoiNhan.setText(getIntent().getStringExtra("tennguoinhan"));
//        tvSdt.setText(getIntent().getStringExtra("SDT"));
//        tvDiaChi.setText(getIntent().getStringExtra("DiaChi"));
//        Log.d("tennguoinhan: ",tvTenNguoiNhan.getText()+"");
//    }

//    private void NhanDuLieu(){
//        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
//        String s="";
//        s=sharedPref.getString("tennguoinhan","");
//        if(!s.isEmpty())
//        {
//            tvTenNguoiNhan.setText(sharedPref.getString("tennguoinhan",""));
//            tvSdt.setText(sharedPref.getString("dienthoai",""));
//            tvDiaChi.setText(sharedPref.getString("diachi",""));
//        }
//        else {
//            tvTenNguoiNhan.setText("Sai roi");
//        }
//
//    }

    private void GetCustomerAddress() {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//ho tro doc JSON
        String urlCustomerAddress=Server.urlCustomerAddress;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCustomerAddress, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() != 2) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int j = 0; j < jsonArray.length(); j++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                tvTenNguoiNhan.setText(jsonObject.getString("tenkh"));
                                tvSdt.setText(jsonObject.getString("dienthoai"));
                                tvDiaChi.setText(jsonObject.getString("diachi"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else { //khi het du lieu de load
                    limitData = true;
                    CheckInternetCnn.NotificationCnn(getApplicationContext(), "Đã hết dữ liệu");
                }
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError { //Gửi dữ liệu leen server dạng hashmap
                    HashMap<String,String> param = new HashMap<String,String>();
                    param.put("makh",String.valueOf(makh));
                    return param;
                }
            };
        requestQueue.add(stringRequest);
        }
}
