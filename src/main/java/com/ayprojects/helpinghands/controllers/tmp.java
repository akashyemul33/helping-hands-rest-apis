package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.PlaceAvailabilityDetails;
import com.ayprojects.helpinghands.util.tools.Utility;


public class tmp {
    public static void main(String[] args) {
        checkOpenCloseTimings();
    }

    private static void checkCountryCodeDetectionFromPhone() {
    }

    private static void checkOpenCloseTimings() {
        PlaceAvailabilityDetails p = new PlaceAvailabilityDetails();
        p.setProvide24into7(false);
        p.setPlaceOpeningTime("09:30");
        p.setPlaceClosingTime("15:40");
        p.setHaveNoLunchHours(false);
        p.setLunchStartTime("12:40");
        p.setLunchEndTime("13:06");

//        String msg = Utility.calculatePlaceOpenCloseMsgWhenNot24into7(p, AppConstants.LANG_ENGLISH);
//        System.out.println("/msg:" + msg);
    }
}
