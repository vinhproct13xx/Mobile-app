package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.model.Category;
import com.example.finalproject.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class SearchAdapter extends BaseAdapter {
    ArrayList<Product> productList;
    ArrayList<Product> searchList;//phu
    Context context;

    public SearchAdapter(Context context,ArrayList<Product> productList) {
        this.productList = productList;
        this.context = context;
        this.searchList = new ArrayList<Product>();
        //this.searchList.addAll(productList);
    }

    @Override
    public int getCount() {
        return searchList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView tvNameSearch;
        ImageView imgSearch;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_listview_search,null);

            viewHolder.tvNameSearch =(TextView)convertView.findViewById(R.id.textview_search);
            viewHolder.imgSearch =(ImageView) convertView.findViewById(R.id.imageview_search);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Product search = (Product) getItem(position);
        viewHolder.tvNameSearch.setText(search.getName());
        Picasso.with(context).load(search.getImage()).into(viewHolder.imgSearch);

        return convertView;
    }

    public void filter(String searchText){
        searchText = searchText.toLowerCase(Locale.getDefault());
        searchList.clear();
        if(searchText.length() ==0){
            //searchList.addAll(productList);
        }else {
            for (Product product : productList){
                if (product.getName().toLowerCase(Locale.getDefault()).contains(searchText)){
                    searchList.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }

}
