package com.library.DAO;

import com.library.Model.Student;
import com.library.Util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public static void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Students(student_id, name, qr_code_path) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getQrCodePath());
            pstmt.executeUpdate();
        }
    }

    public static List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                    rs.getString("student_id"),
                    rs.getString("name"),
                    rs.getString("qr_code_path")
                ));
            }
        }
        return students;
    }

    public static void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE Students SET name = ?, qr_code_path = ? WHERE student_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getQrCodePath());
            pstmt.setString(3, student.getStudentId());
            pstmt.executeUpdate();
        }
    }

    public static void deleteStudent(String studentId) throws SQLException {
        String sql = "DELETE FROM Students WHERE student_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.executeUpdate();
        }
    }
}