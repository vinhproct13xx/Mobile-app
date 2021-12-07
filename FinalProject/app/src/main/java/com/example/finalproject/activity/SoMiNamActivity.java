package com.example.finalproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.adapter.SoMiNamApdater;
import com.example.finalproject.model.Product;
import com.example.finalproject.ultil.CheckInternetCnn;
import com.example.finalproject.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SoMiNamActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbarSoMiNam;
    ListView listViewSoMiNam;
    View footerView;
    SoMiNamApdater soMiNamApdater;
    ArrayList<Product> arrSoMiNam;
    int idCate = 0;
    int page =1;
    boolean isLoading = false;
    MyHandler myHandler;
    boolean limitData = false;//da het data chưa ?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_mi_nam);

        init();
        if(CheckInternetCnn.haveNetworkConnection(getApplicationContext())){
            getIdCate();
            actionToolBar();
            getData(page);
            LoadMoreData();
        }else {
            CheckInternetCnn.NotificationCnn(getApplicationContext(),"No Internet Connection");
        }

    }
    @Override//Tao menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    //bat su kien item menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                Intent intent = new Intent(getApplicationContext(), com.example.finalproject.activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void LoadMoreData() {
        listViewSoMiNam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",arrSoMiNam.get(position));
                startActivity(intent);
            }
        });
        listViewSoMiNam.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {//khi vuốt listview tới 1 vị trí nào đó

            }

            @Override
            //khi vuốt
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount !=0 && isLoading==false
                    &&limitData==false){//Dang o vi tri cuoi cung va dam bao giu lieu da dc load len
                    isLoading =true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//ddối tượng để gửi các request lên server
        String urlSoMiNam = Server.urlSoMiNam+String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSoMiNam, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // Hứng dữ liệu trả về
                int id = 0;
                String tenSoMiNam = "";
                int gia = 0;
                int discount = 0;
                String image = "";
                String mota = "";
                int idCate =0;

                if(response!=null && response.length()!=2){
                    listViewSoMiNam.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int j=0;j<jsonArray.length();j++){
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                id = jsonObject.getInt("id");
                                tenSoMiNam = jsonObject.getString("name");
                                gia = jsonObject.getInt("price");
                                discount = jsonObject.getInt("discount");
                                image = jsonObject.getString("image");
                                mota = jsonObject.getString("description");
                                idCate = jsonObject.getInt("idCategory");

                                arrSoMiNam.add(new Product(id,tenSoMiNam,gia,discount,image,mota,idCate));
                                soMiNamApdater.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else { //khi het du lieu de load
                    limitData = true;
                    listViewSoMiNam.removeFooterView(footerView);
                    CheckInternetCnn.NotificationCnn(getApplicationContext(),"Đã hết dữ liệu");
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
                param.put("idcategory",String.valueOf(idCate));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarSoMiNam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSoMiNam.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIdCate() {
        idCate = getIntent().getIntExtra("idCategory",-1);
        Log.d("idCate: ",idCate+"");
    }

    private void init() {
        toolbarSoMiNam = (Toolbar) findViewById(R.id.toolbar_soMiNam);
        listViewSoMiNam = (ListView) findViewById(R.id.listview_soMiNam);
        arrSoMiNam = new ArrayList<>();
        soMiNamApdater = new SoMiNamApdater(getApplicationContext(),arrSoMiNam);
        listViewSoMiNam.setAdapter(soMiNamApdater);

        LayoutInflater inflater =(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);

        myHandler = new MyHandler();
    }

    public  class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) { // quan ly cac thread gui len
            switch (msg.what){//gia tri gui len
                case 0:
                    listViewSoMiNam.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public  class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);//Lien ket thread voi handler
            myHandler.sendMessage(message);
            super.run();
        }
    }
}
