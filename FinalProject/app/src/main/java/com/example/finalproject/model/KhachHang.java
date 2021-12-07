package com.example.finalproject.model;

public class KhachHang {
    private int makh;
    private String tenkh;
    private String dienthoai;
    private String diachi;


    public KhachHang(int makh, String tenkh,String dienthoai,String diachi)
    {
        this.makh=makh;
        this.tenkh=tenkh;
        this.dienthoai=dienthoai;
        this.diachi=diachi;

    }

    public int getMakh()
    {
        return this.makh;
    }

    public String getTenkh() {
        return this.tenkh;
    }
    public String getDienthoai(){
        return this.dienthoai;
    }
    public String getDiachi(){
        return this.diachi;
    }
    public void setMakh(int makh){
        this.makh=makh;
    }
    public void setTenkh(String tenkh){
        this.tenkh=tenkh;
    }
    public void setDienthoai(String dienthoai){
        this.dienthoai=dienthoai;
    }
    public void setDiachi(String diachi){
        this.diachi=diachi;
    }
}
