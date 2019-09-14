package com.example.helloworldbikefinder.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UploadData {
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String latitude;
    private static String longitude;
    private static String downloadUrl;
    private static byte[] img;

    public UploadData(byte[] img, String latitude, String longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
        setImg(img);
    }

    public UploadData() {
        this(img, latitude, longitude);
    }

    public static void main(String[] args) {
        UploadData upload = new UploadData();

        // Uploads the byte array of the image to firebase storage, which also returns downloadUrl
        upload.uploadToFirebaseStorage(img);

        // Creates bike map consisting of its latitude, longitude, and the now acquired downloadUrl
        Map<String, Object> bike = new HashMap<>();
        bike.put("latitude", latitude);
        bike.put("longitude", longitude);
        bike.put("downloadUrl", downloadUrl);

        // Adds the bike object to the Cloud Firestore database
        upload.addToDatabase(bike);

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

    public void uploadToFirebaseStorage(byte[] img) {
        String path = "bikeImages/" + UUID.randomUUID() + ".png";
        StorageReference bikeRefs = storage.getReference(path);

        UploadTask uploadTask = bikeRefs.putBytes(img);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri url = taskSnapshot.getUploadSessionUri();
                downloadUrl = url.toString();
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
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        downloadUrl = downloadUrl;
    }

    public void setImg(byte[] img) {
        img = img;
    }


}
