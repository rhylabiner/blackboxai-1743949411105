package com.library.Controller;

import com.library.DAO.StudentDAO;
import com.library.Model.Student;
import com.library.Util.QRCodeUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StudentController {
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField nameField;
    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private TableView<Student> studentTable;

    @FXML
    public void addStudent() {
        String studentId = studentIdField.getText();
        String name = nameField.getText();
        String qrCodePath = "qr_codes/" + studentId + ".png"; // Path to save QR code

        try {
            // Generate QR code
            QRCodeUtil.generateQRCode(studentId, qrCodePath, 200);
            // Add student to database
            Student student = new Student(studentId, name, qrCodePath);
            StudentDAO.addStudent(student);
            // Refresh student table
            loadStudents();
            clearFields();
        } catch (IOException | WriterException e) {
            showAlert("Error", "Failed to generate QR code: " + e.getMessage());
        } catch (SQLException e) {
            showAlert("Error", "Failed to add student: " + e.getMessage());
        }
    }

    @FXML
    public void loadStudents() {
        try {
            List<Student> students = StudentDAO.getAllStudents();
            studentTable.getItems().clear();
            studentTable.getItems().addAll(students);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load students: " + e.getMessage());
        }
    }

    private void clearFields() {
        studentIdField.clear();
        nameField.clear();
        qrCodeImageView.setImage(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}