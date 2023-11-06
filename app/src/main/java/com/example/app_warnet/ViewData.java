package com.example.app_warnet;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.app_warnet.admin.AdminMainActivity;
import com.example.app_warnet.admin.AdminUserDataActivity;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore fStore;
    ViewAdapter viewAdapter;
    ArrayList arrayList;

    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        fStore          = FirebaseFirestore.getInstance();
        UserID          = getIntent().getStringExtra("UserID");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList       = new ArrayList<ViewModel>();
        viewAdapter     = new ViewAdapter(arrayList, UserID, ViewData.this);
        recyclerView.setAdapter(viewAdapter);
//        EventChangeListener();
    }
//    private void EventChangeListener() {
//        fStore.collection("Users").orderBy("Email", Query.Direction.ASCENDING)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error != null){
//                            Log.e(TAG, "onEvent: " + error.getMessage());
//                            return;
//                        }
//                        for (DocumentChange dc: value.getDocumentChanges()){
//                            if (dc.getType() == DocumentChange.Type.ADDED){
//                                arrayList.add(dc.getDocument().toObject(ViewModel.class));
//                            }
//                            viewAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewData.this, AdminUserDataActivity.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
        super.onBackPressed();
    }
}