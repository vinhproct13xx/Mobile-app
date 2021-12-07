package com.example.finalproject.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id_user;
    private String tenuser;
    private String dienthoai;
    private String diachi;
    private String email;
    private String password;

    public User(int id_user, String email, String password) {
        this.id_user = id_user;
        this.email = email;
        this.password = password;
    }

    public User() {

    }


    public int getId_user() {
        return id_user;
    }

    public String getTenuser() {
        return tenuser;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setTenuser(String tenuser) {
        this.tenuser = tenuser;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        password = password;
    }
}
