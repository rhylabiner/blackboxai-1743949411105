package com.library.Controller;

import com.library.DAO.AttendanceDAO;
import com.library.DAO.StudentDAO;
import com.library.Model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class AttendanceController {
    @FXML
    private Label statusLabel;
    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private TableView<Student> activeStudentsTable;
    @FXML
    private TableView<Attendance> attendanceTable;

    private String currentStudentId;

    @FXML
    public void scanQRCode() {
        // In a real implementation, this would interface with a QR scanner
        // For now, we'll simulate scanning by showing a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open QR Code Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        
        if (selectedFile != null) {
            try {
                // In a real app, we would decode the QR code to get the student ID
                // For simulation, we'll just use the filename
                String fileName = selectedFile.getName();
                currentStudentId = fileName.substring(0, fileName.lastIndexOf('.'));
                
                // Display the QR code image
                qrCodeImageView.setImage(new Image(selectedFile.toURI().toString()));
                statusLabel.setText("Scanned Student ID: " + currentStudentId);
                
                // Check if student is already checked in
                if (isStudentCheckedIn(currentStudentId)) {
                    checkOutStudent();
                } else {
                    checkInStudent();
                }
                
                refreshActiveStudents();
                refreshAttendanceRecords();
            } catch (Exception e) {
                statusLabel.setText("Error scanning QR code: " + e.getMessage());
            }
        }
    }

    private boolean isStudentCheckedIn(String studentId) throws SQLException {
        List<Attendance> activeSessions = AttendanceDAO.getCurrentActiveSessions();
        return activeSessions.stream()
            .anyMatch(a -> a.getStudentId().equals(studentId));
    }

    private void checkInStudent() throws SQLException {
        AttendanceDAO.checkInStudent(currentStudentId);
        statusLabel.setText("Checked in: " + currentStudentId);
    }

    private void checkOutStudent() throws SQLException {
        // Get the active attendance record for this student
        List<Attendance> activeSessions = AttendanceDAO.getCurrentActiveSessions();
        activeSessions.stream()
            .filter(a -> a.getStudentId().equals(currentStudentId))
            .findFirst()
            .ifPresent(a -> {
                try {
                    AttendanceDAO.checkOutStudent(a.getId());
                    statusLabel.setText("Checked out: " + currentStudentId);
                } catch (SQLException e) {
                    statusLabel.setText("Error checking out: " + e.getMessage());
                }
            });
    }

    private void refreshActiveStudents() throws SQLException {
        List<Student> activeStudents = StudentDAO.getAllStudents().stream()
            .filter(s -> {
                try {
                    return isStudentCheckedIn(s.getStudentId());
                } catch (SQLException e) {
                    return false;
                }
            })
            .toList();
        activeStudentsTable.getItems().clear();
        activeStudentsTable.getItems().addAll(activeStudents);
    }

    private void refreshAttendanceRecords() throws SQLException {
        if (currentStudentId != null) {
            List<Attendance> records = AttendanceDAO.getAttendanceByStudent(currentStudentId);
            attendanceTable.getItems().clear();
            attendanceTable.getItems().addAll(records);
        }
    }
}