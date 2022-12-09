package com.lostdream.bibliotecavirtual;


import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;


public class Leer_Offline extends AppCompatActivity {

    PDFView libroPDFOffline;
    private  String titulo;
    StorageReference storageReference;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_offline);
        storageReference = FirebaseStorage.getInstance().getReference();

        titulo = getIntent().getStringExtra("TITULO");

        libroPDFOffline = findViewById(R.id.viewPDFOffline);
        libroPDFOffline.setMidZoom(1f);

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File descargas = contextWrapper.getExternalFilesDir(DIRECTORY_DOWNLOADS);
        File file = new File(descargas, titulo+".pdf");

        if (!file.exists()){
            Descargar();
        } else {
            Uri uri = Uri.fromFile(file);
            libroPDFOffline.fromUri(uri).enableSwipe(true).pageSnap(true).autoSpacing(true).pageFling(true).fitEachPage(true).load();
        }
    }

    void Descargar(){
        storageReference.child("Libros").child(titulo + ".pdf")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        DescargarFile(Leer_Offline.this, titulo, ".pdf", url);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Leer_Offline.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void DescargarFile(Context context, String fileName, String fileExtension, String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);


        Toast.makeText(this, "Descargando, salga y vuelva a entrar...", Toast.LENGTH_LONG).show();
        request.setDestinationInExternalFilesDir(context, DIRECTORY_DOWNLOADS, fileName + fileExtension);

        downloadManager.enqueue(request);
    }
}