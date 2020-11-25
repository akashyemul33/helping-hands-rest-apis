package com.ayprojects.helpinghands.tools;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.ResponseMessages;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.UserSettings;
import com.ayprojects.helpinghands.services.log.LogService;

import org.apache.commons.io.FilenameUtils;
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
        if (headers.get(AppConstants.LABEL_HEADER_APPLANGUAGE)!=null) {
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

    public static String getUUID()
    {
        return UUID.randomUUID().toString();
    }

    public static String getResponseMessage(String msg, String language) {
        try {
            Field f = ResponseMessages.class.getField(language+"_"+msg);
            LOGGER.info("Utility->getResponseMessage()->f.getName() is "+f.getName());
            String s= (String) f.get(new ResponseMessages());
            LOGGER.info("Utility->getResponseMessage()->String got from REsponseMessages is "+s);
            return s;
        } catch (Exception ex) {
            LOGGER.info("Utility->getResponseMessage()->got exception="+ex.getMessage());
            ex.getMessage();
        }
        return "";
    }

    public static UserSettings getGlobalUserSettings() {
        return new UserSettings(AppConstants.TOTAL_ADD_PLACES_LIMIT,AppConstants.PER_DAY_ADD_PLACES_LIMIT,AppConstants.PER_DAY_ADD_POSTS_LIMIT,AppConstants.PER_PLACE_IMAGES_LIMIT,AppConstants.PER_POST_IMAGES_LIMIT,AppConstants.PER_PLACE_PRODUCTS_LIMIT,false,false,false,false);
    }

    public void addLog(String username,String actionMsg){
        if(logService == null || Utility.isFieldEmpty(username) || Utility.isFieldEmpty(actionMsg)){
            return;
        }
        logService.addLog(new DhLog(Utility.getUUID(), username, actionMsg, Utility.currentDateTimeInUTC(), Utility.currentDateTimeInUTC(), AppConstants.SCHEMA_VERSION));
    }

    public static List<String> checkForEmptyStrings(Map<String,String> validationStrings){
        List<String> emptyStringsList = new ArrayList<>();
        if(validationStrings==null || validationStrings.size()==0)return emptyStringsList;

        for(Map.Entry<String,String> entry : validationStrings.entrySet()){
            if(Utility.isFieldEmpty(entry.getValue()))emptyStringsList.add(entry.getKey());
        }
        return emptyStringsList;
    }

    public List<String> uplodImages(String imgUploadFolder, MultipartFile[] multipartImages, String imagePrefix) throws IOException {
            List<String> uploadedImageNames = new ArrayList<>();
            if(multipartImages==null || multipartImages.length==0)return uploadedImageNames;
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
        obj.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        obj.setCreatedDateTime(currentDateTimeInUTC());
        obj.setModifiedDateTime(currentDateTimeInUTC());
        obj.setStatus(status);
        return obj;
    }

}
