package com.library.Model;

import java.time.LocalDateTime;

public class Borrowing {
    private int id;
    private String studentId;
    private String isbn;
    private LocalDateTime borrowDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;

    public Borrowing(int id, String studentId, String isbn, LocalDateTime borrowDate, LocalDateTime dueDate, LocalDateTime returnDate) {
        this.id = id;
        this.studentId = studentId;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isOverdue() {
        return returnDate == null && LocalDateTime.now().isAfter(dueDate);
    }
}