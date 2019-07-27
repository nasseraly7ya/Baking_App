package com.example.bakingapp;


import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetail extends AppCompatActivity implements View.OnClickListener {
    private int pos;
    private List<Ingredients> ingredients=new ArrayList<>();
    private List<Steps> steps = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private StepsAdapter mAdapter;
    private RecyclerView mRecycle ;
    Button prev , next;
    int lenth ;
    boolean wedgit=false;

    public boolean isWedgit() {
        return wedgit;
    }

    public String getING() {

        return recipe.getIngedient();
    }

    TextView recipeName;
    List<Recipe> recipes;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        wedgit=true;
         recipes =  getIntent().getParcelableArrayListExtra("Recipe");

        pos=Integer.valueOf(getIntent().getAction());
        recipe=recipes.get(pos);
        Log.v("ING",recipe.getIngedient());

        Intent intent = new Intent(this,WidgetProvider.class);
        intent.setAction("update");
        intent.putExtra("update",recipe.getIngedient());
        startService(intent);

        steps.addAll(recipe.getSteps());
        ingredients.addAll(recipe.getIngredients());
        Log.v("INGlenth",String.valueOf(recipe.getIngredients().size()));
        lenth=Integer.valueOf(getIntent().getAction())-1;

        if (getResources().getBoolean(R.bool.landescabe)){
            Log.v("landscabe",String.valueOf(getResources().getBoolean(R.bool.landescabe)));
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        Log.v("id",String.valueOf(recipe.getId()));

        names.add("n");
        names.add("d");
        names.add("g");
        Log.v("name",recipe.getName());
        mRecycle =findViewById(R.id.stepsRecycle);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mRecycle.setLayoutManager(manager);
        int id=recipe.getId();
        mAdapter = new StepsAdapter(this,steps,names,ingredients,getSupportFragmentManager(),id);
        mRecycle.setAdapter(mAdapter);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();

        FrameLayout frameLayout = findViewById(R.id.fragmentFrame);
        if(frameLayout != null){

            Log.v("abletFragmet","true");
            while (ingredients.size() == 0){

            }
            bundle.putParcelableArrayList("ING", (ArrayList<? extends Parcelable>) ingredients);
            Fragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.fragmentFrame, ingredientFragment).commit();

        }else {
            prev = findViewById(R.id.prevRecipe);
            next = findViewById(R.id.nextRecipe);
            recipeName = findViewById(R.id.recipeName);
            recipeName.setText(recipe.getName());
            prev.setOnClickListener(this);
            next.setOnClickListener(this);
        }
        }

    @Override
    public void onClick(View v) {

        if (v==prev){
            if (pos == 0){
                pos=recipes.size()-1;
            }else {
                pos--;
            }
            recipe=recipes.get(pos);
            recipeName.setText(recipe.getName());
            steps.clear();
ingredients.clear();
            steps.addAll(recipe.getSteps());
            Intent intent = new Intent(this,WidgetProvider.class);
            intent.setAction("update");
            intent.putExtra("update",recipe.getIngedient());
            startService(intent);
            ingredients.addAll(recipe.getIngredients());
            mAdapter.notifyDataSetChanged();

        }else {
            if (pos==recipes.size()-1){
                pos=0;
            }else {
                pos++;
            }
            recipe=recipes.get(pos);
            recipeName.setText(recipe.getName());
            steps.clear();
            ingredients.clear();
            steps.addAll(recipe.getSteps());
            ingredients.addAll(recipe.getIngredients());
            Intent intent = new Intent(this,WidgetProvider.class);
            intent.setAction("update");
            intent.putExtra("update",recipe.getIngedient());
            startService(intent);
        mAdapter.notifyDataSetChanged();}
    }


    public class asynkTask extends AsyncTask<Integer,Void,Void>{


        @Override
        protected Void doInBackground(Integer... ids) {

            Json json = new Json();
            try {
                ingredients.addAll(json.getIngredients(json.connection(),ids[0]));
                steps.addAll(json.getSteps(json.connection(), ids[0]));

                Log.v("length,D",String.valueOf(ingredients.size()));
                Log.v("length,D",String.valueOf(steps.size()));

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            names.clear();
            if (steps == null || steps.size()!= 0) {
                int i=0;
                names.add("Ingrediant");

                while (i<steps.size()){
                    names.add(steps.get(i).getShortDescription());
                    i++;
                }

                Log.v("length,D", String.valueOf(ingredients.size()));
                mAdapter.notifyDataSetChanged();
                cancel(true);
            }else error();
            

        }
    }

    private static void error() {


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
