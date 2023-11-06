package com.example.app_warnet.user;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Toast;

import com.example.app_warnet.MainActivity;
import com.example.app_warnet.R;
import com.example.app_warnet.ViewAdapterWarnet;
import com.example.app_warnet.ViewModel;
import com.example.app_warnet.ViewModelWarnet;
import com.example.app_warnet.admin.AdminDataWarnetActivity;
import com.example.app_warnet.admin.AdminMainActivity;
import com.example.app_warnet.model.ViewAdapterNetUser;
import com.example.app_warnet.model.ViewModelNetUser;
import com.example.app_warnet.model.WarnetDetailUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class UserFindActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private androidx.appcompat.widget.Toolbar toolbar;
    private View hView;
    private CollectionReference collectionReference;
    SearchView searchView;
    private ViewAdapterNetUser viewAdapterNetUser;
    private FirebaseFirestore fStore;
    private RecyclerView recyclerView;
    ArrayList arrayList;
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
        setContentView(R.layout.activity_user_find);

        UserID                  = getIntent().getStringExtra("UserID");
        fStore                  = FirebaseFirestore.getInstance();
        collectionReference     = fStore.collection("Warnet");

        searchView              = findViewById(R.id.searchdatawarnetuser);
        setupSearchView();

        recyclerView            = (RecyclerView) findViewById(R.id.listdatawarnetuser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList               = new ArrayList<ViewModelWarnet>();
        viewAdapterNetUser      = new ViewAdapterNetUser(arrayList, UserID, UserFindActivity.this);
        recyclerView.setAdapter(viewAdapterNetUser);
        EventChangeListener();

        toolbar         = findViewById(R.id.admin_toolbar);
        toolbar.setTitle("Find Warnet");
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
                    Intent intent = new Intent(UserFindActivity.this, UserProfileActivity.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                    Toast.makeText(UserFindActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.find) {
                    Toast.makeText(UserFindActivity.this, "Anda Sudah Berada di Menu Find", Toast.LENGTH_SHORT).show();
                }else if (itemId == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserFindActivity.this);
                    builder.setMessage("Apa Anda Yakin Ingin Log Out ?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(UserFindActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(UserFindActivity.this, "Log Out Berhasil", Toast.LENGTH_SHORT).show();
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
                } else if (itemId == R.id.home) {
                    Intent intent = new Intent(UserFindActivity.this, UserMainActivity.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                    Toast.makeText(UserFindActivity.this, "Home", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFirestore (query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFirestore(newText);
                return true;
            }
        });
    }
    private void searchFirestore(String query){
        Query searchQuery = collectionReference.whereEqualTo("NetName", query);
        searchQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<ViewModelNetUser> results = queryDocumentSnapshots.toObjects(ViewModelNetUser.class);
                viewAdapterNetUser.setList(results);
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
                                arrayList.add(dc.getDocument().toObject(ViewModelNetUser.class));
                            }
                            viewAdapterNetUser.notifyDataSetChanged();
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserFindActivity.this, UserMainActivity.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
        super.onBackPressed();
    }

}