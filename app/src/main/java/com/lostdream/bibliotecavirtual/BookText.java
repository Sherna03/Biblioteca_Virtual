package com.lostdream.bibliotecavirtual;

public class BookText {

    private String autor;
    private String titulo;
    private String editorial;
    private String description;
    private String year;
    private String isbn;

    public BookText(){}

    public BookText(String autor, String titulo, String editorial, String description, String year, String isbn){
        this.autor = autor;
        this.titulo = titulo;
        this.editorial = editorial;
        this.description = description;
        this.year = year;
        this.isbn = isbn;
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

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
