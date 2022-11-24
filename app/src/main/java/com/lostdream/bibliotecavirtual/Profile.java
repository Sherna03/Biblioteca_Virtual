package com.lostdream.bibliotecavirtual;


import static android.content.Context.MODE_PRIVATE;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Profile extends Fragment {

    private SharedPreferences preferences;
    ImageView imgProfile;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = requireActivity().getSharedPreferences("Preferences", MODE_PRIVATE);
        String email_usuario = preferences.getString("email_usuario", null);
        String password_usuario = preferences.getString("password_usuario", null);


    }


    private Button button;

    private void cerrarSesion(){
        FirebaseAuth.getInstance().signOut();
        preferences.edit().clear().apply();
        irLogin();
    }

    private void irLogin(){
        Intent login = new Intent(getContext(), MainActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
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

        button = root.findViewById(R.id.cerrarSesion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });

        return root;
    }


}
