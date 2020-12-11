package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // DB doesn't support LocalDate.MIN/MAX
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);

    private DateTimeUtil() {
    }

    public static LocalDateTime atStartOfDayOrMin(LocalDate localDate) {
        return localDate == null ? MIN_DATE : localDate.atStartOfDay();
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDate localDate) {
        return localDate == null ? MAX_DATE : localDate.plus(1, ChronoUnit.DAYS).atStartOfDay();
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.hasText(str) ? LocalDate.parse(str) : null;
    }

    public static @Nullable LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.hasText(str) ? LocalTime.parse(str) : null;
    }
}
