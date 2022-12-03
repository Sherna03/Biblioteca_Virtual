package com.lostdream.bibliotecavirtual;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookListViewHolder> {

    private List<BookList> bookLists;

    public BookListAdapter(List<BookList> bookLists) {
        this.bookLists = bookLists;
    }

    @NonNull
    @Override
    public BookListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookListViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_book,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BookListViewHolder holder, int position) {
        holder.setBookData(bookLists.get(position));

    }

    @Override
    public int getItemCount() {
        return bookLists.size();
    }

    static class BookListViewHolder extends RecyclerView.ViewHolder{

        private KenBurnsView kbvBook;
        private TextView textTitle, textRating;

        BookListViewHolder(@NonNull View itemView) {
            super(itemView);
            kbvBook = itemView.findViewById(R.id.kbvBook);
            textTitle = itemView.findViewById(R.id.TexTitletBook);
            textRating = itemView.findViewById(R.id.TextRating);
        }

        void setBookData(BookList bookList){
            Picasso.get().load(bookList.imageUrl).into(kbvBook);
            textTitle.setText(bookList.title);
            textRating.setText(String.valueOf(bookList.starRating));
        }
    }
}
