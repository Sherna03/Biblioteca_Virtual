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
    private ClickedListener clickedListener;

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

    public class BookTextViewHolder extends RecyclerView.ViewHolder {

        TextView Titulo_Search, Autor_Search;

        public BookTextViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo_Search = itemView.findViewById(R.id.Titulo_Search);
            Autor_Search = itemView.findViewById(R.id.Autor_Search);
        }
    }
}
