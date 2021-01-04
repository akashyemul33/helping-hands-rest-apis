package com.ayprojects.helpinghands.util.tools;

import com.ayprojects.helpinghands.AppConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CalendarOperations {

    public boolean verifyTimeFollowsCorrectFormat(String lastLogoutTime) {
        if (Utility.isFieldEmpty(lastLogoutTime)) return false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
        try {
            simpleDateFormat.parse(lastLogoutTime);
            return true;
        } catch (ParseException e) {
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
