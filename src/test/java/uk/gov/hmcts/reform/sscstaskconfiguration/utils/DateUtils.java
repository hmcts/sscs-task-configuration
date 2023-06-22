package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateUtils() {
    }

    public static String lastMonth() {
        return LocalDateTime.now().minusMonths(1).format(dateFormat);
    }

    public static String lastWeek() {
        return LocalDateTime.now().minusDays(7).format(dateFormat);
    }

    public static String today() {
        return LocalDateTime.now().format(dateFormat);
    }

    public static String tomorrow() {
        return LocalDateTime.now().plusDays(1).format(dateFormat);
    }

    public static String nextWeek() {
        return LocalDateTime.now().plusDays(7).format(dateFormat);
    }

    public static String nextMonth() {
        return LocalDateTime.now().plusMonths(1).format(dateFormat);
    }
}
