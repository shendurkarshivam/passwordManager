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

public class PasswordActivity extends AppCompatActivity {
    String number, pass;
    DatabaseReference ref;
    private Button loginButton;
    private EditText passText;
    private TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        loginButton = findViewById(R.id.verify);
        passText = findViewById(R.id.password_log);
        signUpText = findViewById(R.id.signin_go);

        number = getIntent().getStringExtra("num");

        ref = FirebaseDatabase.getInstance().getReference();

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PasswordActivity.this, SignUpActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = passText.getText().toString().trim();
                if(TextUtils.isEmpty(pass)){
                    passText.setFocusable(true);
                    passText.setError("Password can't be empty");
                }
                ref.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.getKey().equals(number)){
                                Log.i("The password for"+number, snap.child("Password").getValue().toString());
                                if(snap.child("Password").getValue().toString().equals(pass)){
                                    goToPasswordPage();
                                }
                                else{
                                    passText.setFocusable(true);
                                    passText.setError("Wrong Password entered ! Try again");
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void goToPasswordPage() {
                Intent intent = new Intent(PasswordActivity.this, ShowPasswords.class);
                intent.putExtra("num", number);
                startActivity(intent);
                finish();
            }
        });


    }
}
