package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.PlaceAvailabilityDetails;
import com.ayprojects.helpinghands.tools.Utility;


public class tmp {
    public static void main(String[] args) {
        System.out.println("tmp");
//        checkOpenCloseTimings();
          checkCountryCodeDetectionFromPhone();
    }

    private static void checkCountryCodeDetectionFromPhone() {
    }

    private static void checkOpenCloseTimings() {
        PlaceAvailabilityDetails p = new PlaceAvailabilityDetails();
        p.setProvide24into7(true);
        p.setPlaceOpeningTime("10:40");
        p.setPlaceClosingTime("19:45");
        p.setLunchStartTime("19:20");
        p.setLunchEndTime("19:40");

        String msg[]=Utility.calculatePlaceOpenCloseMsg(p,"EN");
        System.out.println("msg[0]="+msg[0]+" msg[1]="+msg[1]);
        boolean isOpen = msg.length > 1 && (msg[1].equalsIgnoreCase(AppConstants.OPEN));
        System.out.println("isOpen"+isOpen);
    }
}
