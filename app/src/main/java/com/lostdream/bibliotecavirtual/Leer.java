package com.lostdream.bibliotecavirtual;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Leer extends AppCompatActivity {

    public final static long ONE_MEGABYTE = 1024 * 1024 * 10;

    PDFView libroPDF;
    private  String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer);

        titulo = getIntent().getStringExtra("TITULO");

        libroPDF = findViewById(R.id.viewPDF);
        libroPDF.setMidZoom(1f);

        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        StorageReference mStorageRef = mStorage.getReference().child("Libros");

        mStorageRef.child(titulo+".pdf").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                libroPDF.fromBytes(bytes).enableSwipe(true).pageSnap(true).autoSpacing(true).pageFling(true).fitEachPage(true).load();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Leer.this, "Libro aun no disponible", Toast.LENGTH_SHORT).show();
            }
        });

    }
}