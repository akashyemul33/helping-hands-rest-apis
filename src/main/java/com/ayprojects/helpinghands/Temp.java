package com.ayprojects.helpinghands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Temp {
    public static void getAwsKeys() throws IOException {
        File file = new File("/home/ay/Desktop/user_akash_active_aws_keys.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String st;
        StringBuilder allLines = new StringBuilder();
        while ((st = bufferedReader.readLine()) != null) {
            System.out.println("st=" + st);
            allLines.append(st);
        }

        String accessKey = allLines.substring(0,allLines.indexOf(":"));
        String secretKey = allLines.substring(allLines.indexOf(":")+1);
        System.out.println("AccessKey=" + accessKey);
        System.out.println("SecretKey=" + secretKey);
        AppConstants.ACCESS_KEY=accessKey;
        AppConstants.SECRET_KEY=secretKey;
        System.out.println("AppConstants.AccessKey=" + AppConstants.ACCESS_KEY);
        System.out.println("AppConstants.SecretKey=" + AppConstants.SECRET_KEY);
    }
}
