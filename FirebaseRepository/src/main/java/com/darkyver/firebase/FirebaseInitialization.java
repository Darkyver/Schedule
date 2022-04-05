package com.darkyver.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class FirebaseInitialization {

    public void initialization(){
        try {
            System.out.println();
            FileInputStream serviceAccount =
                    new FileInputStream(getClass().getClassLoader().getResource("serviceAccountFirebase.json").getPath());


//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .build();

            FirebaseOptions build = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(build);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
