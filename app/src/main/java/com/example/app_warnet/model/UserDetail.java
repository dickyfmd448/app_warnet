package com.example.app_warnet.model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_warnet.LoginActivity;
import com.example.app_warnet.MainActivity;
import com.example.app_warnet.R;
import com.example.app_warnet.ViewData;
import com.example.app_warnet.admin.AdminDataWarnetActivity;
import com.example.app_warnet.admin.AdminMainActivity;
import com.example.app_warnet.admin.AdminUserDataActivity;
import com.example.app_warnet.user.UserProfileActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class UserDetail extends AppCompatActivity {

    String UserID, userID;
    TextView namadetailuser, emaildetailuser, passworddetailuser;
    private String uNama, uEmail, uPassword;
    CardView cv_bckuserdetail;
    FirebaseFirestore fStore;
    Button btn_deleteusrdetail, btn_updateusrdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        UserID          = getIntent().getStringExtra("UserID");
        userID          = getIntent().getStringExtra("userID");
        fStore          = FirebaseFirestore.getInstance();

        btn_deleteusrdetail = findViewById(R.id.btn_userdetaildelete);
        btn_deleteusrdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDetail.this);
                builder.setMessage("Apa anda yakin ingin menghapus akun ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(UserDetail.this, AdminUserDataActivity.class);
                        intent.putExtra("UserID", UserID);
                        startActivity(intent);
                        Toast.makeText(UserDetail.this, "Hapus Akun Berhasil", Toast.LENGTH_SHORT).show();
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

        btn_updateusrdetail = findViewById(R.id.btn_userdetailupdate);
        btn_updateusrdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetail.this, UserEdit.class);
                intent.putExtra("UserID", UserID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        cv_bckuserdetail = findViewById(R.id.cv_backuserdetail);
        cv_bckuserdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetail.this, AdminUserDataActivity.class);
                intent.putExtra("UserID", UserID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        namadetailuser         = findViewById(R.id.nama_detailuser);
        emaildetailuser        = findViewById(R.id.email_detailuser);
        passworddetailuser     = findViewById(R.id.password_detailuser);

        DocumentReference df = fStore.collection("Users").document(userID);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                namadetailuser.setText(documentSnapshot.getString("Name"));
                emaildetailuser.setText(documentSnapshot.getString("Email"));
                passworddetailuser.setText(documentSnapshot.getString("Password"));

                uNama = documentSnapshot.getString("Name");
                uEmail   = documentSnapshot.getString("Email");
                uPassword = documentSnapshot.getString("Password");
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserDetail.this, AdminUserDataActivity.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
        super.onBackPressed();
    }
}