package com.example.mymemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEditActivity extends AppCompatActivity {
    private EditText etJudul, etDescription, etLokasi;

    //add database reference

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etJudul = findViewById(R.id.etJudul);
        etDescription = findViewById(R.id.etDescription);
        etLokasi = findViewById(R.id.etLokasi);
//        etGambar = findViewById(R.id.etGambar);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            String id = intent.getStringExtra("id");
            String judul = intent.getStringExtra("judul");
            String description = intent.getStringExtra("description");
            String lokasi = intent.getStringExtra("lokasi");
//            String gambar = intent.getStringExtra("gambar");

            etJudul.setText(judul);
            etDescription.setText(description);
            etLokasi.setText(lokasi);
//            etGambar.setText(gambar);

            findViewById(R.id.btnSave).setOnClickListener(v -> {
                String newJudul = etJudul.getText().toString();
                String newDescription = etDescription.getText().toString();
                String newLokasi = etLokasi.getText().toString();
//                String newGambar = etGambar.getText().toString();

                updateCatatan(id, newJudul, newDescription, newLokasi);
                finish();
            });
        }else{
            findViewById(R.id.btnSave).setOnClickListener(view -> saveCatatan());
        }

        //add database reference untuk inisialisasi

        databaseReference = FirebaseDatabase.getInstance().getReference("catatans");

        
    }

    private void saveCatatan() {

        String judul = etJudul.getText().toString();
        String description = etDescription.getText().toString();
        String lokasi = etLokasi.getText().toString();
//        String gambar = etGambar.getText().toString();

        if(judul.isEmpty() || description.isEmpty() || lokasi.isEmpty() ){
            Toast.makeText(this,"Filded harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey();

        Catatan catatan = new Catatan(id, judul, description, lokasi);
        databaseReference.child(id).setValue(catatan).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this,"Berhasil di add!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Gagal add!", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }

    private void updateCatatan(String id, String newJudul, String newDescription, String newLokasi) {
        DatabaseReference catatanRef = FirebaseDatabase.getInstance().getReference("catatans").child(id);
        Catatan updateCatatan = new Catatan(id, newJudul, newDescription, newLokasi);
        catatanRef.setValue(updateCatatan).addOnCompleteListener(task ->{
            if(task.isSuccessful()){
                Toast.makeText(this,"Berhasil Update!",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Gagal Update!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void MainAct(View view){
        Intent openMainAct = new Intent(this,MainActivity.class);
        startActivity(openMainAct);
    }
}