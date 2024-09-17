package com.example.hm.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateUtils {


    public static Timestamp convertToTimestamp(String dateTimeString) {
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
      for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
                return Timestamp.valueOf(dateTime);
            } catch (DateTimeParseException e) {
            }
        }
        throw new IllegalArgumentException("Invalid date-time format. Supported formats are 'yyyy-MM-dd HH:mm:ss', 'yyyy/MM/dd HH:mm:ss', 'dd-MM-yyyy HH:mm:ss', 'dd/MM/yyyy HH:mm:ss'.");
    }
}
