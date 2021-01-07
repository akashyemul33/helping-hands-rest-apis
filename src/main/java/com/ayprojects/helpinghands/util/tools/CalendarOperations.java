package com.ayprojects.helpinghands.util.tools;

import com.ayprojects.helpinghands.AppConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.TimeZone;

public class CalendarOperations {

    public boolean verifyTimeFollowsCorrectFormat(String lastLogoutTime) {
        if (Utility.isFieldEmpty(lastLogoutTime)) return false;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(AppConstants.DATE_TIME_FORMAT);
        try {
            dateTimeFormatter.parse(lastLogoutTime);
            return true;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String currentDateTimeInUTC() throws NullPointerException, IllegalArgumentException {
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        return dateFormat.format(new Date());
    }

    public String getTimeAtFileEnd() throws NullPointerException, IllegalArgumentException {
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_WITHOUT_UNDERSCORE);
        dateFormat.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        return dateFormat.format(new Date());
    }

}
