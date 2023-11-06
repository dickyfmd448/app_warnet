package com.example.app_warnet.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_warnet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WarnetEdit extends AppCompatActivity {

    String UserID, WarnetID;
    FirebaseFirestore fStore;
    String wNama, wAlamat, wHarga, wFasilitas, wMaps;
    EditText editNameWarnet, editAlamatWarnet, editHargaWarnet, editFasilitasWarnet, editMapsWarnet;
    Button btn_batalupdatenet, btn_updatenet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warnet_edit);

        UserID          = getIntent().getStringExtra("UserID");
        WarnetID         = getIntent().getStringExtra("WarnetID");
        fStore              = FirebaseFirestore.getInstance();

        editNameWarnet = findViewById(R.id.ubahnamawarnet);
        editAlamatWarnet = findViewById(R.id.ubahalamatwarnet);
        editHargaWarnet = findViewById(R.id.ubahhargawarnet);
        editFasilitasWarnet = findViewById(R.id.ubahfasilitaswarnet);
        editMapsWarnet = findViewById(R.id.ubahmapswarnet);

        DocumentReference df = fStore.collection("Warnet").document(WarnetID);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                editNameWarnet.setText(documentSnapshot.getString("NetName"));
                editAlamatWarnet.setText(documentSnapshot.getString("Alamat"));
                editHargaWarnet.setText(documentSnapshot.getString("Harga"));
                editFasilitasWarnet.setText(documentSnapshot.getString("Fasilitas"));
                editMapsWarnet.setText(documentSnapshot.getString("Maps"));

                wNama      = documentSnapshot.getString("NetName");
                wAlamat   = documentSnapshot.getString("Alamat");
                wHarga     = documentSnapshot.getString("Harga");
                wFasilitas   = documentSnapshot.getString("Fasilitas");
                wMaps = documentSnapshot.getString("Maps");
            }
        });

        btn_updatenet = findViewById(R.id.btn_updatedatawarnet);
        btn_updatenet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WarnetEdit.this);
                builder.setMessage("Ingin Menyimpan Perubahan ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        fStore.collection("Warnet").document(WarnetID).update("NetName", editNameWarnet.getText().toString());
                        fStore.collection("Warnet").document(WarnetID).update("Alamat", editAlamatWarnet.getText().toString());
                        fStore.collection("Warnet").document(WarnetID).update("Harga", editHargaWarnet.getText().toString());
                        fStore.collection("Warnet").document(WarnetID).update("Fasilitas", editFasilitasWarnet.getText().toString());
                        fStore.collection("Warnet").document(WarnetID).update("Maps", editMapsWarnet.getText().toString());
                        Intent intent = new Intent(WarnetEdit.this, WarnetDetail.class);
                        intent.putExtra("WarnetID", WarnetID);
                        intent.putExtra("UserID", UserID);
                        startActivity(intent);
                        Toast.makeText(WarnetEdit.this, "Perubahan Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btn_batalupdatenet = findViewById(R.id.btn_batalupdatedatawarnet);
        btn_batalupdatenet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WarnetEdit.this);
                builder.setMessage("Ingin Membatalkan Perubahan ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(WarnetEdit.this, WarnetDetail.class);
                        intent.putExtra("WarnetID", WarnetID);
                        intent.putExtra("UserID", UserID);
                        startActivity(intent);
                        Toast.makeText(WarnetEdit.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}