package com.example.terrace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.terrace.Adapter.ProductAdapter;
import com.example.terrace.databinding.ActivityAdminPageBinding;
import com.example.terrace.model.Product;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminPageActivity extends AppCompatActivity implements ProductAdapter.ProductOnClickListener {
    private ActivityAdminPageBinding binding;
    //firebase auth
    private ArrayList<Product> arr_Product;
    private ProductAdapter adapterProduct;
    private RecyclerView recyclerViewSP;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityAdminPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPageActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
        addControls();
    }
    private void filter(String text) {
        ArrayList<Product> filteredList = new ArrayList<>();
        for (Product item : arr_Product) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapterProduct.filterList(filteredList);
    }
    private void addControls() {
        recyclerViewSP = findViewById(R.id.rvSP);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewSP.setLayoutManager(gridLayoutManager);

        db = FirebaseFirestore.getInstance();
        arr_Product = new ArrayList<Product>();

        loadServices();
        adapterProduct = new ProductAdapter(this, arr_Product, this);
        recyclerViewSP.setAdapter(adapterProduct);

    }
    private void loadServices() {
        db.collection("drinks").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("Error", error.getMessage());
                        } else {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                arr_Product.add(dc.getDocument().toObject(Product.class));
                            }
                            adapterProduct.notifyDataSetChanged();
                        }

                    }
                });
    }


    @Override
    public void onClickAtItem(int position) {

    }
}