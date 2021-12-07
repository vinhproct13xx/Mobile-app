package com.example.finalproject.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SoMiNamApdater extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListProduct;

    public SoMiNamApdater(Context context, ArrayList<Product> arrayListProduct) {
        this.context = context;
        this.arrayListProduct = arrayListProduct;
    }

    @Override
    public int getCount() {
        return arrayListProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView textView_tenSoMiNam, textView_gia,textView_giacu, textView_moTa;
        public ImageView imageView_SoMiNam;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView== null){ //lan dau chay
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_sominam,null);
            viewHolder.textView_tenSoMiNam = convertView.findViewById(R.id.textView_tenSoMiNam);
            viewHolder.textView_gia = convertView.findViewById(R.id.textview_giaSoMiNam);
            viewHolder.textView_giacu = convertView.findViewById(R.id.textview_oldpricesominam);
            viewHolder.textView_moTa = convertView.findViewById(R.id.textView_moTaSoMiNam);
            viewHolder.imageView_SoMiNam = convertView.findViewById(R.id.imageView_soMiNam);
            convertView.setTag(viewHolder);// truyen id qua
        }else { //da chay roi
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.textView_tenSoMiNam.setText(product.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textView_gia.setText("Giá: "+decimalFormat.format(product.getPrice())+"đ");
        if (product.getDiscount()>0){
            int giaMoi = product.getPrice()- (product.getPrice()*product.getDiscount())/100;
            viewHolder.textView_giacu.setPaintFlags(viewHolder.textView_giacu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.textView_giacu.setText("Giá cũ: "+decimalFormat.format(product.getPrice())+"đ");
            viewHolder.textView_gia.setText("Giá: "+decimalFormat.format(giaMoi)+"đ");
        }else {
            viewHolder.textView_giacu.setVisibility(View.INVISIBLE);
            viewHolder.textView_gia.setText("Giá: "+decimalFormat.format(product.getPrice())+"đ");
        }

        viewHolder.textView_moTa.setMaxLines(2);
        viewHolder.textView_moTa.setEllipsize(TextUtils.TruncateAt.END);//Hien thi dau 3 cham khi dai qua
        viewHolder.textView_moTa.setText(product.getDescription());
        Picasso.with(context).load(product.getImage()).into(viewHolder.imageView_SoMiNam);

        return convertView;
    }
}
