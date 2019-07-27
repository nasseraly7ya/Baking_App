package com.example.bakingapp;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Json {

    private String name = "name";
    private static String ingredients = "ingredients";
    private static String Jsonsteps = "steps";
    private static String quantity = "quantity";
    private static String measure = "measure";
    private static String ingredient = "ingredient";
    private static String description = "description";
    private static String videoURL = "videoURL";
    private static String shortDescription = "shortDescription";



    public String connection() throws IOException {
        URL url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        try {
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(10000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();

                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();

                if (hasInput) {
                    String data = scanner.next();
//                    Log.v("DATA : ",data);
                    return data;

                } else return null;


            }
        } catch (IOException e) {
        } finally {
            connection.disconnect();

        }
        return null;

    }

    public List<Recipe> getData(String json) {
        Recipe recipe = null;
        List<Recipe> recipeList = new ArrayList<>();
//        List<Steps> stepsList = new ArrayList<>();

        try {


            JSONArray root = new JSONArray(json);
            int length = root.length() - 1;
            while (length > -1) {
                JSONObject item = root.getJSONObject(length);
                String recipename = item.getString(name);
                int id = item.getInt("id");
                JSONArray ingredients_list = item.getJSONArray(ingredients);
                List<Ingredients> ingredientsList = new ArrayList<>();

                int size = ingredients_list.length() - 1;
                int i = 0;
                while (size > i) {

                    Log.v("itemnum",String.valueOf(i));
                    JSONObject element = ingredients_list.getJSONObject(i);
                    Ingredients ingredients = new Ingredients
                            (element.getString(quantity), element.getString(measure), element.getString(ingredient));
                    Log.v("quantity",ingredients.getQuantity());
                    ingredientsList.add(ingredients);
                    i++;
                }

                JSONArray steps_list = item.getJSONArray(Jsonsteps);
                i = 0;
                size = steps_list.length() - 1;
                ArrayList<Steps> stepsList=new ArrayList<>();

                while (size > i) {
                    Log.v("size : ", String.valueOf(size));

                    JSONObject element = steps_list.getJSONObject(i);

                    Steps steps = null;

                    if (element.has(videoURL) && element.has(description)) {

                        steps = new Steps(element.getString(description),element.getString(shortDescription), element.getString(videoURL));

                    } else if (element.has(description)) {
                        steps = new Steps(element.getString(description),element.getString(shortDescription), null);

                    }
                    stepsList.add(steps);
                    i++;
        }
                    Log.v("jsID", String.valueOf(id));
                    recipe = new Recipe(recipename, id,stepsList,ingredientsList);
                    recipeList.add(recipe);


                    length--;
                }



            return recipeList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ingredients> getIngredients(String json, int id) {

        List<Ingredients> ingredientsList = new ArrayList<>();

        try {


            JSONArray root = new JSONArray(json);
            int length = root.length() - 1;


            JSONObject item = root.getJSONObject(id-1);

            String recipename = item.getString(name);

            Log.v("enterd", "yes");
            JSONArray ingredients_list = item.getJSONArray(ingredients);

            int size = ingredients_list.length() - 1;
            int i = 0;
            while (size > i) {
                JSONObject element = ingredients_list.getJSONObject(i);
                Ingredients ingredients = new Ingredients
                        (element.getString(quantity), element.getString(measure), element.getString(ingredient));
                ingredientsList.add(ingredients);
                i++;
            }




         return ingredientsList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Steps> getSteps(String json, int id){
        List<Steps> stepsList = new ArrayList<>();


        try {


            JSONArray root = new JSONArray(json);
            int length = root.length() - 1;
                JSONObject item = root.getJSONObject(id-1);
                String recipename = item.getString(name);
                    Log.v("enterd","yes");


                    JSONArray steps_list = item.getJSONArray(Jsonsteps);

                    int i = 0;
                    int size = steps_list.length() - 1;
                    while (size > i) {

                            JSONObject element = steps_list.getJSONObject(i);

                            Steps steps ;

                            if (element.has(videoURL)) {
                                steps = new Steps(element.getString(description),element.getString(shortDescription), element.getString(videoURL));

                            }
                           else steps = new Steps(element.getString(description),element.getString(shortDescription),null);
                            stepsList.add(steps);
                            i++;

                    }




            return stepsList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

