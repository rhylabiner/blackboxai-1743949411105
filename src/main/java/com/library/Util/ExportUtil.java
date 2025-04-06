package com.library.Util;

import com.library.Model.*;
import java.io.FileWriter;
import java.util.List;

public class ExportUtil {
    public static void exportToCSV(List<?> data, String filePath) throws Exception {
        try (FileWriter writer = new FileWriter(filePath)) {
            if (!data.isEmpty()) {
                // Write headers based on first item's class
                Object first = data.get(0);
                if (first instanceof Student) {
                    writer.write("StudentID,Name,QRCodePath\n");
                    for (Object item : data) {
                        Student s = (Student) item;
                        writer.write(String.format("%s,%s,%s\n", 
                            s.getStudentId(), s.getName(), s.getQrCodePath()));
                    }
                } else if (first instanceof Book) {
                    writer.write("ISBN,Title,Author,Available,QRCodePath\n");
                    for (Object item : data) {
                        Book b = (Book) item;
                        writer.write(String.format("%s,%s,%s,%s,%s\n", 
                            b.getIsbn(), b.getTitle(), b.getAuthor(), 
                            b.isAvailable(), b.getQrCodePath()));
                    }
                }
                // Add other data types as needed
            }
        }
    }
}