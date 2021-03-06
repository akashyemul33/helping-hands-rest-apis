package com.ayprojects.helpinghands;

import com.ayprojects.helpinghands.services.firebase.FirebaseSetup;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Temp {

    public static void main(String[] args) throws IOException {
        sendMsgToRegToken();
    }

    public static void sendMsgToRegToken() {
        try {
            FirebaseSetup firebaseSetup = new FirebaseSetup();
            firebaseSetup.initializeFirebase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String regToken = "c3J0Qo3_SoKv0Nnrt6gOG2:APA91bHTe05xykY6vJfEQR9iVILX7dSCJVw-mRPMPJb7AkhlzRZTDxq-fuVjDgsCFzcFGXttL5ujnAMuGuCaq0UEOkiP7El8Ov6PS_PEkbMpsmCZlqi5xCh4rMjXZaBgZoOQdLWMf2oB";
        Message message = Message.builder()
                .putData("title", "Some title")
                .putData("body", "some body")
                .setToken(regToken)
                        .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        System.out.println("sendMsgToRegToken->response=" + response);

    }

    public static void abc() throws IOException {
        File fileEng = new File("/home/ay/Desktop/english_categories_array");
//        File fileMar = new File("/home/ay/Desktop/marathi-categories-array");
        BufferedReader bfEng = new BufferedReader(new FileReader(fileEng));
//        BufferedReader bfMr = new BufferedReader(new FileReader(fileMar));
        String st;
        StringBuilder allLines = new StringBuilder();
        while ((st = bfEng.readLine()) != null) {
            String[] splitStrEng = st.split(":")[0].split(",");
            String[] splitStrMr = st.split(":")[1].split(",");
            String[] splitStrTe = st.split(":")[2].split(",");
            String[] splitStrKn = st.split(":")[3].split(",");
            String[] splitStrGu = st.split(":")[4].split(",");
            String placeMainCategoryId = "M_PLS_CTGRY_20210420140153";
            for (int i = 0; i < splitStrEng.length; i++) {
                System.out.println(String.format("  {\n" +
                        "      \"defaultName\": \"%s\",\n" +
                        "      \"status\": \"%s\",\n" +
                        "      \"placeMainCategoryId\": \"%s\",\n" +
                        "      \"placeSubCategoryImagePath\": \"\",\n" +
                        "      \"translations\": [\n" +
                        "        {\n" +
                        "          \"lang\": \"mr\",\n" +
                        "          \"value\": \"%s\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"lang\": \"hi\",\n" +
                        "          \"value\": \"%s\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"lang\": \"te\",\n" +
                        "          \"value\": \"%s\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"lang\": \"kn\",\n" +
                        "          \"value\": \"%s\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"lang\": \"gu\",\n" +
                        "          \"value\": \"%s\"\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },", splitStrEng[i].trim(), "Active", placeMainCategoryId, splitStrMr[i].trim(), splitStrMr[i].trim(), splitStrTe[i].trim(), splitStrKn[i].trim(), splitStrGu[i].trim()));

            }
            allLines.append(st);
        }
    }


    public static void getAwsKeys() throws IOException {
        File file = new File("/home/ay/Desktop/user_akash_active_aws_keys.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String st;
        StringBuilder allLines = new StringBuilder();
        while ((st = bufferedReader.readLine()) != null) {
            System.out.println("st=" + st);
            allLines.append(st);
        }

        String accessKey = allLines.substring(0, allLines.indexOf(":"));
        String secretKey = allLines.substring(allLines.indexOf(":") + 1);
        System.out.println("AccessKey=" + accessKey);
        System.out.println("SecretKey=" + secretKey);
        AppConstants.ACCESS_KEY = accessKey;
        AppConstants.SECRET_KEY = secretKey;
        System.out.println("AppConstants.AccessKey=" + AppConstants.ACCESS_KEY);
        System.out.println("AppConstants.SecretKey=" + AppConstants.SECRET_KEY);
    }
}
