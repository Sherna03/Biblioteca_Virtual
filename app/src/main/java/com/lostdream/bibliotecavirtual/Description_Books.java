package com.lostdream.bibliotecavirtual;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Description_Books extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView codigo, editorial, year, number, description;
    String codigoText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_description__books, container, false);

        codigoText = "123124219";

        codigo = root.findViewById(R.id.CodigoText);
        codigo.setText(codigoText);



        return root;
    }

}