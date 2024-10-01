package com.example.terrace.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.terrace.AdminActivity;
import com.example.terrace.AdminPageActivity;
import com.example.terrace.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    CardView btnRes;
    ImageButton btnLog, btnBack;
    EditText edtEmail, edtPass;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btnBack =  findViewById(R.id.btnBack);
        edtEmail =  findViewById(R.id.edtEmail);
        edtPass =  findViewById(R.id.edtPass);

        btnRes =  findViewById(R.id.cardRes);
        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegeisterAvtivity.class);
                startActivity(i);
            }
        });
        btnLog =  findViewById(R.id.btnLogin);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckUser();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(LoginActivity.this, Introduce.class);
               startActivity(i);
            }
        });
    }

    private void CheckUser() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();

        // Check if email or password is empty
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Query Firestore for matching account and password
        db.collection("user")
                .whereEqualTo("mail", email)
                .whereEqualTo("pass", pass)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        // User found, get the role
                        for (DocumentSnapshot document : task.getResult()) {
                            String role = document.getString("role");

                            if (role != null && role.equals("admin")) {
                                // If the user is an admin
                                Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, AdminPageActivity.class); // Assuming you have an AdminActivity
                                startActivity(i);
                            } else {
                                // If the user is not an admin
                                Toast.makeText(this, "Welcome "+document.getString("account"), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("name",document.getString("account"));// Regular user activity
                                startActivity(i);
                            }
                            finish();
                        }
                    } else {
                        // Invalid email or password
                        Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error connecting to database", Toast.LENGTH_SHORT).show();
                });
    }



}