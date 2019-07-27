package com.example.bakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StepsActivity extends AppCompatActivity implements View.OnClickListener {
    Button prev,next;

    int pos,id;
    BlankFragment stepsFragment = new BlankFragment();
    IngredientFragment ingredientFragment = new IngredientFragment();
     List<Ingredients> ingredients= new ArrayList<>();
     List<Steps> steps = new ArrayList<>();
     long videoPos=0;
     boolean state=false;

    public long getVideoPos() {
        return videoPos;
    }

    public void setVideoPos(long videoPos) {
        this.videoPos = videoPos;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState !=null){
            videoPos=savedInstanceState.getLong("videoPos");
            state=savedInstanceState.getBoolean("state");
            pos=savedInstanceState.getInt("pos");
            Log.v("videoPosSA",String.valueOf(videoPos));

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
//        id=getIntent().getIntExtra("id",0);
//        new asynkTask().execute();



        next = findViewById(R.id.btnNext);
        prev = findViewById(R.id.btnPrev);
        if (next!=null) {
            next.setOnClickListener(this);
            prev.setOnClickListener(this);
        }
        Bundle bundle = new Bundle();
        if (savedInstanceState == null){
        pos= Integer.valueOf(getIntent().getAction())-1;}
        steps = getIntent().getParcelableArrayListExtra("Steps");
        ingredients = getIntent().getParcelableArrayListExtra("ING");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (pos == -1) {
             Log.v("lenthFr",String.valueOf(ingredients.size()));
             if (prev!=null){
            prev.setVisibility(View.GONE);}
            bundle.putParcelableArrayList("ING", (ArrayList<? extends Parcelable>) ingredients);
            ingredientFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fragmentt, ingredientFragment).commit();

        } else {

            stepsFragment.setStepsActivity(this);
            bundle.putParcelable("steps", steps.get(pos));
            bundle.putLong("videoPos",videoPos);
            bundle.putBoolean("state",state);
            stepsFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fragmentt, stepsFragment).commit();


        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        if (v== next){
            prev.setVisibility(View.VISIBLE);
                pos++;
                if (pos==steps.size()-1){
                    next.setVisibility(View.GONE);
                }
            Steps step = steps.get(pos);
            Log.v("StepV",step.getVideoURL());
            BlankFragment stepFragmen = new BlankFragment();

            stepFragmen.setStepsActivity(this);
            bundle.putParcelable("steps", step);
            bundle.putString("pos", getIntent().getAction());
            stepFragmen.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentt,stepFragmen).commit();

        }else {
            pos--;

            if (pos == -1){
                bundle.putParcelableArrayList("ING", (ArrayList<? extends Parcelable>) ingredients);
                ingredientFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentt, ingredientFragment).commit();
                prev.setVisibility(View.INVISIBLE);
            }else {

                next.setVisibility(View.VISIBLE);
                stepsFragment = new BlankFragment();
                Steps step = steps.get(pos);
                bundle.putParcelable("steps", step);
                bundle.putString("pos", getIntent().getAction());
                stepsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentt, stepsFragment).commit();
            }

        }

    }
        public class asynkTask extends AsyncTask<Void,Void,Void> {


            @Override
            protected Void doInBackground(Void... voids) {
                Json json = new Json();
                try {
                    ingredients.addAll(json.getIngredients(json.connection(), id));
                    steps.addAll(json.getSteps(json.connection(), id));

                    Log.v("length,D",String.valueOf(ingredients.size()));
                    Log.v("length,D",String.valueOf(steps.size()));

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }


        }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong("videoPos",stepsFragment.getVideoPos());
        outState.putBoolean("state",stepsFragment.getState());
        outState.putInt("pos",pos);
        Log.v("state",String.valueOf(stepsFragment.getState()));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        Log.v("VIDEO",String.valueOf(videoPos));

        super.onStop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toHome();
        return true;
    }

    public void toHome(){
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
