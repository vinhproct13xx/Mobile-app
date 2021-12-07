package com.example.finalproject.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.adapter.ItemLongClickListener;
import com.example.finalproject.adapter.RecyclerViewTouchListener;
import com.example.finalproject.adapter.Recyclerview_ClientAdapter;
import com.example.finalproject.model.HoaDon;
import com.example.finalproject.ultil.CheckInternetCnn;
import com.example.finalproject.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KhachHang extends AppCompatActivity {
    Toolbar toolbarChiTiet;
    Button bt_sua,bt_luu;
    EditText et_ten,et_dt,et_diachi;
    int makh=1;
    boolean limitData = false;
    RecyclerView rv;
    ArrayList<HoaDon> HoaDons;
    Recyclerview_ClientAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        init();
        bt_sua.setVisibility(View.VISIBLE);
        bt_luu.setVisibility(View.INVISIBLE);
        et_ten.setEnabled(false);
        et_diachi.setEnabled(false);
        et_dt.setEnabled(false);

        if(CheckInternetCnn.haveNetworkConnection(getApplicationContext())){
            ButtonEvent();
            GetCustomerAddress();
            getBillList();
            actionToolBar();
        }else {
            CheckInternetCnn.NotificationCnn(getApplicationContext(),"No Internet Connection");
        }
    }


    private void ButtonEvent(){
        bt_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_luu.setVisibility(View.VISIBLE);
                et_ten.setEnabled(true);
                et_diachi.setEnabled(true);
                et_dt.setEnabled(true);
                bt_sua.setVisibility(View.INVISIBLE);
            }
        });
        bt_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenkh=et_ten.getText().toString().trim();
                final String sdt=et_dt.getText().toString().trim();
                final String diachi=et_diachi.getText().toString().trim();
                if(tenkh.length()>0&&sdt.length()>0&&diachi.length()>0&&makh>0){
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.urlInsertKH, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String mahd) {
                            Log.d("mahd", mahd);

                        }}, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<String,String>();
                            hashMap.put("makh",String.valueOf(makh));
                            hashMap.put("tenkh",tenkh);
                            hashMap.put("dienthoai",sdt);
                            hashMap.put("diachi",diachi);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(KhachHang.this,"Hãy kiểm tra lại dữ liệu",Toast.LENGTH_LONG).show();
                }
                Toast.makeText(KhachHang.this,"Sửa địa chỉ thành công!!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), KhachHang.class);
                startActivity(intent);
            }
        });

        rv.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), rv, new ItemLongClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view,final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KhachHang.this);
                builder.setTitle("Chi tiết hóa đơn");
                builder.setMessage("Bạn có muốn xem CTHD sản phẩm này?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("mahd",String.valueOf(HoaDons.get(position).getMahd()));
                        editor.putString("tongtien",String.valueOf(HoaDons.get(position).getTongtien()));
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), KhachHang_CTHD.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        }
        ));
    }

    private void GetCustomerAddress() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//ho tro doc JSON
        String urlCustomerAddress= Server.urlCustomerAddress;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCustomerAddress, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() != 2) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int j = 0; j < jsonArray.length(); j++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                et_ten.setText(jsonObject.getString("tenkh"));
                                et_dt.setText(jsonObject.getString("dienthoai"));
                                et_diachi.setText(jsonObject.getString("diachi"));
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

    private void init(){
        bt_sua=findViewById(R.id.bt_sua_cl);
        bt_luu=findViewById(R.id.bt_luu_cl);
        et_ten=findViewById(R.id.et_nguoinhan_cl);
        et_dt=findViewById(R.id.et_dienthoai_cl);
        et_diachi=findViewById(R.id.et_diachi_cl);
        toolbarChiTiet=(Toolbar)findViewById(R.id.toolbar_client);
        rv=(RecyclerView) findViewById(R.id.rv_hoadon);
        HoaDons = new ArrayList<>();
        adapter = new Recyclerview_ClientAdapter(HoaDons);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        SharedPreferences sharedPref = getSharedPreferences("MyReferences",Context.MODE_PRIVATE);
        makh = sharedPref.getInt("makh",1);
    }

    private void getBillList(){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//ho tro doc JSON
        String urlBillList= Server.urlBillList;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urlBillList , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String ngaythanhtoan;
                int mahd,tongtien;
                if (response != null && response.length() != -1) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int j = 0; j < jsonArray.length(); j++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                mahd=jsonObject.getInt("mahd");
                                tongtien=jsonObject.getInt("tongtien");
                                ngaythanhtoan=jsonObject.getString("ngaythanhtoan");
                                HoaDons.add(new HoaDon(mahd,tongtien,ngaythanhtoan));
                                adapter.notifyDataSetChanged();
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
    private void actionToolBar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
