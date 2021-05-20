package com.ayprojects.helpinghands.util.tools;

import com.ayprojects.helpinghands.AppConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarOperations {

    public boolean verifyTimeFollowsCorrectFormat(String time) {
        return verifyDateTimeFormat(time, AppConstants.DATE_FORMAT_HOUR_MIN);
    }

    public boolean verifyDateTimeFollowsCorrectFormat(String time) {
        return verifyDateTimeFormat(time, AppConstants.DATE_TIME_FORMAT);
    }

    private boolean verifyDateTimeFormat(String time, String dateTimeFormat) {
        if (Utility.isFieldEmpty(time)) return false;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        try {
            dateTimeFormatter.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String currentDateTimeInUTC() throws NullPointerException, IllegalArgumentException {
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        return dateFormat.format(new Date());
    }

    public String getTimeAtFileEnd() throws NullPointerException, IllegalArgumentException {
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_WITHOUT_UNDERSCORE);
        dateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        return dateFormat.format(new Date());
    }

    public static String convert24HoursFormatTo12Hours(String timeIn24HourFormat) {
        //Displaying given time in 12 hour format with AM/PM
        if (Utility.isFieldEmpty(timeIn24HourFormat)) return "";
        //old format
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        try {
            Date date3 = sdf.parse(timeIn24HourFormat);
            //new format
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
            //formatting the given time to new format with AM/PM
            return sdf2.format(date3);
        } catch (ParseException e) {
            e.printStackTrace();
            return timeIn24HourFormat;
        }
    }
}
