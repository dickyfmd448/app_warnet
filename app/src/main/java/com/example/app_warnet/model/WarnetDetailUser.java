package com.example.app_warnet.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_warnet.R;
import com.example.app_warnet.admin.AdminDataWarnetActivity;
import com.example.app_warnet.admin.AdminMainActivity;
import com.example.app_warnet.maps.NetLocation;
import com.example.app_warnet.user.UserFindActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class WarnetDetailUser extends AppCompatActivity {

    String UserID, WarnetID;
    private TextView nama_warnet, alamat_warnet, harga_warnet, fasilitas_warnet, map_warnet;
    private String wNama, wAlamat, wHarga, wFasilitas, wMap, wImageURL;
    private ImageView photoWarnet;
    CardView cv_backnetdetailuser;
    Button btn_mapsusrdetail;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warnet_detail_user);

        fStore          = FirebaseFirestore.getInstance();

        UserID          = getIntent().getStringExtra("UserID");
        WarnetID        = getIntent().getStringExtra("WarnetID");

        btn_mapsusrdetail = findViewById(R.id.btn_mapuserdetail);
        btn_mapsusrdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WarnetDetailUser.this, NetLocation.class);
                intent.putExtra("UserID", UserID);
                intent.putExtra("WarnetID", WarnetID);
                startActivity(intent);
                Toast.makeText(WarnetDetailUser.this, "Menampilkan Google Maps", Toast.LENGTH_SHORT).show();
            }
        });

        cv_backnetdetailuser = findViewById(R.id.cv_backwarnetdetailuser);
        cv_backnetdetailuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WarnetDetailUser.this, UserFindActivity.class);
                intent.putExtra("UserID", UserID);
                intent.putExtra("WarnetID", WarnetID);
                startActivity(intent);
            }
        });

        nama_warnet         = findViewById(R.id.warnetnameuser);
        alamat_warnet       = findViewById(R.id.warnetlocationuser);
        harga_warnet        = findViewById(R.id.warnetpriceuser);
        fasilitas_warnet    = findViewById(R.id.warnetfacilityuser);
        map_warnet          = findViewById(R.id.warnetmapsuser);
        photoWarnet         = findViewById(R.id.warnetphotouser);
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

                wNama      = documentSnapshot.getString("NetName");
                wAlamat   = documentSnapshot.getString("Alamat");
                wHarga     = documentSnapshot.getString("Harga");
                wFasilitas   = documentSnapshot.getString("Fasilitas");
                wMap = documentSnapshot.getString("Maps");
                wImageURL   = documentSnapshot.getString("ImageURL");

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WarnetDetailUser.this, UserFindActivity.class);
        intent.putExtra("UserID", UserID);
        intent.putExtra("WarnetID", WarnetID);
        startActivity(intent);
        super.onBackPressed();
    }
}