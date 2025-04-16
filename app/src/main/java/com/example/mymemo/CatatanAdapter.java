package com.example.mymemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.ViewHolder>{
    private List<Catatan> listCatatan;
    private Context context;

    public CatatanAdapter(List<Catatan> listCatatan, Context context) {
        this.listCatatan = listCatatan;
        this.context = context;
    }

    @NonNull
    @Override
    public CatatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_catatan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatatanAdapter.ViewHolder holder, int position) {
        Catatan catatan = listCatatan.get(position);
        holder.Judul.setText(catatan.getJudul());
//        holder.Description.setText(catatan.getDescription());
        holder.Lokasi.setText(catatan.getLokasi());
//        holder.Gambar.setText(catatan.getGambar());

        // Handle Delete
        holder.btnDelete.setOnClickListener(v -> {
            deleteCatatan(catatan.getId()); // Panggil metode delete
        });

        // Handle Edit
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditActivity.class);
            intent.putExtra("id", catatan.getId());
            intent.putExtra("judul", catatan.getJudul());
            intent.putExtra("description", catatan.getDescription());

            intent.putExtra("lokasi", catatan.getLokasi());
//            intent.putExtra("gambar", catatan.getGambar());
            context.startActivity(intent); // Buka form Edit
        });
    }

    private void deleteCatatan(String id) {
        //delete catatn ke firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("catatans").child(id);
        databaseReference.removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(context, "Catatan berhasil dihapus!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Catatan berhasil dihapus!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCatatan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Judul, Description, Lokasi;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Judul = itemView.findViewById(R.id.Judul);
//            Description = itemView.findViewById(R.id.Description);
//            Gambar = itemView.findViewById(R.id.Gambar);
            Lokasi = itemView.findViewById(R.id.Lokasi);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
