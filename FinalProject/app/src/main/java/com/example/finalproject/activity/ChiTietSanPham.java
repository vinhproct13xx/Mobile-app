package com.example.finalproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.adapter.CommentAdapter;
import com.example.finalproject.adapter.SoMiNamApdater;
import com.example.finalproject.model.Category;
import com.example.finalproject.model.Comment;
import com.example.finalproject.model.GioHang;
import com.example.finalproject.model.Product;
import com.example.finalproject.ultil.CheckInternetCnn;
import com.example.finalproject.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSanPham extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbarChiTiet;
    ImageView imgChiTiet;
    TextView tvTen,tvGiaCu, tvGia, tvMoTa;
    ListView lvComment;
    ArrayList<Comment> commentList;
    CommentAdapter commentAdapter;
    EditText editTextComment;

    Spinner spinner;
    Button btnDatMua, btnComment;


    int id = 0;
    String tenChiTiet = "";
    int giaChiTiet = 0;
    int discount = 0;
    String anhChiTiet ="";
    String moTaChiTiet = "";
    int idCate =0;

    String username ="";
    String content ="";
    String newContent ="";

    Integer makh = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        init();
        actionToolBar();
        getInformation();
        catchEventSpinner();
        eventButton();
        getComment();

    }

    private void getComment() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//ddối tượng để gửi các request lên server
        String urlComment = Server.urlComment+String.valueOf(id);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlComment,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null){
                            Log.d("CMM",response.length()+"CMM");
                            for (int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    username = jsonObject.getString("username");
                                    content = jsonObject.getString("content");

                                    commentList.add(new Comment(username,content));
                                    commentAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckInternetCnn.NotificationCnn(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
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
    private void eventButton() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mangGioHang.size()>0){//nếu giỏ hàng đã có sản phầm
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    Boolean exits = false;
                    for (int i=0; i<MainActivity.mangGioHang.size();i++){
                        if(MainActivity.mangGioHang.get(i).getIdsp()==id){
                            //cập nhật lại sl trong giỏ hàng
                            int slTrongMang = MainActivity.mangGioHang.get(i).getSoluong();
                            MainActivity.mangGioHang.get(i).setSoluong(slTrongMang+sl);
                            if(MainActivity.mangGioHang.get(i).getSoluong()>=10){
                                MainActivity.mangGioHang.get(i).setSoluong(10);
                            }
                            //Cap nhat lai gia trong gio hang
                            MainActivity.mangGioHang.get(i).setGiasp(giaChiTiet*MainActivity.mangGioHang.get(i).getSoluong());
                            exits = true;
                        }
                    }
                    if (exits == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        int giaMoi = soluong*giaChiTiet;
                        MainActivity.mangGioHang.add(new GioHang(id,tenChiTiet,giaMoi,anhChiTiet,soluong));
                    }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    int giaMoi = soluong*giaChiTiet;
                    MainActivity.mangGioHang.add(new GioHang(id,tenChiTiet,giaMoi,anhChiTiet,soluong));
                }
                CheckInternetCnn.NotificationCnn(getApplicationContext(),"Đã thêm vào giỏ hàng");
            }
        });


        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newContent =editTextComment.getText().toString().trim();
                Log.d("mmm",newContent+"mmm");
                if(newContent.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//ddối tượng để gửi các request lên server
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlAddComment, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { // Hứng dữ liệu trả về
                            Log.d("repon",response+"");
                            if(response.equals("1")){
                                commentList.add(new Comment("Doan",newContent));
                                commentAdapter.notifyDataSetChanged();
                                CheckInternetCnn.NotificationCnn(getApplicationContext(),"Đã thêm");
                            }else { //khi het du lieu de load
                                CheckInternetCnn.NotificationCnn(getApplicationContext(),"That bai");
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
                            param.put("masp",String.valueOf(id));
                            param.put("content",newContent);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckInternetCnn.NotificationCnn(getApplicationContext(),"Vui lòng nhập thông tin");
                }
            }
        });
    }

    private void catchEventSpinner() {
        Integer[] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soLuong);
        spinner.setAdapter(arrayAdapter);
    }

    private void getInformation() {

        Product product = (Product) getIntent().getSerializableExtra("thongtinsanpham");
        id = product.getId();
        tenChiTiet = product.getName();
        if (product.getDiscount()>0){
            giaChiTiet = product.getPrice()- (product.getPrice()*product.getDiscount())/100;
        }else {
            giaChiTiet = product.getPrice();
        }

        discount = product.getDiscount();
        anhChiTiet = product.getImage();
        moTaChiTiet = product.getDescription();
        idCate = product.getId_Category();


        tvTen.setText(tenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (product.getDiscount()>0){
            int giaMoi = product.getPrice()- (product.getPrice()*product.getDiscount())/100;
            tvGiaCu.setPaintFlags(tvGiaCu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvGiaCu.setText("Giá cũ: "+decimalFormat.format(product.getPrice())+"đ");
            tvGia.setText("Giá: "+decimalFormat.format(giaMoi)+"đ");
        }else {
            tvGiaCu.setVisibility(View.INVISIBLE);
            tvGia.setText("Giá: "+decimalFormat.format(product.getPrice())+"đ");
        }
        tvMoTa.setText(moTaChiTiet);
        Picasso.with(getApplicationContext()).load(anhChiTiet).into(imgChiTiet);

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

    private void init() {
        toolbarChiTiet = (Toolbar)findViewById(R.id.toolbar_ChiTietSP);
        imgChiTiet = (ImageView) findViewById(R.id.imageview_ChiTietSP);
        tvTen = (TextView) findViewById(R.id.textview_TenChiTietSP);
        tvGiaCu = (TextView) findViewById(R.id.textview_GiaCuChiTiet);
        tvGia = (TextView) findViewById(R.id.textview_GiaChiTietSP);
        //tvGia.setPaintFlags(tvGia.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvMoTa = (TextView) findViewById(R.id.textview_MoTaChiTietSQP);
        btnDatMua = (Button)findViewById(R.id.button_DatMua);
        btnComment = (Button)findViewById(R.id.btn_comment);
        spinner = (Spinner)findViewById(R.id.spinner);
        editTextComment = (EditText)findViewById(R.id.editText_comment);

        lvComment = (ListView) findViewById(R.id.listview_comment);
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList,getApplicationContext());
        lvComment.setAdapter(commentAdapter);

        SharedPreferences sharedPref = getSharedPreferences("MyReferences", Context.MODE_PRIVATE);
         makh = sharedPref.getInt("makh",1);
    }
}
