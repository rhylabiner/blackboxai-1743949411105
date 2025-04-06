package com.library.DAO;

import com.library.Model.Book;
import com.library.Util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public static void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO Books(isbn, title, author, available, qr_code_path) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setBoolean(4, book.isAvailable());
            pstmt.setString(5, book.getQrCodePath());
            pstmt.executeUpdate();
        }
    }

    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getBoolean("available"),
                    rs.getString("qr_code_path")
                ));
            }
        }
        return books;
    }

    public static void updateBook(Book book) throws SQLException {
        String sql = "UPDATE Books SET title = ?, author = ?, available = ?, qr_code_path = ? WHERE isbn = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setBoolean(3, book.isAvailable());
            pstmt.setString(4, book.getQrCodePath());
            pstmt.setString(5, book.getIsbn());
            pstmt.executeUpdate();
        }
    }

    public static void deleteBook(String isbn) throws SQLException {
        String sql = "DELETE FROM Books WHERE isbn = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
        }
    }

    public static Book getBookByIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM Books WHERE isbn = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Book(
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getBoolean("available"),
                    rs.getString("qr_code_path")
                );
            }
        }
        return null;
    }
}