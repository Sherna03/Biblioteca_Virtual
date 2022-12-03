package com.lostdream.bibliotecavirtual;


import static android.content.Context.MODE_PRIVATE;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Profile extends Fragment {

    ImageView imgProfile;

    BottomNavigationView bottomNavigationView;

    Leer_mas_tarde leerMasTarde = new Leer_mas_tarde();
    Favoritos favoritos = new Favoritos();
    Comentarios comentarios = new Comentarios();





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private ImageButton button;

    private void irAjustes(){
        Intent intent = new Intent(getContext(), Ajustes.class);
        startActivity(intent);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        imgProfile = root.findViewById(R.id.imgProfile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            //String name = user.getDisplayName();
            //String gmail = user.getEmail();

            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.login).into(imgProfile);
        } else {
            getContext();
        }

        button = root.findViewById(R.id.Ajustes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irAjustes();
            }
        });


        bottomNavigationView = root.findViewById(R.id.bottomNavigationProfile);

        //getFragmentManager().beginTransaction().replace(R.id.containerProfile, leerMasTarde).commit();
        getFragmentManager().beginTransaction().replace(R.id.containerProfile, leerMasTarde).addToBackStack(null).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.LeerMasTarde:
                        assert getFragmentManager() != null;
                        getFragmentManager().beginTransaction().replace(R.id.containerProfile, leerMasTarde).commit();
                        return true;
                    case R.id.Favoritos:
                        assert getFragmentManager() != null;
                        getFragmentManager().beginTransaction().replace(R.id.containerProfile, favoritos).commit();
                        return true;
                    case R.id.Comentarios:
                        assert getFragmentManager() != null;
                        getFragmentManager().beginTransaction().replace(R.id.containerProfile, comentarios).commit();
                        return true;
                    }
                return false;
            }});

        return root;
    }
}
