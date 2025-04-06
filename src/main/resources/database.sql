-- SQLite schema for Library Management System
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS Students (
    student_id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    qr_code_path TEXT
);

CREATE TABLE IF NOT EXISTS Books (
    isbn TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    author TEXT,
    available BOOLEAN DEFAULT TRUE,
    qr_code_path TEXT
);

CREATE TABLE IF NOT EXISTS Attendance (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id TEXT,
    check_in TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    check_out TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES Students(student_id)
);

CREATE TABLE IF NOT EXISTS Borrowings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id TEXT,
    isbn TEXT,
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP,
    return_date TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (isbn) REFERENCES Books(isbn)
);

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_attendance_student ON Attendance(student_id);
CREATE INDEX IF NOT EXISTS idx_borrowings_student ON Borrowings(student_id);
CREATE INDEX IF NOT EXISTS idx_borrowings_book ON Borrowings(isbn);