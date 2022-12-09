package com.lostdream.bibliotecavirtual;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class Description_Books extends DialogFragment {

    DatabaseReference mDatabase;
    StorageReference storageReference;


    public String titulo, autor, editorial, year, isbn, description;
    private static String tituloB;
    int REQUEST_CODE = 200;

    TextView Titulo, AutorText, EditorialText, YearText, ISBNText, DescriptionText;
    Button leer, descargar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Libros").child(tituloB).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    titulo = Objects.requireNonNull(snapshot.child("titulo").getValue()).toString();
                    autor = Objects.requireNonNull(snapshot.child("autor").getValue()).toString();
                    editorial = Objects.requireNonNull(snapshot.child("editorial").getValue()).toString();
                    year = Objects.requireNonNull(snapshot.child("year").getValue()).toString();
                    isbn = Objects.requireNonNull(snapshot.child("isbn").getValue()).toString();
                    description = Objects.requireNonNull(snapshot.child("description").getValue()).toString();
                    MostrarDatosLibro(titulo, autor, editorial, year, isbn, description);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_description_books, container, false);

        Titulo = root.findViewById(R.id.Titulo);
        AutorText = root.findViewById(R.id.AutorText);
        EditorialText = root.findViewById(R.id.EditorialText);
        YearText = root.findViewById(R.id.YearText);
        ISBNText = root.findViewById(R.id.ISBNText);
        DescriptionText = root.findViewById(R.id.DescriptionText);
        leer = root.findViewById(R.id.Leer);
        descargar = root.findViewById(R.id.Leer_Offline);

        leer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Leer.class);
                intent.putExtra("TITULO", tituloB);
                startActivity(intent);
            }
        });

        descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Leer_Offline.class);
                intent.putExtra("TITULO", tituloB);
                startActivity(intent);
            }
        });

        return root;
    }

     static void Rdata(Bundle bundle){
        tituloB = (String) bundle.getSerializable("titulo");
    }

    void MostrarDatosLibro(String titulo, String autor, String editorial, String year, String isbn, String description){
        Titulo.setText(titulo);
        AutorText.setText(autor);
        EditorialText.setText(editorial);
        YearText.setText(year);
        ISBNText.setText(isbn);
        DescriptionText.setText(description);
    }
}