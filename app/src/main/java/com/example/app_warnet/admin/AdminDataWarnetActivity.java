package com.example.app_warnet.admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app_warnet.MainActivity;
import com.example.app_warnet.R;
import com.example.app_warnet.ViewAdapter;
import com.example.app_warnet.ViewAdapterWarnet;
import com.example.app_warnet.ViewModel;
import com.example.app_warnet.ViewModelWarnet;
import com.example.app_warnet.model.TambahDataWarnet;
import com.example.app_warnet.model.UserDetail;
import com.example.app_warnet.model.WarnetDetail;
import com.example.app_warnet.user.UserMainActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminDataWarnetActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ViewAdapterWarnet viewAdapterWarnet;
    FirebaseFirestore fStore;
    RecyclerView recyclerView;
    ArrayList arrayList;

    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private androidx.appcompat.widget.Toolbar toolbar;
    private View hView;
    Button btn_adddatanet;
    String UserID;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data_warnet);

        UserID                  = getIntent().getStringExtra("UserID");
        fStore                  = FirebaseFirestore.getInstance();

        recyclerView            = (RecyclerView) findViewById(R.id.listdatawarnet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList               = new ArrayList<ViewModelWarnet>();
        viewAdapterWarnet       = new ViewAdapterWarnet(arrayList, UserID, AdminDataWarnetActivity.this);
        recyclerView.setAdapter(viewAdapterWarnet);
        EventChangeListener();

        toolbar         = findViewById(R.id.admin_toolbar);
        toolbar.setTitle("Data Warnet");
        setSupportActionBar(toolbar);

        drawerLayout    = findViewById(R.id.drawerLayout);
        navigationView  = findViewById(R.id.nav_view);
        hView           = navigationView.getHeaderView(0);

        actionBarDrawerToggle   = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Intent intent = new Intent(AdminDataWarnetActivity.this, AdminMainActivity.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                    Toast.makeText(AdminDataWarnetActivity.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.user) {
                    Intent intent = new Intent(AdminDataWarnetActivity.this, AdminUserDataActivity.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                    Toast.makeText(AdminDataWarnetActivity.this, "Membuka Data User", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.warnet) {
                    Toast.makeText(AdminDataWarnetActivity.this, "Anda Sudah Di Halaman Data Warnet", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminDataWarnetActivity.this);
                    builder.setMessage("Apa Anda Yakin Ingin Log Out ?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(AdminDataWarnetActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(AdminDataWarnetActivity.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
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
                return false;
            }
        });

        btn_adddatanet = findViewById(R.id.btn_addwarnet);
        btn_adddatanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDataWarnetActivity.this, TambahDataWarnet.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });
    }
    private void EventChangeListener() {
        fStore.collection("Warnet").orderBy("NetName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e(TAG, "onEvent: " + error.getMessage());
                            return;
                        }
                        for (DocumentChange dc: value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                arrayList.add(dc.getDocument().toObject(ViewModelWarnet.class));
                            }
                            viewAdapterWarnet.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminDataWarnetActivity.this, AdminMainActivity.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
        super.onBackPressed();
    }
}