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

import com.example.app_warnet.LoginActivity;
import com.example.app_warnet.MainActivity;
import com.example.app_warnet.R;
import com.example.app_warnet.ViewAdapter;
import com.example.app_warnet.ViewData;
import com.example.app_warnet.ViewModel;
import com.example.app_warnet.model.TambahDataUser;
import com.example.app_warnet.model.UserDetail;
import com.example.app_warnet.user.UserFindActivity;
import com.example.app_warnet.user.UserMainActivity;
import com.example.app_warnet.user.UserProfileActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminUserDataActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button btn_tambahuser;
    ViewAdapter viewAdapter;
    FirebaseFirestore fStore;
    RecyclerView recyclerView;
    ArrayList arrayList;
    ActionBarDrawerToggle actionBarDrawerToggle;

    CardView cv_usr1, cv_usr2, cv_usr3, cv_usr4, cv_usr5;
    private androidx.appcompat.widget.Toolbar toolbar;
    private View hView;
    String UserID, userID;

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
        setContentView(R.layout.activity_admin_user_data);
        UserID          = getIntent().getStringExtra("UserID");
        userID          = getIntent().getStringExtra("userID");
        fStore          = FirebaseFirestore.getInstance();

        toolbar         = findViewById(R.id.admin_toolbar);
        toolbar.setTitle("Data User");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.listdatauser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList       = new ArrayList<ViewModel>();
        viewAdapter     = new ViewAdapter(arrayList, UserID, AdminUserDataActivity.this);
        recyclerView.setAdapter(viewAdapter);
        EventChangeListener();

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
                    Intent intent = new Intent(AdminUserDataActivity.this, AdminMainActivity.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                    Toast.makeText(AdminUserDataActivity.this, "Home", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.user) {
                    Toast.makeText(AdminUserDataActivity.this, "Anda Sudah di Halaman Data User", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.warnet) {
                    Intent intent = new Intent(AdminUserDataActivity.this, AdminDataWarnetActivity.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                    Toast.makeText(AdminUserDataActivity.this, "Membuka Data Warnet", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminUserDataActivity.this);
                    builder.setMessage("Apa Anda Yakin Ingin Log Out ?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(AdminUserDataActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(AdminUserDataActivity.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
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

        btn_tambahuser = findViewById(R.id.btn_tambahdatauser);
        btn_tambahuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminUserDataActivity.this, TambahDataUser.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });
    }

    private void EventChangeListener() {
        fStore.collection("Users").orderBy("Email", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e(TAG, "onEvent: " + error.getMessage());
                            return;
                        }
                        for (DocumentChange dc: value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                arrayList.add(dc.getDocument().toObject(ViewModel.class));
                            }
                            viewAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminUserDataActivity.this, AdminMainActivity.class);
        intent.putExtra("UserID", UserID);
        intent.putExtra("userID", userID);
        startActivity(intent);
        super.onBackPressed();
    }
}