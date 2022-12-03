package com.lostdream.bibliotecavirtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.errorprone.annotations.Var;

public class Inicio extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Book book = new Book();
    Search search = new Search();
    Profile profile = new Profile();
    Leer_mas_tarde leerMasTarde = new Leer_mas_tarde();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        bottomNavigationView = findViewById(R.id.BottonNav);

        getSupportFragmentManager().beginTransaction().add(R.id.container, book).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, profile).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, search).commit();

        //Esconder
        getSupportFragmentManager().beginTransaction().hide(profile).commit();
        getSupportFragmentManager().beginTransaction().hide(search).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Book:
                        getSupportFragmentManager().beginTransaction().show(book).commit();
                        getSupportFragmentManager().beginTransaction().hide(search).commit();
                        getSupportFragmentManager().beginTransaction().hide(profile).commit();
                        return true;
                    case R.id.Search:
                        getSupportFragmentManager().beginTransaction().show(search).commit();
                        getSupportFragmentManager().beginTransaction().hide(book).commit();
                        getSupportFragmentManager().beginTransaction().hide(profile).commit();
                        return true;
                    case R.id.Profile:
                        getSupportFragmentManager().beginTransaction().show(profile).commit();
                        getSupportFragmentManager().beginTransaction().hide(book).commit();
                        getSupportFragmentManager().beginTransaction().hide(search).commit();
                        return true;
                }
                return false;
            }
        });
    }
}