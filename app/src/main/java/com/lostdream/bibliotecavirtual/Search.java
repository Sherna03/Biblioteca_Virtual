package com.lostdream.bibliotecavirtual;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


public class Search extends Fragment implements ClickedListener {

    DatabaseReference reference;
    ArrayList<BookText> lists;
    RecyclerView recyclerView;
    SearchView searchView;
    BookTextAdapter bookTextAdapter;

    LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference().child("Libros");
        lists = new ArrayList<>();
        bookTextAdapter = new BookTextAdapter(lists, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = root.findViewById(R.id.SearchView);
        searchView = root.findViewById(R.id.Search);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(bookTextAdapter);



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        BookText bookT = snapshot1.getValue(BookText.class);
                        lists.add(bookT);
                    }
                    bookTextAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return false;
            }
        });



        return root;
    }

    private void buscar(String s) {
        ArrayList<BookText> List = new ArrayList<>();

        for (BookText obj: lists){
            if (obj.getTitulo().toLowerCase().contains(s.toLowerCase()) | obj.getAutor().toLowerCase().contains(s.toLowerCase())){
                List.add(obj);
            }
            BookTextAdapter adapter = new BookTextAdapter(List, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onPictureClicked(int position, String titulo) {
        //Iniciar Fragment
        Description_Books description_books = new Description_Books();
        assert getFragmentManager() != null;
        description_books.show(getFragmentManager(), "Description Book");

        //Enviar el titulo al fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("titulo", titulo );
        Description_Books.Rdata(bundle);
    }
}