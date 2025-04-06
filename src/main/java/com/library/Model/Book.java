package com.library.Model;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean available;
    private String qrCodePath;

    public Book(String isbn, String title, String author, boolean available, String qrCodePath) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
        this.qrCodePath = qrCodePath;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }
}