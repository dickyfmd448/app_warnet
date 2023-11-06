package com.example.app_warnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_warnet.admin.AdminMainActivity;
import com.example.app_warnet.user.UserFindActivity;
import com.example.app_warnet.user.UserMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    TextView tv_daftar;
    String UserID;
    EditText email, password;
    Button btn_login, btn_back;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    boolean valid = true;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserID          = getIntent().getStringExtra("UserID");

        fAuth       = FirebaseAuth.getInstance();
        fStore      = FirebaseFirestore.getInstance();

        email       = findViewById(R.id.email);
        password    = findViewById(R.id.password);

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

        btn_login   = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(email);
                checkField(password);

                if (valid){
                    fAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            checkIfAdmin(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Email atau Password Salah", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "Mohon Isi Email dan Password yang Sesuai", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        tv_daftar = findViewById(R.id.daftar);
        tv_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkIfAdmin(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
                String isAdmin = documentSnapshot.getString("isAdmin");
                if (isAdmin.equals("1")){
                    Intent i = new Intent(getApplicationContext(), AdminMainActivity.class);
                    i.putExtra("UserID", uid);
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), UserMainActivity.class);
                    i.putExtra("UserID", uid);
                    startActivity(i);
                }
            }
        });
    }

    public boolean checkField(EditText textField){
        if (textField.getText().toString().isEmpty()){
            textField.setError("Masih kosong!");
            valid = false;
        }else{
            valid = true;
        }
        return valid;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
        super.onBackPressed();
    }
}