package ru.job4j.grabber.utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SqlRuDateConvertor {
    private static final Locale LOCALE = new Locale("ru");
    private static final String[] SHORT_MONTHS = {
            "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"
    };

    public static Date convertor(String strDate) {
        Date date = null;
        try {
            if (strDate.startsWith("сегодня") || strDate.startsWith("вчера")) {
                date = humanityToDate(strDate);
            } else {
                DateFormatSymbols dfs = DateFormatSymbols.getInstance(LOCALE);
                dfs.setShortMonths(SHORT_MONTHS);
                SimpleDateFormat format;
                format = new SimpleDateFormat("d MMM yy HH:mm", LOCALE);
                format.setDateFormatSymbols(dfs);
                date = format.parse(parseDate(strDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private static Date humanityToDate(String strDate) {
        String[] dateParts = parseDate(strDate).split(" ");
        Calendar calendar = Calendar.getInstance(LOCALE);
        if (dateParts[0].equals("вчера")) {
            calendar.add(Calendar.DATE, -1);
        }
        int hours = Integer.parseInt(dateParts[1].split(":")[0]);
        int minutes = Integer.parseInt(dateParts[1].split(":")[1]);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private static String parseDate(String text) {
        String[] splitedText = text.split(", ");
        String date = splitedText[0];
        String time = splitedText[1].split(" ")[0];
        return date + " " + time;
    }
}
