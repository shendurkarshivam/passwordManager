package com.example.passwordmanagerdbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPassword extends AppCompatActivity {
    String number, reference;
    EditText accountName, userName, password;
    Button saveNew;
    String valueAccount, valueUserName, valuePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        number = getIntent().getStringExtra("num");
        reference = getIntent().getStringExtra("reference");

        accountName = findViewById(R.id.account);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password_of_account);
        saveNew = findViewById(R.id.verify_new);

        FirebaseDatabase.getInstance().getReference().child("UserPasswords").child(number).child(reference).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                valueAccount = snapshot.child("account").getValue().toString();
                valueUserName = snapshot.child("username").getValue().toString();
                valuePassword = snapshot.child("password").getValue().toString();
                Log.i("account ------", valueAccount);
                Log.i("username -----", valueUserName);
                Log.i("password -----", valuePassword);

                loadData();
            }

            private void loadData() {
                accountName.setText(valueAccount);
                userName.setText(valueUserName);
                password.setText(valuePassword);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        saveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("UserPasswords").child(number).child(reference).child("account").setValue(accountName.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("UserPasswords").child(number).child(reference).child("username").setValue(userName.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("UserPasswords").child(number).child(reference).child("password").setValue(password.getText().toString());
                
                Toast.makeText(getApplicationContext(), "The account details of " + reference + " are edited", Toast.LENGTH_LONG).show();

                int secondsDelayed = 1;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(EditPassword.this, ShowPasswords.class);
                        intent.putExtra("num", number);
                        startActivity(intent);
                        finish();
                    }
                }, secondsDelayed * 1200);

            }
        });

    }

}
