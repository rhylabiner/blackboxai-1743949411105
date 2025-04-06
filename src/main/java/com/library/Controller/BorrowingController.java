package com.library.Controller;

import com.library.DAO.BorrowingDAO;
import com.library.DAO.BookDAO;
import com.library.DAO.StudentDAO;
import com.library.Model.Borrowing;
import com.library.Model.Book;
import com.library.Model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class BorrowingController {
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField isbnField;
    @FXML
    private TableView<Borrowing> borrowingTable;

    @FXML
    public void borrowBook() {
        String studentId = studentIdField.getText();
        String isbn = isbnField.getText();

        try {
            // Validate student and book existence
            Student student = StudentDAO.getAllStudents().stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
            Book book = BookDAO.getAllBooks().stream()
                .filter(b -> b.getIsbn().equals(isbn) && b.isAvailable())
                .findFirst()
                .orElse(null);

            if (student == null) {
                showAlert("Error", "Student not found.");
                return;
            }
            if (book == null) {
                showAlert("Error", "Book not available.");
                return;
            }

            // Create borrowing record
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime dueDate = now.plusDays(7); // 7 days from now
            Borrowing borrowing = new Borrowing(0, studentId, isbn, now, dueDate, null);
            BorrowingDAO.borrowBook(borrowing);
            loadBorrowings();
            clearFields();
        } catch (SQLException e) {
            showAlert("Error", "Failed to borrow book: " + e.getMessage());
        }
    }

    @FXML
    public void loadBorrowings() {
        try {
            List<Borrowing> borrowings = BorrowingDAO.getBorrowingsByStudent(studentIdField.getText());
            borrowingTable.getItems().clear();
            borrowingTable.getItems().addAll(borrowings);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load borrowings: " + e.getMessage());
        }
    }

    private void clearFields() {
        studentIdField.clear();
        isbnField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}