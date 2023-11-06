package com.example.app_warnet.user;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_warnet.MainActivity;
import com.example.app_warnet.R;
import com.example.app_warnet.admin.AdminDataWarnetActivity;
import com.example.app_warnet.admin.AdminMainActivity;
import com.example.app_warnet.maps.NetLocation;
import com.example.app_warnet.model.TambahDataWarnet;
import com.example.app_warnet.model.UserEditProfile;
import com.example.app_warnet.model.WarnetDetailUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class UserProfileActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private androidx.appcompat.widget.Toolbar toolbar;
    private View hView;

    String UserID, userID;
    TextView nama, email;
    Button btn_deleteakunusrprofile, btn_updateakunusrprofile;
    FirebaseFirestore fStore;

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
        setContentView(R.layout.activity_user_profile);
        UserID          = getIntent().getStringExtra("UserID");
        userID          = getIntent().getStringExtra("userID");
        fStore       = FirebaseFirestore.getInstance();
        nama            = findViewById(R.id.nama);
        email           = findViewById(R.id.email);

        toolbar         = findViewById(R.id.admin_toolbar);
        toolbar.setTitle("Profile");
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
                if (itemId == R.id.user_profile) {
                    Toast.makeText(UserProfileActivity.this, "Anda Sudah di Halaman Profile", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.find) {
                    Intent intent = new Intent(UserProfileActivity.this, UserFindActivity.class);
                    intent.putExtra("UserID", UserID);
                    intent.putExtra("userID", userID);
                    startActivity(intent);
                    Toast.makeText(UserProfileActivity.this, "Let's Find Out", Toast.LENGTH_SHORT).show();
                }else if (itemId == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                    builder.setMessage("Apa Anda Yakin Ingin Log Out ?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(UserProfileActivity.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
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
                }else if (itemId == R.id.home) {
                    Intent intent = new Intent(UserProfileActivity.this, UserMainActivity.class);
                    intent.putExtra("UserID", UserID);
                    intent.putExtra("userID", userID);
                    startActivity(intent);
                    Toast.makeText(UserProfileActivity.this, "Home", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        DocumentReference df = fStore.collection("Users").document(UserID);
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                nama.setText(documentSnapshot.getString("Name"));
                email.setText(documentSnapshot.getString("Email"));
            }
        });
        btn_deleteakunusrprofile = findViewById(R.id.btn_deleteakunuser);
        btn_deleteakunusrprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileActivity.this);
                builder.setMessage("Apa anda yakin ingin menghapus akun ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(UserProfileActivity.this, "Hapus Akun Berhasil", Toast.LENGTH_SHORT).show();
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
        btn_updateakunusrprofile = findViewById(R.id.btn_updateakunuser);
        btn_updateakunusrprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, UserEditProfile.class);
                intent.putExtra("UserID", UserID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserProfileActivity.this, UserMainActivity.class);
        intent.putExtra("UserID", UserID);
        intent.putExtra("userID", userID);
        startActivity(intent);
        super.onBackPressed();
    }
}