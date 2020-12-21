package com.ayprojects.helpinghands.tools;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.ResponseMessages;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.LangValueObj;
import com.ayprojects.helpinghands.models.PlaceAvailabilityDetails;
import com.ayprojects.helpinghands.models.UserSettings;
import com.ayprojects.helpinghands.services.log.LogService;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class Utility {

    @Autowired
    LogService logService;

    public static String getLanguageFromHeader(HttpHeaders headers) {
        String lang = "en";

        String[] languages = AppConstants.LANGUAGES;
        List<String> languageList = Arrays.asList(languages);
        if (headers.get(AppConstants.LABEL_HEADER_APPLANGUAGE) != null) {
            lang = headers.get(AppConstants.LABEL_HEADER_APPLANGUAGE).get(0);
            if (isFieldEmpty(lang) || !languageList.contains(lang)) {
                lang = "en";
            }
        } else {
            lang = "en";
        }
        return lang;
    }

    public static boolean isFieldEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static String currentDateTimeInUTC() {
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date());
    }

    public static String currentDateTimeInUTC(String dateTimeFormat) {
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_WITHOUT_UNDERSCORE);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date());
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getResponseMessage(String msg, String language) {
        try {
            Field f = ResponseMessages.class.getField(language + "_" + msg);
            LOGGER.info("Utility->getResponseMessage()->f.getName() is " + f.getName());
            String s = (String) f.get(new ResponseMessages());
            LOGGER.info("Utility->getResponseMessage()->String got from REsponseMessages is " + s);
            return s;
        } catch (Exception ex) {
            LOGGER.info("Utility->getResponseMessage()->got exception=" + ex.getMessage());
            ex.getMessage();
        }
        return "";
    }

    public static UserSettings getGlobalUserSettings() {
        return new UserSettings(AppConstants.TOTAL_ADD_PLACES_LIMIT, AppConstants.PER_DAY_ADD_PLACES_LIMIT, AppConstants.PER_DAY_ADD_POSTS_LIMIT, AppConstants.PER_PLACE_IMAGES_LIMIT, AppConstants.PER_POST_IMAGES_LIMIT, AppConstants.PER_PLACE_PRODUCTS_LIMIT, false, false, false, false);
    }

    public static List<String> checkForEmptyStrings(Map<String, String> validationStrings) {
        List<String> emptyStringsList = new ArrayList<>();
        if (validationStrings == null || validationStrings.size() == 0) return emptyStringsList;

        for (Map.Entry<String, String> entry : validationStrings.entrySet()) {
            if (Utility.isFieldEmpty(entry.getValue())) emptyStringsList.add(entry.getKey());
        }
        return emptyStringsList;
    }

    public static String distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        double result = c * r;
        if (result > 1) {
            return " " + roundTwoDecimals(result) + " KM";
        } else {
            return "" + roundTwoDecimals(convertMetres(result)) + " m";
        }
    }

    public static String[] calculatePlaceOpenCloseMsg(PlaceAvailabilityDetails p, String language) {
        if (p == null) return new String[]{""};
        if (p.isProvide24into7()) {
            return new String[]{Utility.getResponseMessage(AppConstants.MSG_OPEN_24INTO7, language), AppConstants.OPEN};
        }
        Calendar now = Calendar.getInstance();
        String currentHourMin = now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
        LOGGER.info("calculatePlaceOpenCloseMsg->currentHourMin=" + currentHourMin);

        SimpleDateFormat format = new SimpleDateFormat(AppConstants.DATE_FORMAT_HOUR_MIN);
        try {
            Date currentTime = format.parse(currentHourMin);
            Date openingTime = format.parse(p.getPlaceOpeningTime());
            Date closingTime = format.parse(p.getPlaceClosingTime());
            Date lunchStartTime = null;
            Date lunchEndTime = null;
            if (!p.getHaveNoLunchHours()) {
                lunchStartTime = format.parse(p.getLunchStartTime());
                lunchEndTime = format.parse(p.getLunchEndTime());
            }

            //if before opening time
            if (currentTime.before(openingTime) || currentTime.after(closingTime)) {
                return new String[]{Utility.getResponseMessage(AppConstants.MSG_CLOSED_OPENS_AT, language) + " " + p.getPlaceOpeningTime(), AppConstants.CLOSED};
            } else {

                if ((currentTime.getTime() - closingTime.getTime()) <= AppConstants.HOUR_IN_MILLIS) {
                    return new String[]{Utility.getResponseMessage(AppConstants.MSG_OPEN_CLOSES_AT, language) + " " + p.getPlaceClosingTime(), AppConstants.OPEN};
                } else if (p.getHaveNoLunchHours()) {
                    return new String[]{Utility.getResponseMessage(AppConstants.MSG_NO_LUNCH_HOURS, language), AppConstants.OPEN};
                } else if (p.getHaveNoLunchHours() && currentTime.after(lunchStartTime) && currentTime.before(lunchEndTime)) {
                    return new String[]{Utility.getResponseMessage(AppConstants.MSG_LUNCH_HOURS, language), AppConstants.OPEN};
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new String[]{""};
    }

    public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.parseDouble(twoDForm.format(d));
    }

    public static double convertMetres(double km) {
        return km * 1000;
    }

    public static String getMainCategoryName(DhPlaceCategories mc, String language) {
        if (mc == null) return null;
        if (mc.getTranslations() == null) return mc.getDefaultName();

        for (LangValueObj l : mc.getTranslations()) {
            if (l.getLang().equalsIgnoreCase(language)) return l.getValue();
        }
        return mc.getDefaultName();
    }

    public void addLog(String username, String actionMsg) {
        if (logService == null || Utility.isFieldEmpty(username) || Utility.isFieldEmpty(actionMsg)) {
            return;
        }
        logService.addLog(new DhLog(Utility.getUUID(), username, actionMsg, Utility.currentDateTimeInUTC(), Utility.currentDateTimeInUTC(), AppConstants.SCHEMA_VERSION));
    }

    public List<String> uplodImages(String imgUploadFolder, MultipartFile[] multipartImages, String imagePrefix) throws IOException {
        List<String> uploadedImageNames = new ArrayList<>();
        if (multipartImages == null || multipartImages.length == 0) return uploadedImageNames;
        Path path1 = Paths.get(imgUploadFolder);
        Files.createDirectories(path1);
        for (MultipartFile multipartFile : multipartImages) {
            String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String imgPath = imgUploadFolder + imagePrefix + Calendar.getInstance().getTimeInMillis() + "." + ext;
            LOGGER.info("Utility->uplodImages : imgUploadFolderWithFile = " + imgPath);
            byte[] bytes = multipartFile.getBytes();
            Path filePath = Paths.get(imgPath);
            Files.probeContentType(filePath);
            Files.write(filePath, bytes);
            uploadedImageNames.add(imgPath);
        }
        return uploadedImageNames;
    }

    public AllCommonUsedAttributes setCommonAttrs(AllCommonUsedAttributes obj, String status) {
        if (obj == null) obj = new AllCommonUsedAttributes();
        obj.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        obj.setCreatedDateTime(currentDateTimeInUTC());
        obj.setModifiedDateTime(currentDateTimeInUTC());
        obj.setStatus(status);
        return obj;
    }
}
