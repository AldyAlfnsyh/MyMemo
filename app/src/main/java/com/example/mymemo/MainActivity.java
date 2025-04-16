package com.example.mymemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CatatanAdapter catatanAdapter;
    private List<Catatan> catatanList;

    // add database reference
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        catatanList = new ArrayList<>();
        catatanAdapter = new CatatanAdapter(catatanList, this);
        recyclerView.setAdapter(catatanAdapter);

        //add inisialisasi ke firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("catatans");
        findViewById(R.id.btnAddCatatan).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddEditActivity.class));
        });

        loadItems();
    }

    private void loadItems() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                catatanList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Catatan catatan = dataSnapshot.getValue(Catatan.class);
                    catatanList.add(catatan);
                }
                catatanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}