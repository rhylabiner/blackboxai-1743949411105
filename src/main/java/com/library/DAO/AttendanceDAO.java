package com.library.DAO;

import com.library.Model.Attendance;
import com.library.Util.DatabaseUtil;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    public static void checkInStudent(String studentId) throws SQLException {
        String sql = "INSERT INTO Attendance(student_id, check_in) VALUES(?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        }
    }

    public static void checkOutStudent(int attendanceId) throws SQLException {
        String sql = "UPDATE Attendance SET check_out = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(2, attendanceId);
            pstmt.executeUpdate();
        }
    }

    public static List<Attendance> getAttendanceByStudent(String studentId) throws SQLException {
        List<Attendance> records = new ArrayList<>();
        String sql = "SELECT * FROM Attendance WHERE student_id = ? ORDER BY check_in DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                records.add(new Attendance(
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getTimestamp("check_in").toLocalDateTime(),
                    rs.getTimestamp("check_out") != null ? 
                        rs.getTimestamp("check_out").toLocalDateTime() : null
                ));
            }
        }
        return records;
    }

    public static List<Attendance> getCurrentActiveSessions() throws SQLException {
        List<Attendance> activeSessions = new ArrayList<>();
        String sql = "SELECT * FROM Attendance WHERE check_out IS NULL";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                activeSessions.add(new Attendance(
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getTimestamp("check_in").toLocalDateTime(),
                    null
                ));
            }
        }
        return activeSessions;
    }
}