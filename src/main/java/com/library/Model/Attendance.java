package com.library.Model;

import java.time.LocalDateTime;

public class Attendance {
    private int id;
    private String studentId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public Attendance(int id, String studentId, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.id = id;
        this.studentId = studentId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public long getDurationInMinutes() {
        if (checkOut == null) return 0;
        return java.time.Duration.between(checkIn, checkOut).toMinutes();
    }
}