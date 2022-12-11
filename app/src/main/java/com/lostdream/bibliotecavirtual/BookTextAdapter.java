package com.lostdream.bibliotecavirtual;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookTextAdapter extends RecyclerView.Adapter<BookTextAdapter.BookTextViewHolder> {

    List<BookText> BookTextList;
    private final ClickedListener clickedListener;

    //Crear diferentes Card-View dependiente de los titulos y autores existente en la base de datos

    public BookTextAdapter(List<BookText> bookTextList,ClickedListener clickedListener) {
        BookTextList = bookTextList;
        this.clickedListener = clickedListener;
    }

    @NonNull
    @Override
    public BookTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        BookTextViewHolder holder = new BookTextViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookTextViewHolder holder, int position) {
        BookText bookText = BookTextList.get(position);
        //Insertar información
        holder.Titulo_Search.setText(bookText.getTitulo());
        holder.Autor_Search.setText(bookText.getAutor());
        holder.Titulo_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedListener.onPictureClicked(holder.getAdapterPosition(), bookText.getTitulo());
            }
        });
    }


    @Override
    public int getItemCount() {
        return BookTextList.size();
    }

    public static class BookTextViewHolder extends RecyclerView.ViewHolder {

        TextView Titulo_Search, Autor_Search;

        public BookTextViewHolder(@NonNull View itemView) {
            super(itemView);
            //Llamar objetos de la clase Card-Items
            Titulo_Search = itemView.findViewById(R.id.Titulo_Search);
            Autor_Search = itemView.findViewById(R.id.Autor_Search);
        }
    }
}
