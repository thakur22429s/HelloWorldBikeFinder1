package com.example.helloworldbikefinder;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.helloworldbikefinder.firebase.AccessData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.grpc.internal.IoUtils;

public class FindYourBikeActivity extends AppCompatActivity {

    ScrollView bicycleScrollView;
    byte[] img_byte_array;
    Bitmap[] bitmapImages;
    ArrayList<Map> data;
    ArrayList<String> docIDs;
    ArrayList<BikeTab> bikeTabs;

    String[] listViewLocation = new String[]{"WALC", "Lawson", "Engg. Fountain", "CL 1950 LH", "Bell Tower"};
    int[] listViewImage = new int[]{R.drawable.bikey_on_sapling, R.drawable.bikes_hang_on_tree, R.drawable.limebike_tree_crop, R.drawable.bikey_on_tree, R.drawable.tree};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_bike);

        collectAllData();
        createTabs();

        /* FOR NOW, FIREBASE DOESN'T WORK.
        for (int i = 0; i < data.size(); i++) {
            String url = (String) data.get(i).get("downloadUrl");
            try {
                BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                img_byte_array = IoUtils.toByteArray(in);

                // now convert the img_byte_array to bitmap and display it on ImageView
                Bitmap bmp = BitmapFactory.decodeByteArray(img_byte_array, 0, img_byte_array.length);
                bitmapImages[i] = bmp;

            } catch (IOException e) {
                // do nothing here
                // hope to God it fucking works and there are no exceptions
            }
        }
        */
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_location", listViewLocation[i]);
            hm.put("listview_image", Integer.toString(listViewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_location", "listview_image"};
        int[] to = {R.id.listview_location, R.id.listview_image};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_activity, from, to);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(simpleAdapter);


    }


   public void collectAllData() {
        AccessData access = new AccessData();
        data = access.accessData();
    }


    /**
     * Creates tabs for each object. Needs functionality for constant scrolling.
     */
   public void createTabs() {
        // updateUI to erase previous tabs
        // Create table layout
        for (int i = 0; i < data.size(); i++) {
            bikeTabs.add(new BikeTab(data.get(i), docIDs.get(i)));
        }
   }

}