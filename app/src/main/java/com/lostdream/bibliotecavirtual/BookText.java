package com.lostdream.bibliotecavirtual;

public class BookText {

    private String titulo;
    private String autor;

    public BookText(){}

    public BookText(String autor, String titulo){
        this.autor = titulo;
        this.titulo = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
