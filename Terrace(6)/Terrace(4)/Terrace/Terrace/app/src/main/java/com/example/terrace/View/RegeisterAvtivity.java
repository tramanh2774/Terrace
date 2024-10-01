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

import com.example.terrace.model.User;
import com.example.terrace.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegeisterAvtivity extends AppCompatActivity {
    CardView cardLog;
    ImageButton btnRes, btnBack;
    EditText edtEmail, edtPass,edtAccount, edtPhone;
    String account, pass, email,phone;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        btnBack =  findViewById(R.id.btnBack);
        edtEmail =  findViewById(R.id.edtEmail);
        edtPass =  findViewById(R.id.edtPass);
        edtAccount =  findViewById(R.id.edtAccount);
        edtPhone =  findViewById(R.id.edtPhone);

        cardLog =  findViewById(R.id.cardLog);
        cardLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegeisterAvtivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        btnRes =  findViewById(R.id.btnRegister);
        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Register();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Register(){
        email = edtEmail.getText().toString();
        account = edtAccount.getText().toString();
        pass = edtPass.getText().toString();
        phone = edtPhone.getText().toString();
        String role ="custumer";
        if (email.isEmpty() || account.isEmpty() || pass.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(account,pass,email,role,phone);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user")
                .add(user)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        Toast.makeText(this, "Register sussessfull", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegeisterAvtivity.this, MainActivity.class);
                        i.putExtra("name",account);
                        startActivity(i);

                    }
                    else{
                        Toast.makeText(this, " Register fail", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

}