package com.example.terrace.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.terrace.R;
import com.example.terrace.model.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Product> arr_Product;
    ProductOnClickListener productOnClickListener;

    FirebaseFirestore db;

    public ProductAdapter(Context context, ArrayList<Product> arr_Product, ProductOnClickListener productOnClickListener){
        this.context = context;
        this.arr_Product = arr_Product;
        this.productOnClickListener = productOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewSanPham = layoutInflater.inflate(R.layout.productitems,parent,false);
        ViewHolder viewHolderSP = new ViewHolder(viewSanPham);
        return viewHolderSP;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();
        Product sp =  arr_Product.get(position);
        String ma = sp.getMaSP();
        Picasso.get().load(sp.getImage()).into(holder.ivHinhSP);
        String g = String.valueOf(sp.getPrice());
        holder.txtGiaSP.setText(g);
        holder.txtTenSP.setText(sp.getName());
    }

    @Override
    public int getItemCount() {
        return arr_Product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHinhSP;
        TextView txtTenSP, txtGiaSP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhSP = itemView.findViewById(R.id.ivHinhSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
        }
    }
    public void filterList(ArrayList<Product> filteredList) {
        this.arr_Product = filteredList;
        notifyDataSetChanged();
    }
    public interface ProductOnClickListener {
        void onClickAtItem(int position);
    }
}
