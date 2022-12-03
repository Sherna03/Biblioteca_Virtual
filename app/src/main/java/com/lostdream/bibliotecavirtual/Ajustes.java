package com.lostdream.bibliotecavirtual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Ajustes extends AppCompatActivity {

    Button cerrarSesionA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        cerrarSesionA = findViewById(R.id.Cerrar);
        cerrarSesionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });


    }

    private void cerrarSesion(){
        FirebaseAuth.getInstance().signOut();
        irLogin();
    }

    private void irLogin(){
        Intent login = new Intent(Ajustes.this, MainActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
    }
}