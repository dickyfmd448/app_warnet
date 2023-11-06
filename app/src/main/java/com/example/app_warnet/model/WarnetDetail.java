package com.example.app_warnet.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.app_warnet.MainActivity;
import com.example.app_warnet.R;
import com.example.app_warnet.admin.AdminDataWarnetActivity;
import com.example.app_warnet.admin.AdminMainActivity;
import com.example.app_warnet.admin.AdminUserDataActivity;
import com.example.app_warnet.user.UserProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class WarnetDetail extends AppCompatActivity {

    String UserID, WarnetID;
    private TextView nama_warnet, alamat_warnet, harga_warnet, fasilitas_warnet, map_warnet;
    private String wNama, wAlamat, wHarga, wFasilitas, wMap, wImageURL;
    FirebaseFirestore fStore;
    private ImageView photoWarnet;
    CardView cv_backnetdetail;
    Button btn_deletenetdetail, btn_updatenetdetail;
    private String Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warnet_detail);
        fStore          = FirebaseFirestore.getInstance();

        UserID          = getIntent().getStringExtra("UserID");
        WarnetID        = getIntent().getStringExtra("WarnetID");

        btn_deletenetdetail = findViewById(R.id.btn_deletenet);
        btn_deletenetdetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(WarnetDetail.this);
                builder.setMessage("Apa anda yakin ingin menghapus data warnet ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        fStore.collection("Warnet").document(WarnetID)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(Tag, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(Tag, "Error deleting document", e);
                                    }
                                });
                        Intent intent = new Intent(WarnetDetail.this, AdminDataWarnetActivity.class);
                        intent.putExtra("WarnetID", WarnetID);
                        intent.putExtra("UserID", UserID);
                        startActivity(intent);
                        Toast.makeText(WarnetDetail.this, "Data Warnet Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        nama_warnet         = findViewById(R.id.warnetname);
        alamat_warnet       = findViewById(R.id.warnetlocation);
        harga_warnet        = findViewById(R.id.warnetprice);
        fasilitas_warnet    = findViewById(R.id.warnetfacility);
        map_warnet          = findViewById(R.id.warnetmaps);
        photoWarnet         = findViewById(R.id.warnetphoto);
        DocumentReference df = fStore.collection("Warnet").document(WarnetID);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                nama_warnet.setText(documentSnapshot.getString("NetName"));
                alamat_warnet.setText(documentSnapshot.getString("Alamat"));
                harga_warnet.setText(documentSnapshot.getString("Harga"));
                fasilitas_warnet.setText(documentSnapshot.getString("Fasilitas"));
                map_warnet.setText(documentSnapshot.getString("Maps"));
                Picasso.get().load(documentSnapshot.getString("ImageURL")).into(photoWarnet);

                wNama       = documentSnapshot.getString("NetName");
                wAlamat     = documentSnapshot.getString("Alamat");
                wHarga      = documentSnapshot.getString("Harga");
                wFasilitas  = documentSnapshot.getString("Fasilitas");
                wMap        = documentSnapshot.getString("Maps");
                wImageURL   = documentSnapshot.getString("ImageURL");
            }
        });

        btn_updatenetdetail = findViewById(R.id.btn_updatenet);
        btn_updatenetdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WarnetDetail.this, WarnetEdit.class);
                intent.putExtra("UserID", UserID);
                intent.putExtra("WarnetID", WarnetID);
                startActivity(intent);
            }
        });

        cv_backnetdetail = findViewById(R.id.cv_backwarnetdetail);
        cv_backnetdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WarnetDetail.this, AdminDataWarnetActivity.class);
                intent.putExtra("UserID", UserID);
                intent.putExtra("WarnetID", WarnetID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WarnetDetail.this, AdminDataWarnetActivity.class);
        intent.putExtra("UserID", UserID);
        intent.putExtra("WarnetID", WarnetID);
        startActivity(intent);
        super.onBackPressed();
    }
}