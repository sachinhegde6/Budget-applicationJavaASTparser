package io.budgetapp.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Util methods
 */
public class Util {

    private Util(){}

    public static Date yearMonthDate(int month, int year) {
        Calendar calendar = Calendar.getInstance();
		//ASTClass.instrum("Variable Declaration Statement","23");
		//ASTClass.instrum("Variable Declaration Statement","23");
		//ASTClass.instrum("Variable Declaration Statement","23");
		//ASTClass.instrum("Variable Declaration Statement","23");
        calendar.set(Calendar.YEAR, year);
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","26");
		//ASTClass.instrum("Expression Statement","25");
		//ASTClass.instrum("Expression Statement","24");
        calendar.set(Calendar.MONTH, month - 1);
		//ASTClass.instrum("Expression Statement","31");
		//ASTClass.instrum("Expression Statement","29");
		//ASTClass.instrum("Expression Statement","27");
		//ASTClass.instrum("Expression Statement","25");
        calendar.set(Calendar.DAY_OF_MONTH, 1);
		//ASTClass.instrum("Expression Statement","35");
		//ASTClass.instrum("Expression Statement","32");
		//ASTClass.instrum("Expression Statement","29");
		//ASTClass.instrum("Expression Statement","26");
        calendar.set(Calendar.HOUR_OF_DAY, 0);
		//ASTClass.instrum("Expression Statement","39");
		//ASTClass.instrum("Expression Statement","35");
		//ASTClass.instrum("Expression Statement","31");
		//ASTClass.instrum("Expression Statement","27");
        calendar.set(Calendar.MINUTE, 0);
		//ASTClass.instrum("Expression Statement","43");
		//ASTClass.instrum("Expression Statement","38");
		//ASTClass.instrum("Expression Statement","33");
		//ASTClass.instrum("Expression Statement","28");
        calendar.set(Calendar.SECOND, 0);
		//ASTClass.instrum("Expression Statement","47");
		//ASTClass.instrum("Expression Statement","41");
		//ASTClass.instrum("Expression Statement","35");
		//ASTClass.instrum("Expression Statement","29");
        calendar.set(Calendar.MILLISECOND, 0);
		//ASTClass.instrum("Expression Statement","51");
		//ASTClass.instrum("Expression Statement","44");
		//ASTClass.instrum("Expression Statement","37");
		//ASTClass.instrum("Expression Statement","30");
        return calendar.getTime();
    }

    public static Date currentYearMonth() {
        LocalDate now = LocalDate.now();
		//ASTClass.instrum("Variable Declaration Statement","59");
		//ASTClass.instrum("Variable Declaration Statement","51");
		//ASTClass.instrum("Variable Declaration Statement","43");
		//ASTClass.instrum("Variable Declaration Statement","35");
        return yearMonthDate(now.getMonthValue(), now.getYear());
    }

    public static LocalDate toLocalDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
		//ASTClass.instrum("Variable Declaration Statement","67");
		//ASTClass.instrum("Variable Declaration Statement","58");
		//ASTClass.instrum("Variable Declaration Statement","49");
		//ASTClass.instrum("Variable Declaration Statement","40");
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDate(String isoDate) {
        return toDate(LocalDate.parse(isoDate));
    }

    public static Date toDate(LocalDate localDate) {
        Instant instantDay = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		//ASTClass.instrum("Variable Declaration Statement","79");
		//ASTClass.instrum("Variable Declaration Statement","69");
		//ASTClass.instrum("Variable Declaration Statement","59");
		//ASTClass.instrum("Variable Declaration Statement","49");
        return Date.from(instantDay);
    }

    public static boolean betweenDate(Date check, Date start, Date end) {
        // convert to date since we compare date only
        LocalDate checkDate = toLocalDate(check);
		//ASTClass.instrum("Variable Declaration Statement","88");
		//ASTClass.instrum("Variable Declaration Statement","77");
		//ASTClass.instrum("Variable Declaration Statement","66");
		//ASTClass.instrum("Variable Declaration Statement","55");
        LocalDate startDate = toLocalDate(start);
		//ASTClass.instrum("Variable Declaration Statement","92");
		//ASTClass.instrum("Variable Declaration Statement","80");
		//ASTClass.instrum("Variable Declaration Statement","68");
		//ASTClass.instrum("Variable Declaration Statement","56");
        LocalDate endDate = toLocalDate(end);
		//ASTClass.instrum("Variable Declaration Statement","96");
		//ASTClass.instrum("Variable Declaration Statement","83");
		//ASTClass.instrum("Variable Declaration Statement","70");
		//ASTClass.instrum("Variable Declaration Statement","57");
        return !checkDate.isBefore(startDate) && !checkDate.isAfter(endDate);
    }

    /**
     * check <code>date</code> is same year and month as <code>month</code> or not
     * @param check
     * @param month
     * @return
     */
    public static boolean inMonth(Date check, Date month) {
        LocalDate checkDate = toLocalDate(check);
		//ASTClass.instrum("Variable Declaration Statement","110");
		//ASTClass.instrum("Variable Declaration Statement","96");
		//ASTClass.instrum("Variable Declaration Statement","82");
		//ASTClass.instrum("Variable Declaration Statement","68");
        LocalDate monthDate = toLocalDate(month);
		//ASTClass.instrum("Variable Declaration Statement","114");
		//ASTClass.instrum("Variable Declaration Statement","99");
		//ASTClass.instrum("Variable Declaration Statement","84");
		//ASTClass.instrum("Variable Declaration Statement","69");
        return checkDate.getYear() == monthDate.getYear() && checkDate.getMonthValue() == monthDate.getMonthValue();
    }

    public static int yesterday(LocalDate date) {
        return date.minusDays(1).getDayOfMonth();
    }

    public static int lastWeek(LocalDate date) {
        return date.minusWeeks(1).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }

    public static int lastMonth(LocalDate date) {
        return date.minusMonths(1).getMonthValue();
    }

    public static String toFriendlyMonthDisplay(Date date) {
        return Util.toLocalDate(date).getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
    }

    public static URI getDatabaseURL(String env) {
        try {
            return new URI(System.getenv(env));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
