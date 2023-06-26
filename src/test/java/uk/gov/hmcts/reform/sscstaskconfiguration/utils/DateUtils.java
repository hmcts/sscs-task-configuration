package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateUtils() {
    }

    public static String today() {
        return LocalDate.now().format(dateFormat);
    }

    public static String today(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(dateFormat);
    }

    public static String tomorrow() {
        return today(1);
    }

    public static String tomorrow(int plusDays) {
        return today(1 + plusDays);
    }

    public static String lastWeek() {
        return today(-7);
    }

    public static String nextWeek() {
        return today(7);
    }

    public static String nextWeek(int plusDays) {
        return today(7 + plusDays);
    }

    public static String lastMonth() {
        return LocalDate.now().minusMonths(1).format(dateFormat);
    }

    public static String nextMonth() {
        return LocalDate.now().plusMonths(1).format(dateFormat);
    }

    public static String nextMonth(int plusDays) {
        return LocalDate.now().plusMonths(1).plusDays(plusDays).format(dateFormat);
    }
}
