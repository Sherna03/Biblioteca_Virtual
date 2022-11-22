package com.lostdream.bibliotecavirtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Inicio extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Book book = new Book();
    Search search = new Search();
    Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        bottomNavigationView = findViewById(R.id.BottonNav);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, book).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, book).commit();
                        return true;
                    case R.id.Search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, search).commit();
                        return true;
                    case R.id.Profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profile).commit();
                        return true;
                }
                return false;
            }
        });
    }
}