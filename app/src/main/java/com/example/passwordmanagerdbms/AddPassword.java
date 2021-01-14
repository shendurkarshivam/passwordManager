package com.example.passwordmanagerdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPassword extends AppCompatActivity {
    private EditText username, password, account;
    private Button saveButton;
    String userNameText, passwordText, accountText, number;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        number = getIntent().getStringExtra("num");

        ref = FirebaseDatabase.getInstance().getReference();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password_of_account);
        account = findViewById(R.id.account);
        saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameText = username.getText().toString().trim();
                passwordText = password.getText().toString().trim();
                accountText = account.getText().toString().trim();

                if(TextUtils.isEmpty(userNameText)){
                    username.setFocusable(true);
                    username.setError("Enter the Username please");
                }
                if(TextUtils.isEmpty(passwordText)){
                    password.setFocusable(true);
                    password.setError("Enter The password");
                }
                if(TextUtils.isEmpty(accountText)){
                    account.setFocusable(true);
                    account.setError("Enter the name of account");
                }
                saveData();
            }

            private void saveData() {
                ref.child("UserPasswords").child(number).child(accountText).child("account").setValue(accountText);
                ref.child("UserPasswords").child(number).child(accountText).child("username").setValue(userNameText);
                ref.child("UserPasswords").child(number).child(accountText).child("password").setValue(passwordText);



                Toast.makeText(getApplicationContext(), "The new Password has been set", Toast.LENGTH_LONG).show();
                int secondsDelayed = 1;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(AddPassword.this, ShowPasswords.class);
                        intent.putExtra("num", number);
                        startActivity(intent);
                        finish();
                    }
                }, secondsDelayed * 1000);
            }
        });
    }
}
