package com.example.bakingapp;

import android.app.admin.DevicePolicyManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Recipe> list = new ArrayList<>();
    MyAdapter myAdapter ;
    RecyclerView recyclerView ;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        new fetchData().execute();
        adView=findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        adView.loadAd(adRequest);

        if (getResources().getBoolean(R.bool.landescabe)){
            Log.v("landscabe",String.valueOf(getResources().getBoolean(R.bool.landescabe)));
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        boolean tablet = (getResources().getConfiguration().screenLayout&
                 Configuration.SCREENLAYOUT_LAYOUTDIR_MASK )>= Configuration.SCREENLAYOUT_SIZE_XLARGE;
            Log.v("tablet", String.valueOf(tablet));





        recyclerView= findViewById(R.id.recycleview);

        myAdapter = new MyAdapter(this,list);

LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(myAdapter);




    }




    private class fetchData extends AsyncTask<Void,Void, List<Recipe>> {
        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            try {
                String json = new Json().connection();
                list.addAll(new Json().getData(json));

                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            Log.v("recipesLenght",String.valueOf(list.size()));

             myAdapter.notifyDataSetChanged();

        }
    }

}
