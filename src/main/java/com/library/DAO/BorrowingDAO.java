package com.library.DAO;

import com.library.Model.Borrowing;
import com.library.Util.DatabaseUtil;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BorrowingDAO {
    public static void borrowBook(Borrowing borrowing) throws SQLException {
        String sql = "INSERT INTO Borrowings(student_id, isbn, borrow_date, due_date) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, borrowing.getStudentId());
            pstmt.setString(2, borrowing.getIsbn());
            pstmt.setTimestamp(3, Timestamp.valueOf(borrowing.getBorrowDate()));
            pstmt.setTimestamp(4, Timestamp.valueOf(borrowing.getDueDate()));
            pstmt.executeUpdate();
        }
    }

    public static void returnBook(int borrowingId) throws SQLException {
        String sql = "UPDATE Borrowings SET return_date = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(2, borrowingId);
            pstmt.executeUpdate();
        }
    }

    public static List<Borrowing> getBorrowingsByStudent(String studentId) throws SQLException {
        List<Borrowing> borrowings = new ArrayList<>();
        String sql = "SELECT * FROM Borrowings WHERE student_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                borrowings.add(new Borrowing(
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getString("isbn"),
                    rs.getTimestamp("borrow_date").toLocalDateTime(),
                    rs.getTimestamp("due_date").toLocalDateTime(),
                    rs.getTimestamp("return_date") != null ? 
                        rs.getTimestamp("return_date").toLocalDateTime() : null
                ));
            }
        }
        return borrowings;
    }

    public static List<Borrowing> getOverdueBorrowings() throws SQLException {
        List<Borrowing> overdueBorrowings = new ArrayList<>();
        String sql = "SELECT * FROM Borrowings WHERE return_date IS NULL AND due_date < ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                overdueBorrowings.add(new Borrowing(
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getString("isbn"),
                    rs.getTimestamp("borrow_date").toLocalDateTime(),
                    rs.getTimestamp("due_date").toLocalDateTime(),
                    null
                ));
            }
        }
        return overdueBorrowings;
    }
}