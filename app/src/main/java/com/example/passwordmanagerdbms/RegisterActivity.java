package com.example.passwordmanagerdbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.hbb20.CountryCodePicker;

public class RegisterActivity extends AppCompatActivity {
    EditText mobileNo_reg;
    FirebaseDatabase db ;
    DatabaseReference ref;
    TextView signupGo;
    String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mobileNo_reg=(EditText) findViewById(R.id.mobileNo_reg);
        Button verify=findViewById(R.id.verify);
        signupGo = findViewById(R.id.signup_go);

        signupGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, SignUpActivity.class));
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 num = mobileNo_reg.getText().toString().trim();

                if(TextUtils.isEmpty(num)){
                    mobileNo_reg.setFocusable(true);
                    mobileNo_reg.setError("mobile number is required");
                }
                db= FirebaseDatabase.getInstance();
                ref=db.getReference();
                ref.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.getKey().equals(num)){
                                goToPassword();
                            }
                            else {
                                mobileNo_reg.setFocusable(true);
                                mobileNo_reg.setError("This number does not exist");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            private void goToPassword() {
                Intent intent = new Intent(RegisterActivity.this, PasswordActivity.class);
                intent.putExtra("num", num);
                startActivity(intent);

            }
        });

    }
}
