package com.library.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:library.db";
    private static final String INIT_SCRIPT = "/database.sql";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             InputStream is = DatabaseUtil.class.getResourceAsStream(INIT_SCRIPT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            // Execute all statements
            for (String sql : sb.toString().split(";")) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql);
                }
            }
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}