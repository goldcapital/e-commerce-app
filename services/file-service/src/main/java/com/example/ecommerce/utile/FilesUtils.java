package com.example.ecommerce.utile;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;

public class FilesUtils {
    public static String createPath(String fileName) {
        var date = LocalDate.now().format(ofPattern("yyyy/MM/dd"));
        return date + "/" + fileName;
    }
    public static String getFileName(String originalName) {
        return System.currentTimeMillis() + originalName;
    }
}
