package com.example.terrace.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.terrace.Interface.icDrinkClick;
import com.example.terrace.R;
import com.example.terrace.model.Drinks;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder>  {
    //khai bao bien
    Activity context;
    ArrayList<Drinks> arr_Drinks;
    icDrinkClick icDrinkClickm;
    public SanPhamAdapter(Activity context,ArrayList<Drinks> arr_Drinks,  icDrinkClick icDrinkClickm){
        this.context=context;
        this.arr_Drinks = arr_Drinks;
        this.icDrinkClickm = icDrinkClickm;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View viewSanPham=layoutInflater.inflate(R.layout.items_sp,parent, false);
        ViewHolder viewHolderSP=new ViewHolder(viewSanPham);
        return  viewHolderSP;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drinks sp= arr_Drinks.get(position);

        Glide.with(context)
                .load(sp.getImage())
                .into(holder.iv_1);
        holder.txtSanPham.setText(sp.getName());
        int g = (int) sp.getPrice();
        holder.txtGia.setText(String.valueOf(g));
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icDrinkClickm.onDrinkClick(sp);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr_Drinks.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        ImageButton iv_1;
        TextView txtSanPham, txtGia;
        ImageButton btnAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_1=itemView.findViewById(R.id.iv_1);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtSanPham=itemView.findViewById(R.id.txtSanPham);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
