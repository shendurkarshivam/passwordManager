package com.example.passwordmanagerdbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ShowPasswords extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView passList;
    String number;
    //MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_passwords);

        number = getIntent().getStringExtra("num");

        fab = findViewById(R.id.fab_create_event);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPasswords();
            }

            private void addPasswords(){
                Intent intent = new Intent(ShowPasswords.this, AddPassword.class);
                intent.putExtra("num", number);
                startActivity(intent);
            }
        });
    }
    public String getNumber(){
        return number;
    }

    @Override
    protected void onStart() {
        super.onStart();

        passList = (RecyclerView)findViewById(R.id.recyclerView);
        passList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options=
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("UserPasswords").child(number), Model.class)
                        .build();

        FirebaseRecyclerAdapter<Model, ShowPasswords.MyViewHolder> adapter =new FirebaseRecyclerAdapter<Model, ShowPasswords.MyViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Model model) {
                holder.user.setText(model.getUsername());
                holder.account.setText(model.getAccount());
                holder.pass.setText(model.getPassword());

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String reference = getRef(position).getKey();
                        Log.i("password ----------", reference);
                        Log.i("number --------", number);


                        FirebaseDatabase.getInstance().getReference().child("UserPasswords").child(number).child(reference).removeValue();
                        Toast.makeText(getApplicationContext(), "The Account Details of " + reference + " are deleted", Toast.LENGTH_LONG).show();
                    }
                });
                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String reference = getRef(position).getKey();
                        Intent intent = new Intent(ShowPasswords.this, EditPassword.class);
                        intent.putExtra("reference", reference);
                        intent.putExtra("num", number);

                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pass_row, parent, false);
                return new MyViewHolder(view);
            }
        };
        //adapter = new MyAdapter(options);
        passList.setAdapter(adapter);
        adapter.startListening();

    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView user, pass, account;
        ImageView delete, edit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.user);
            pass = itemView.findViewById(R.id.passsss);
            account = itemView.findViewById(R.id.title_pass);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }

}
