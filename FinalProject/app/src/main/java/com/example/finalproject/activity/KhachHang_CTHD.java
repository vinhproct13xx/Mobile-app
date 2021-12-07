package com.example.finalproject.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.adapter.CTHD_ClientAdapter;
import com.example.finalproject.model.CTHD;
import com.example.finalproject.ultil.CheckInternetCnn;
import com.example.finalproject.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KhachHang_CTHD extends AppCompatActivity {
    Toolbar toolbarChiTiet;
    TextView tv_tongtien;
    String mahd;
    int masp=0,price=0,discount =0 ,soluong=0;
    String name="";
    String image="";
    boolean limitData=false;
    ListView lv;
    CTHD_ClientAdapter adapter;
    public static ArrayList<CTHD> mangCTHD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_cthd);
        init();
        actionToolBar();
        getCTHD();
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

    private void getCTHD(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//ho tro doc JSON
        String urlCTHDClient= Server.urlCTHDClient;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlCTHDClient, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() != 2) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int j = 0; j < jsonArray.length(); j++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                masp=jsonObject.getInt("masp");
                                name=jsonObject.getString("Name");
                                price=jsonObject.getInt("Price");
                                discount=jsonObject.getInt("Discount");
                                image=jsonObject.getString("Image");
                                soluong=jsonObject.getInt("soluong");
                                mangCTHD.add(new CTHD(masp,price,discount,name,soluong,image));
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
                param.put("mahd",String.valueOf(mahd));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void init(){
        toolbarChiTiet=(Toolbar)findViewById(R.id.toolbar_client_cthd);
        tv_tongtien=findViewById(R.id.tv_TongTien_client_cthd);
        lv=(ListView)findViewById(R.id.lv_CTHD_client);
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String tongtien1=sharedPref.getString("tongtien","");
        tv_tongtien.setText(tongtien1+"Đ");
        mahd=sharedPref.getString("mahd","");
        mangCTHD=new ArrayList<CTHD>();
        adapter = new CTHD_ClientAdapter(getApplicationContext(),mangCTHD);
        lv.setAdapter(adapter);
    }

}
