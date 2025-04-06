package com.library.Model;

public class Student {
    private String studentId;
    private String name;
    private String qrCodePath;

    public Student(String studentId, String name, String qrCodePath) {
        this.studentId = studentId;
        this.name = name;
        this.qrCodePath = qrCodePath;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQrCodePath() {
        return qrCodePath;
    }

    public void setQrCodePath(String qrCodePath) {
        this.qrCodePath = qrCodePath;
    }
}