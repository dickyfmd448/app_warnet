package com.example.app_warnet.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_warnet.MainActivity;
import com.example.app_warnet.R;
import com.example.app_warnet.ViewData;
import com.example.app_warnet.user.UserMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminMainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private androidx.appcompat.widget.Toolbar toolbar;
    private View hView;
    String UserID;
    TextView welcome_admin;
    FirebaseFirestore firestore;

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
        setContentView(R.layout.activity_admin_main);
        UserID          = getIntent().getStringExtra("UserID");
        welcome_admin   = findViewById(R.id.welcome_admin);
        firestore       = FirebaseFirestore.getInstance();

        toolbar         = findViewById(R.id.admin_toolbar);
        toolbar.setTitle("Dashboard");
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
                    Toast.makeText(AdminMainActivity.this, "Anda Sudah Berada di Homepage", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.warnet) {
                    Intent intent = new Intent(AdminMainActivity.this, AdminDataWarnetActivity.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                    Toast.makeText(AdminMainActivity.this, "Membuka Data Warnet", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.user) {
                    Intent intent = new Intent(AdminMainActivity.this, AdminUserDataActivity.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                    Toast.makeText(AdminMainActivity.this, "Membuka Data User", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);
                    builder.setMessage("Apa Anda Yakin Ingin Log Out ?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(AdminMainActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(AdminMainActivity.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
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

        DocumentReference df = firestore.collection("Users").document(UserID);
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                welcome_admin.setText(documentSnapshot.getString("Name"));
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);
        builder.setMessage("Apa Anda Yakin Ingin Log Out ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(AdminMainActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(AdminMainActivity.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
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
}