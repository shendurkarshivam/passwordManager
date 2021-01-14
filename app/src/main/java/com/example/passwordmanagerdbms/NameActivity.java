package com.example.passwordmanagerdbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class NameActivity extends AppCompatActivity {

    Button procced;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        procced = findViewById(R.id.proceed);

        procced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NameActivity.this, RegisterActivity.class));
                finish();
            }
        });


    }
}
