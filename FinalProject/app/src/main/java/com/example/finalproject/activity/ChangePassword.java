package com.example.finalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.ultil.Server;
import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    Button btnConfirm;
    EditText edtOldpass;
    EditText edtNewpass, edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pasword);
        addControl();
        addEvent();
    }
    private void addEvent() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String Oldpass = edtOldpass.getText().toString().trim();
               String Newpass = edtNewpass.getText().toString().trim();
               String Email = edtEmail.getText().toString().trim();
               changepass(Oldpass, Newpass, Email);
           }
       });
    }

    private void changepass(final String Oldpass, final String Newpass, final String Email){
        if (checkEditText(edtOldpass) && checkEditText(edtNewpass) && checkEditText(edtEmail)) {
            StringRequest requestLogin = new StringRequest(Request.Method.POST, Server.URL_CHANGE_PASS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("aaa", response+"");
                            try {
                                System.out.print(response.trim());
                                if(response.trim().equals("success")){
                                    Toast.makeText(ChangePassword.this, "Đã đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(ChangePassword.this, "Chưa đổi được mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("errorrrrrrrrrrrrrrrrrrr");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("oldpass", Oldpass);
                    params.put("newpass", Newpass);
                    params.put("email", Email);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(requestLogin);
        }
    }
    private void addControl() {
        btnConfirm = findViewById(R.id.btnConfirm);
        edtNewpass = findViewById(R.id.editNewpass);
        edtOldpass = findViewById(R.id.editOldpass);
        edtEmail = findViewById(R.id.editemail_changepass);
    }
    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;

    }

}
