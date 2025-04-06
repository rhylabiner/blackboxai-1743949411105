package com.library.Controller;

import com.library.DAO.AttendanceDAO;
import com.library.DAO.BorrowingDAO;
import com.library.Model.Attendance;
import com.library.Model.Borrowing;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class ReportsController {
    @FXML
    private TableView<Attendance> attendanceTable;
    @FXML
    private TableView<Borrowing> overdueTable;

    @FXML
    public void initialize() {
        setupAttendanceTable();
        setupOverdueTable();
        loadReports();
    }

    private void setupAttendanceTable() {
        TableColumn<Attendance, String> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        
        TableColumn<Attendance, String> checkInCol = new TableColumn<>("Check-In");
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        
        TableColumn<Attendance, String> checkOutCol = new TableColumn<>("Check-Out");
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        
        attendanceTable.getColumns().setAll(studentIdCol, checkInCol, checkOutCol);
    }

    private void setupOverdueTable() {
        TableColumn<Borrowing, String> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        
        TableColumn<Borrowing, String> isbnCol = new TableColumn<>("Book ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        
        TableColumn<Borrowing, String> dueDateCol = new TableColumn<>("Due Date");
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        
        overdueTable.getColumns().setAll(studentIdCol, isbnCol, dueDateCol);
    }

    private void loadReports() {
        try {
            // Load attendance data
            List<Attendance> attendanceRecords = AttendanceDAO.getAllAttendance();
            attendanceTable.getItems().setAll(attendanceRecords);
            
            // Load overdue books
            List<Borrowing> overdueBooks = BorrowingDAO.getOverdueBorrowings();
            overdueTable.getItems().setAll(overdueBooks);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load reports: " + e.getMessage());
        }
    }

    @FXML
    private void exportAttendance() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Attendance Report");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(attendanceTable.getScene().getWindow());
        if (file != null) {
            try {
                ExportUtil.exportToCSV(attendanceTable.getItems(), file.getPath());
                showAlert("Success", "Attendance data exported successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to export: " + e.getMessage());
            }
        }
    }

    @FXML
    private void exportOverdue() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Overdue Books Report");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(overdueTable.getScene().getWindow());
        if (file != null) {
            try {
                ExportUtil.exportToCSV(overdueTable.getItems(), file.getPath());
                showAlert("Success", "Overdue books data exported successfully!");
            } catch (Exception e) {
                showAlert("Error", "Failed to export: " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}