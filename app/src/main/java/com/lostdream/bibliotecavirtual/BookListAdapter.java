package com.lostdream.bibliotecavirtual;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.material.transition.Hold;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookListViewHolder> {

    private List<BookList> bookLists;
    private ViewPager2 viewPager2;
    private ClickedListener clickedListener;

    public BookListAdapter(List<BookList> bookLists, ViewPager2 viewPager2, ClickedListener clickedListener) {
        this.bookLists = bookLists;
        this.viewPager2 = viewPager2;
        this.clickedListener = clickedListener;
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

        /*

        if (position == bookLists.size() - 2){
            viewPager2.post(runnable);
        }

         */
    }

    @Override
    public int getItemCount() {
        return bookLists.size();
    }

    class BookListViewHolder extends RecyclerView.ViewHolder{

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
            kbvBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickedListener.onPictureClicked(getAdapterPosition(), bookList.title);
                }
            });

        }
    }
    /*
    //Sin uso por consumo excesivo de ram

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            bookLists.addAll(bookLists);
            //notifyDataSetChanged();
        }
    };

     */
}
