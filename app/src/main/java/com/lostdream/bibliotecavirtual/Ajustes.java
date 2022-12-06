package com.lostdream.bibliotecavirtual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Ajustes extends AppCompatActivity {

    Button cerrarSesionA;
    TextView correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        correo = findViewById(R.id.correoProfile);
        cerrarSesionA = findViewById(R.id.Cerrar);
        cerrarSesionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });



        if (user!=null){
            String gmail = user.getEmail();

            correo.setText(gmail);
        }
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