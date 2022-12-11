package com.lostdream.bibliotecavirtual;

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
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
        bookList2First.imageUrl = "https://kbimages1-a.akamaihd.net/72de175e-66c0-4509-8f2c-6d1f42710cf7/1200/1200/False/jaque-al-psicoanalista.jpg";
        bookList2First.title = "Jaque Al Psicoanalista";
        bookList2First.starRating = 5.0f;
        bookLists2.add(bookList2First);

        BookList bookList2Second = new BookList();
        bookList2Second.imageUrl = "https://locatelcolombia.vtexassets.com/arquivos/ids/236182/9789585993679_1_LIBRO-EL-PSICOANALISTA.jpg?v=637535128630130000";
        bookList2Second.title = "El Psicoanalista";
        bookList2Second.starRating = 4.9f;
        bookLists2.add(bookList2Second);

        BookList bookList2Third = new BookList();
        bookList2Third.imageUrl = "https://www.perlego.com/books/RM_Books/libranda_xpmioj/9788423343362.jpg";
        bookList2Third.title = "1984";
        bookList2Third.starRating = 4.8f;
        bookLists2.add(bookList2Third);

        BookList bookList2Fourth = new BookList();
        bookList2Fourth.imageUrl = "https://pbs.twimg.com/media/EmVsOB6XUAAszHV?format=jpg&name=large";
        bookList2Fourth.title = "Rebelión En La Granja";
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