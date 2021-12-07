package com.example.finalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.activity.ChiTietSanPham;
import com.example.finalproject.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<Product> productArr;

    public ProductAdapter(Context context, ArrayList<Product> productArr) {
        this.context = context;
        this.productArr = productArr;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_newproduct,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        Product product = productArr.get(position);
        holder.textViewName.setText(product.getName());
        if (product.getDiscount()>0){
            int giaMoi = product.getPrice()- (product.getPrice()*product.getDiscount())/100;
            holder.textViewOldPrice.setPaintFlags(holder.textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textViewOldPrice.setText("Giá cũ: "+decimalFormat.format(product.getPrice())+"đ");
            holder.textViewPrice.setText("Giá: "+decimalFormat.format(giaMoi)+"đ");
        }else {
            holder.textViewOldPrice.setVisibility(View.INVISIBLE);
            holder.textViewPrice.setText("Giá: "+decimalFormat.format(product.getPrice())+"đ");
        }
        Picasso.with(context).load(product.getImage()).into(holder.imageViewProduct);
    }

    @Override
    public int getItemCount() {
        return productArr.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewProduct;
        public TextView textViewName, textViewPrice, textViewOldPrice;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = (ImageView)itemView.findViewById(R.id.imageview_product);
            textViewName = (TextView) itemView.findViewById(R.id.textview_productname);
            textViewPrice = (TextView) itemView.findViewById(R.id.textview_productprice);
            textViewOldPrice = (TextView) itemView.findViewById(R.id.textview_oldproductprice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham",productArr.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//de co the start activity
                    context.startActivity(intent);
                }
            });
        }
    }
}
