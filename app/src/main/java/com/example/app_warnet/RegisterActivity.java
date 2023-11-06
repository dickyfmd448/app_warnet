package com.example.app_warnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_warnet.admin.AdminMainActivity;
import com.example.app_warnet.user.UserMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    TextView tv_login;
    String UserID;
    EditText name, email, password, confirm_password;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    boolean valid = true;
    boolean match = true;
    boolean passwordVisible;
    boolean confirm_passwordVisible;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UserID              = getIntent().getStringExtra("UserID");

        fAuth               = FirebaseAuth.getInstance();
        fStore              = FirebaseFirestore.getInstance();

        name                = findViewById(R.id.name);
        email               = findViewById(R.id.email);
        password            = findViewById(R.id.password);
        confirm_password    = findViewById(R.id.confirm_password);

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

        confirm_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP) {
                    if (event.getRawX()>=confirm_password.getRight()-confirm_password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=confirm_password.getSelectionEnd();
                        if (confirm_passwordVisible){
                            confirm_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,R.drawable.umpetpassword,0);
                            confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            confirm_passwordVisible=false;
                        }else{
                            confirm_password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,R.drawable.intippassword,0);
                            confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            confirm_passwordVisible=true;
                        }
                        confirm_password.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(name);
                checkField(email);
                checkField(password);
                checkField(confirm_password);
                confirmPassword(password, confirm_password);

                if (valid && match){
                    fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser fUser= fAuth.getCurrentUser();
                            DocumentReference documentReference =fStore.collection("Users").document(fUser.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("UserID", fUser.getUid());
                            userInfo.put("Name", name.getText().toString());
                            userInfo.put("Email", email.getText().toString());
                            userInfo.put("Password", password.getText().toString());
                            userInfo.put("isAdmin", "0");
                            documentReference.set(userInfo);
                            Toast.makeText(RegisterActivity.this, "Akun Berhasil Dibuat", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("UserID", fUser.getUid());
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Akun Gagal Dibuat", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "Email Sudah digunakan", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "Atau kesalahan saat input data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        tv_login = findViewById(R.id.login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
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
    public boolean confirmPassword(EditText password, EditText confirm_password){
        String pass1, pass2;
        pass1 = password.getText().toString();
        pass2 = confirm_password.getText().toString();

        if (pass1.equals(pass2)){
            match = true;
        }else{
            Toast.makeText(RegisterActivity.this, "Password Doesn't Mactch", Toast.LENGTH_SHORT).show();
            match = false;
        }
        return match;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
        super.onBackPressed();
    }
}