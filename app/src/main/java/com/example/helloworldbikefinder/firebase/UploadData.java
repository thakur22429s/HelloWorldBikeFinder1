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
    private String downloadUrl;
    private File img;

    public UploadData(File img, String latitude, String longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
        setImg(img);
        upload();
    }

    public void upload() {
        // Uploads the byte array of the image to firebase storage, which also returns downloadUrl


        uploadToFirebaseStorage(img);

        // Creates bike map consisting of its latitude, longitude, and the now acquired downloadUrl
        Map<String, Object> bike = new HashMap<>();
        bike.put("latitude", latitude);
        bike.put("longitude", longitude);
        bike.put("downloadUrl", downloadUrl);

        // Adds the bike object to the Cloud Firestore database
        addToDatabase(bike);

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
        String path = "bikeImages/" + UUID.randomUUID() + ".png";
        StorageReference reference = storage.getReference();
        final StorageReference bikeRefs = reference.child(path);
        //final StorageReference ref = storageRef.child("images/mountains.jpg");
        //uploadTask = ref.putFile(file);

        UploadTask uploadTask = bikeRefs.putFile(android.net.Uri.parse(img.toURI().toString()));

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    System.out.println("\n\nEXCEPTION\n\n");
                    throw task.getException();
                }


                // Continue with the task to get the download URL
                System.out.println("\n\nGETTING DOWNLOAD\n\n");
                return bikeRefs.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    System.out.println("\n\nDOWNLOAD ACCESSED\n\n");
                    Uri downloadUri = task.getResult();
                    downloadUrl = downloadUri.toString();
                } else {
                    System.out.println("\n\nNo download url accessed\n\n");
                }
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
        this.downloadUrl = downloadUrl;
    }

    public void setImg(File img) {
        this.img = img;
    }


}
