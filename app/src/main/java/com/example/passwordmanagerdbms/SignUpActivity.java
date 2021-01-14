package com.example.passwordmanagerdbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private Button register;
    private EditText number, password;
    private TextView signIn;
    FirebaseDatabase db;
    DatabaseReference ref;
     String num;
     String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        signIn = findViewById(R.id.signin);
        register = findViewById(R.id.verify_new);
        number = findViewById(R.id.mobileNo_reg);
        password = findViewById(R.id.password);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, RegisterActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 num=number.getText().toString().trim();
                 pass=password.getText().toString().trim();

                if(TextUtils.isEmpty(num)){
                    number.setFocusable(true);
                    number.setError("mobile number is required");

                }
                if(TextUtils.isEmpty(pass)){
                    password.setFocusable(true);
                    password.setError("mobile number is required");

                }
                ref.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Log.i("The value of snap", snap.getKey());
                            if(snap.getKey().equals(num)){
                                number.setFocusable(true);
                                number.setError("This is already taken");
                            }
                            else{
                                addUser();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            private void addUser() {
                ref.child("Users").child(num).child("Password").setValue(pass);
                Intent intent = new Intent(SignUpActivity.this, ShowPasswords.class);
                intent.putExtra("num", num);
                startActivity(intent);
                finish();
            }

        });
    }
}
