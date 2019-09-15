package com.example.helloworldbikefinder.firebase;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UploadData {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String latitude;
    private String longitude;
    private String downloadUrl2;
    private File img;

    public UploadData(File img, String latitude, String longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
        setImg(img);
        upload();
    }

    public void upload() {
        // Uploads the byte array of the image to firebase storage, which also returns downloadUrl

        System.out.println("\n\n" + latitude + " " + longitude + "\n\n");
        uploadToFirebaseStorage(img);
        System.out.println("\n\n                                    DOWNLOAD URL:" + downloadUrl2);

        // Creates bike map consisting of its latitude, longitude, and the now acquired downloadUrl


        // Adds the bike object to the Cloud Firestore database
        //addToDatabase(bike);

    }

    public void addToDatabase(Map bike) {
        // Add a new document with a generated ID
        db.collection("bikes")
                .add(bike)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void uploadToFirebaseStorage(File img) {
        final String[] downloadUrl = new String[1];
        String path = "bikeImages/" + UUID.randomUUID() + ".png";
        StorageReference bikeRefs = storage.getReference(path);
        UploadTask uploadTask = bikeRefs.putFile(android.net.Uri.parse(img.toURI().toString()));
        StorageReference storageRef = storage.getReference();
        //final StorageReference imageRef = storageRef.child(path);
        final Map<String, String> bike = new HashMap<>();
        bike.put("latitude", latitude);
        bike.put("longitude", longitude);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Task<Uri> downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                downloadUri.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        System.out.println( downloadUri.getResult().toString());
                        downloadUrl[0] = downloadUri.getResult().toString();
                        System.out.println(downloadUrl[0]);
                        bike.put("downloadUrl", downloadUrl[0]);
                        db.collection("bikes")
                                .add(bike)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }
                });
            }
        });

    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDownloadUrl() {
        return downloadUrl2;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl2 = downloadUrl;
    }

    public void setImg(File img) {
        this.img = img;
    }


}
