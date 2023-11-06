package com.example.app_warnet.model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_warnet.R;
import com.example.app_warnet.ViewData;
import com.example.app_warnet.admin.AdminDataWarnetActivity;
import com.example.app_warnet.admin.AdminUserDataActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserEdit extends AppCompatActivity {

    String UserID, userID;
    EditText name, email, password, confirm_password;
    Button btn_bataldatausr, btn_updatedatausr;
    String uNama, uEmail, uPassword;
    FirebaseFirestore fStore;

    boolean passwordVisible, confirm_passwordVisible;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        UserID              = getIntent().getStringExtra("UserID");
        userID              = getIntent().getStringExtra("userID");
        fStore              = FirebaseFirestore.getInstance();

        name                = findViewById(R.id.ubahnamauser);
        email               = findViewById(R.id.ubahemailuser);
        password            = findViewById(R.id.ubahpassworduser);

        DocumentReference df = fStore.collection("Users").document(userID);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText(documentSnapshot.getString("Name"));
                email.setText(documentSnapshot.getString("Email"));
                password.setText(documentSnapshot.getString("Password"));

                uNama      = documentSnapshot.getString("Name");
                uEmail   = documentSnapshot.getString("Email");
                uPassword     = documentSnapshot.getString("Password");
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=password.getSelectionEnd();
                        if (passwordVisible){
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,R.drawable.umpetpassword,0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else{
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,R.drawable.intippassword,0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

//        confirm_password.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                final int Right=2;
//                if(event.getAction()==MotionEvent.ACTION_UP) {
//                    if (event.getRawX()>=confirm_password.getRight()-confirm_password.getCompoundDrawables()[Right].getBounds().width()){
//                        int selection=confirm_password.getSelectionEnd();
//                        if (confirm_passwordVisible){
//                            confirm_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,R.drawable.umpetpassword,0);
//                            confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                            confirm_passwordVisible=false;
//                        }else{
//                            confirm_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,R.drawable.intippassword,0);
//                            confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                            confirm_passwordVisible=true;
//                        }
//                        confirm_password.setSelection(selection);
//                        return true;
//                    }
//                }
//
//                return false;
//            }
//        });

        btn_updatedatausr = findViewById(R.id.btn_updatedatauser);
        btn_updatedatausr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserEdit.this);
                builder.setMessage("Ingin Menyimpan Perubahan ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        fStore.collection("Users").document(userID).update("Name", name.getText().toString());
                        fStore.collection("Users").document(userID).update("Email", email.getText().toString());
                        fStore.collection("Users").document(userID).update("Password", password.getText().toString());
                        Intent intent = new Intent(UserEdit.this, AdminUserDataActivity.class);
                        intent.putExtra("UserID", UserID);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                        Toast.makeText(UserEdit.this, "Perubahan Berhasil Disimpan", Toast.LENGTH_SHORT).show();
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

        btn_bataldatausr = findViewById(R.id.btn_batalubahdatauser);
        btn_bataldatausr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserEdit.this);
                builder.setMessage("Ingin Membatalkan Perubahan ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(UserEdit.this, UserDetail.class);
                        intent.putExtra("UserID", UserID);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                        Toast.makeText(UserEdit.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
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