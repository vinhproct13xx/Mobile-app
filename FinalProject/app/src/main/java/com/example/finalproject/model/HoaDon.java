package com.example.finalproject.model;

import java.util.Date;

public class HoaDon {
    private int mahd;
    private int tongtien;
    private String ngaythanhtoan;

    public HoaDon(int mahd, int tongtien, String ngaythanhtoan) {
        this.mahd = mahd;
        this.tongtien = tongtien;
        this.ngaythanhtoan = ngaythanhtoan;
    }

    public int getMahd() {
        return mahd;
    }

    public void setMahd(int mahd) {
        this.mahd = mahd;
    }


    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }



    public String getNgaythanhtoan() {
        return ngaythanhtoan;
    }

    public void setNgaythanhtoan(String ngaythanhtoan) {
        this.ngaythanhtoan = ngaythanhtoan;
    }
}