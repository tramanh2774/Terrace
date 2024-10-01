package com.example.terrace.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.terrace.ActivityCart;
import com.example.terrace.Adapter.SanPhamAdapter;
import com.example.terrace.Interface.icDrinkClick;
import com.example.terrace.R;
import com.example.terrace.model.Drinks;
import com.example.terrace.model.cart;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewSP;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<Drinks> arr_Drinks;
    ImageButton btnLogout, btnCart;

    TextView txtName;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        addControls();
        loadData();

        // Điều chỉnh padding khi có system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Xử lý sự kiện khi chọn một mục
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                Toast.makeText(MainActivity.this, "Home selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_gift) {
                Toast.makeText(MainActivity.this, "Gift selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_list) {
                Toast.makeText(MainActivity.this, "List selected", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        //su kien logout
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        //Gan username
        txtName = findViewById(R.id.txtName);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        txtName.setText(name.toString());

        btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivityCart.class);
                i.putExtra("total",totalPrice);
                startActivity(i);
            }
        });


    }
    String name;
    private void loadData() {
        // Listen for real-time updates in the 'drinks' collection
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("drinks")
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null) {
                        Log.w("Firestore", "Listen failed.", error);
                        return;
                    }

                    // Clear the list to avoid duplicates
                    arr_Drinks.clear();

                    // Duyệt qua từng tài liệu (product) trong collection "drinks"
                    for (QueryDocumentSnapshot document : snapshots) {
                        // Chuyển đổi tài liệu thành đối tượng Drinks
                        Drinks drinks = document.toObject(Drinks.class);
                        arr_Drinks.add(drinks);
                    }

                    // Notify adapter that data has changed
                    sanPhamAdapter.notifyDataSetChanged();
                });
    }

    private void addControls() {
        recyclerViewSP=findViewById(R.id.recyclerSanPham);
        arr_Drinks =new ArrayList<>();
        sanPhamAdapter=new SanPhamAdapter(this, arr_Drinks, new icDrinkClick() {
            @Override
            public void onDrinkClick(Drinks drinks) {
                AddProduct(drinks);
            }
        });
        recyclerViewSP.setAdapter(sanPhamAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager=
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewSP.setLayoutManager(staggeredGridLayoutManager);
    }
    float totalPrice = 0;
    private void AddProduct(Drinks drinks) {
        // Tạo một đối tượng Cart từ sản phẩm Drinks
        cart cartItem = new cart(drinks.getName(), drinks.getImage(),name, drinks.getPrice(), 1.0f);

        // Thêm sản phẩm vào Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cart")
                .add(cartItem)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(MainActivity.this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    totalPrice += cartItem.getPrice();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Thêm sản phẩm vào giỏ hàng thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}