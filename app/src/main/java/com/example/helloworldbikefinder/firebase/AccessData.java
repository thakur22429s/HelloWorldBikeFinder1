package com.example.helloworldbikefinder.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class AccessData {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Object> latitudes = new ArrayList<>();
    private ArrayList<Object> longitudes = new ArrayList<>();
    private ArrayList<Object> allBikesImg = new ArrayList<>();

    public void accessData() {
        db.collection("bikes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                // Code for each specific document would go here
                                Map data = document.getData();
                                latitudes.add(data.get("latitude"));
                                longitudes.add(data.get("longitude"));
                                allBikesImg.add(data.get("downloadUrl"));
                                String jsonString = convertToGeoJSON(data.get("latitude"), data.get("longitude"));
                                Gson gson = new Gson();
                                jsonString = gson.toJson(jsonString);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public String convertToGeoJSON(Object latitude, Object longitude) {
        return "{\"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [" +
                latitude + ", " + longitude + "]},properties: { \"name\": " + latitude + longitude + "} }";

    }


}
