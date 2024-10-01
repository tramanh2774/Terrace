package com.example.terrace;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.terrace.Adapter.CartAdapter;
import com.example.terrace.Interface.icCartClick;
import com.example.terrace.model.Drinks;
import com.example.terrace.model.cart;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ActivityCart extends AppCompatActivity {

    RecyclerView rvCart;
    ArrayList<cart> arr_Cart;
    CartAdapter cartAdapter;
    TextView txtTotalPrice;
    float total;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        arr_Cart = new ArrayList<>();
        loadData();
        rvCart = findViewById(R.id.rvCart);

        cartAdapter = new CartAdapter(this, arr_Cart, new icCartClick() {
            @Override
            public void onCartClick(cart cart) {

            }
        });
        rvCart.setAdapter(cartAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager=
                new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        rvCart.setLayoutManager(staggeredGridLayoutManager);

      txtTotalPrice = findViewById(R.id.txtTotalPrice);
        Intent i = getIntent();
        total = i.getFloatExtra("total", 0);
        txtTotalPrice.setText(String.valueOf(total));

    }
    private void loadData() {
        // Listen for real-time updates in the 'drinks' collection
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart")
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null) {
                        Log.w("Firestore", "Listen failed.", error);
                        return;
                    }

                    // Clear the list to avoid duplicates
                    arr_Cart.clear();

                    // Duyệt qua từng tài liệu (product) trong collection "drinks"
                    for (QueryDocumentSnapshot document : snapshots) {
                        // Chuyển đổi tài liệu thành đối tượng Drinks
                        cart cart = document.toObject(cart.class);
                        arr_Cart.add(cart);
                    }

                    // Notify adapter that data has changed
                    cartAdapter.notifyDataSetChanged();
                });
    }
}