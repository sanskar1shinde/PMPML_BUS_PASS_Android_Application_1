package com.example.finalsemproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewPassess extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AdminViewPassessAdapter adminViewPassessAdapter;
    ArrayList<PMPMLTable> list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_passess);

        recyclerView = findViewById(R.id.passList);
        databaseReference= FirebaseDatabase.getInstance().getReference("PMPMLPASSDATA");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adminViewPassessAdapter=new AdminViewPassessAdapter(this,list);
        recyclerView.setAdapter(adminViewPassessAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    PMPMLTable user=dataSnapshot.getValue(PMPMLTable.class);
                    list.add(user);
                }
                adminViewPassessAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
