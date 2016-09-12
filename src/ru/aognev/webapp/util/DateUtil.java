package ru.aognev.webapp.util;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by aognev on 07.09.2016.
 */
public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
