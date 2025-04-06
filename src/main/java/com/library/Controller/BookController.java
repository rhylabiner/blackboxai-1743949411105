package com.library.Controller;

import com.library.DAO.BookDAO;
import com.library.Model.Book;
import com.library.Util.QRCodeUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BookController {
    @FXML
    private TextField isbnField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private ImageView barcodeImageView;
    @FXML
    private TableView<Book> bookTable;

    @FXML
    public void addBook() {
        String isbn = isbnField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String barcodePath = "barcodes/" + isbn + ".png";

        try {
            // Generate barcode/QR code
            QRCodeUtil.generateQRCode(isbn, barcodePath, 200);
            // Add book to database
            Book book = new Book(isbn, title, author, true, barcodePath);
            BookDAO.addBook(book);
            // Refresh book table
            loadBooks();
            clearFields();
        } catch (IOException | WriterException e) {
            showAlert("Error", "Failed to generate barcode: " + e.getMessage());
        } catch (SQLException e) {
            showAlert("Error", "Failed to add book: " + e.getMessage());
        }
    }

    @FXML
    public void loadBooks() {
        try {
            List<Book> books = BookDAO.getAllBooks();
            bookTable.getItems().clear();
            bookTable.getItems().addAll(books);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load books: " + e.getMessage());
        }
    }

    private void clearFields() {
        isbnField.clear();
        titleField.clear();
        authorField.clear();
        barcodeImageView.setImage(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}