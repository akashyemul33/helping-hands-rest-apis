package com.ayprojects.helpinghands.services.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

@Service
public class FirebaseSetup {
    @PostConstruct
    public static void initializeFirebase() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("/home/ay/Pictures/server_side_private_key/food-delivery-601b2-firebase-adminsdk-r13fq-acc56cc0bc.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
