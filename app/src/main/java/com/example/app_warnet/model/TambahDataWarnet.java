package com.example.app_warnet.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_warnet.R;
import com.example.app_warnet.admin.AdminDataWarnetActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TambahDataWarnet extends AppCompatActivity {
    String UserID, WarnetID, imageUrl, nameWarnet, alamatWarnet, hargaWarnet, fasilitasWarnet, mapsWarnet;
    private StorageReference storageReference;
    private ImageView inputImage;
    private static final int GalleryPick = 1;
    private Uri imageUri;
    Button btn_backdatanet, btn_tambahdatanet;
    EditText name_warnet, alamat_warnet, harga_warnet, fasilitas_warnet, maps_warnet;
    private String productRandomKey, saveCurrentDate, saveCurrentTime;
    FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    boolean valid = true;
    boolean match = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_warnet);

        UserID              = getIntent().getStringExtra("UserID");
        WarnetID            = getIntent().getStringExtra("WarnetID");
        storageReference    = FirebaseStorage.getInstance().getReference();

        fAuth               = FirebaseAuth.getInstance();
        fStore              = FirebaseFirestore.getInstance();

        name_warnet         = findViewById(R.id.tambahnamawarnet);
        alamat_warnet       = findViewById(R.id.tambahalamatwarnet);
        harga_warnet        = findViewById(R.id.tambahhargawarnet);
        fasilitas_warnet    = findViewById(R.id.tambahfasilitaswarnet);
        maps_warnet         = findViewById(R.id.tambahmapswarnet);
        inputImage      = findViewById(R.id.photoWarnet);
        inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalery();
            }
        });

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        WarnetID = saveCurrentDate + saveCurrentTime;

        btn_tambahdatanet = findViewById(R.id.btn_tambahdatawarnet);

        btn_tambahdatanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProduk();
            }
        });

        btn_backdatanet = findViewById(R.id.btn_backtambahdatawarnet);
        btn_backdatanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TambahDataWarnet.this, AdminDataWarnetActivity.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });
    }
    private void uploadProduk(){
        nameWarnet           = name_warnet.getText().toString();
        alamatWarnet         = alamat_warnet.getText().toString();
        hargaWarnet          = harga_warnet.getText().toString();
        fasilitasWarnet      = fasilitas_warnet.getText().toString();
        mapsWarnet           = maps_warnet.getText().toString();
        if (imageUri == null){
            Toast.makeText(TambahDataWarnet.this, "Foto Warnet Belum DI Upload", Toast.LENGTH_SHORT).show();
        }else{
            storePhoto();
        }
    }
    private void storeToDatabase(){
//        Toast.makeText(TambahDataWarnet.this, "Produk Berhasil Diunggah", Toast.LENGTH_SHORT).show();
        DocumentReference df = fStore.collection("Warnet").document(WarnetID);
        Map<String, Object> warnet = new HashMap<>();
        warnet.put("NetName", nameWarnet);
        warnet.put("Alamat", alamatWarnet);
        warnet.put("Harga", hargaWarnet);
        warnet.put("Fasilitas", fasilitasWarnet);
        warnet.put("Maps", mapsWarnet);
        warnet.put("ImageURL", imageUrl);
        warnet.put("WarnetID", WarnetID);
        df.set(warnet);
        Intent i = new Intent(getApplicationContext(), TambahDataWarnet.class);
        i.putExtra("UserID", UserID);
        i.putExtra("WarnetID", WarnetID);
        startActivity(i);
    }
    private void storePhoto(){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        WarnetID = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath = storageReference.child(imageUri.getLastPathSegment() + WarnetID + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(TambahDataWarnet.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(TambahDataWarnet.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        imageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            imageUrl = task.getResult().toString();

                            Toast.makeText(TambahDataWarnet.this, "Warnet Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();

                            saveData();
                        }
                    }
                });
            }
        });
    }
    private void openGalery(){
        Intent galeryIntent =new Intent();
        galeryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galeryIntent.setType("image/*");
        startActivityForResult(galeryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick  &&  resultCode == RESULT_OK  &&  data!=null)
        {
            imageUri = data.getData();
            inputImage.setImageURI(imageUri);
        }
    }
    private void saveData(){
        Map<String, Object> warnet = new HashMap<>();
        warnet.put("WarnetID", WarnetID);
        warnet.put("NetName", nameWarnet);
        warnet.put("Alamat", alamatWarnet);
        warnet.put("Harga", hargaWarnet);
        warnet.put("Fasilitas", fasilitasWarnet);
        warnet.put("Maps", mapsWarnet);
        warnet.put("ImageURL", imageUrl);

        DocumentReference df = fStore.collection("Warnet").document(WarnetID);
        df.set(warnet);
        Intent intent = new Intent(TambahDataWarnet.this, AdminDataWarnetActivity.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
//        db.collection("Warnet")
//                .add(warnet)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(TambahDataWarnet.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(TambahDataWarnet.this, AdminDataWarnetActivity.class);
//                        intent.putExtra("UserID", UserID);
//                        startActivity(intent);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(TambahDataWarnet.this, "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TambahDataWarnet.this, AdminDataWarnetActivity.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
        super.onBackPressed();
    }
}