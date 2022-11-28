package com.lostdream.bibliotecavirtual;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Book extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Button buttonLeer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book, container, false);

        buttonLeer = root.findViewById(R.id.Reciente_Book_1);
        buttonLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Description_Books description_books = new Description_Books();
                description_books.show(getFragmentManager(), "Description Book");
            }
        });



        return root;
    }
}