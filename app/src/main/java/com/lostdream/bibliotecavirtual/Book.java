package com.lostdream.bibliotecavirtual;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Book extends Fragment implements ClickedListener{


    ImageView imgProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book, container, false);
        /*
        buttonLeer = root.findViewById(R.id.Reciente_Book_1);
        buttonLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Description_Books description_books = new Description_Books();
                description_books.show(getFragmentManager(), "Description Book");
            }
        });
        */

        imgProfile = root.findViewById(R.id.imgProfile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.login).into(imgProfile);
        } else {
            getContext();
        }

        //ViewsSiders
        ViewPager2 NuevosLibrosView = root.findViewById(R.id.NuevosLibrosView);
        ViewPager2 DestacadosLibrosView = root.findViewById(R.id.DestacadosLibrosView);

        //Nuevos Libros
        List<BookList> bookLists = new ArrayList<>();

        BookList bookListFirst = new BookList();
        bookListFirst.imageUrl = "https://www.republica.com/wp-content/uploads/2010/03/el-tunel-sabato.jpg";
        bookListFirst.title = "El Túnel";
        bookListFirst.starRating = 4.0f;
        bookLists.add(bookListFirst);

        BookList bookListSecond = new BookList();
        bookListSecond.imageUrl = "https://nidodelibros.com/wp-content/uploads/2021/04/9789877514308.jpeg";
        bookListSecond.title = "El Principito";
        bookListSecond.starRating = 4.3f;
        bookLists.add(bookListSecond);

        BookList bookListThird = new BookList();
        bookListThird.imageUrl = "https://imagessl7.casadellibro.com/a/l/t7/77/9788401027277.jpg";
        bookListThird.title = "Diario De Ana Frank";
        bookListThird.starRating = 4.2f;
        bookLists.add(bookListThird);

        BookList bookListFourth = new BookList();
        bookListFourth.imageUrl = "https://cdn.culturagenial.com/es/imagenes/la-sombra-del-viento-portada-cke.jpg";
        bookListFourth.title = "La Sombra Del Viento";
        bookListFourth.starRating = 3.9f;
        bookLists.add(bookListFourth);

        NuevosLibrosView.setAdapter(new BookListAdapter(bookLists, NuevosLibrosView, this));

        NuevosLibrosView.setClipToPadding(false);
        NuevosLibrosView.setClipChildren(false);
        NuevosLibrosView.setOffscreenPageLimit(3);
        NuevosLibrosView.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        NuevosLibrosView.setPageTransformer(compositePageTransformer);



        //Libros Destacados

        List<BookList> bookLists2 = new ArrayList<>();

        BookList bookList2First = new BookList();
        bookList2First.imageUrl = "https://www.marcialpons.es/media/img/portadas/9788415436676.jpg";
        bookList2First.title = "La Guerra De Stalin";
        bookList2First.starRating = 5.0f;
        bookLists2.add(bookList2First);

        BookList bookList2Second = new BookList();
        bookList2Second.imageUrl = "https://i.pinimg.com/originals/29/80/f0/2980f0e3584a8b192c135740c6f756aa.jpg";
        bookList2Second.title = "La Guerra De Los Treinta Años";
        bookList2Second.starRating = 4.9f;
        bookLists2.add(bookList2Second);

        BookList bookList2Third = new BookList();
        bookList2Third.imageUrl = "https://katakrak.net/sites/default/files/styles/extra_large/public/portadas/9788494289057.jpeg?itok=-tv8Q9Sg";
        bookList2Third.title = "La Segunda Guerra Mundial";
        bookList2Third.starRating = 4.8f;
        bookLists2.add(bookList2Third);

        BookList bookList2Fourth = new BookList();
        bookList2Fourth.imageUrl = "https://cdn1.despertaferro-ediciones.com/wp-content/uploads/2017/02/portada-titanes-2ED.jpg";
        bookList2Fourth.title = "Choque De Titanes";
        bookList2Fourth.starRating = 4.6f;
        bookLists2.add(bookList2Fourth);

        DestacadosLibrosView.setAdapter(new BookListAdapter(bookLists2, DestacadosLibrosView, this));

        DestacadosLibrosView.setClipToPadding(false);
        DestacadosLibrosView.setClipChildren(false);
        DestacadosLibrosView.setOffscreenPageLimit(3);
        DestacadosLibrosView.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        DestacadosLibrosView.setPageTransformer(compositePageTransformer);

        return root;
    }

    @Override
    public void onPictureClicked(int position, String tituloB) {

        //Toast.makeText(getContext(), tituloB, Toast.LENGTH_SHORT).show();

        //Iniciar Fragment
        Description_Books description_books = new Description_Books();
        assert getFragmentManager() != null;
        description_books.show(getFragmentManager(), "Description Book");

        //Enviar el titulo al fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("titulo", tituloB);
        Description_Books.Rdata(bundle);
    }

}