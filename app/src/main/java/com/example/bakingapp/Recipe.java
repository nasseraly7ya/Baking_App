package com.example.bakingapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
public class Recipe implements Parcelable {

    private String name;
    private int id;
    List<Steps> steps;

    protected Recipe(Parcel in) {
        name = in.readString();
        id = in.readInt();
        steps = in.createTypedArrayList(Steps.CREATOR);
        ingredients = in.createTypedArrayList(Ingredients.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    List<Ingredients> ingredients;


    public Recipe(String name, int id,List<Steps> steps,List<Ingredients> ingredients) {
        this.name = name;
        this.id = id;
        this.steps = steps;
        this.ingredients = ingredients;


    }






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeTypedList(steps);
        dest.writeTypedList(ingredients);
    }
    public String getIngedient(){
        int i =0;
        String text ="";
        while (i<ingredients.size()) {
            String s="ingredient : "+ingredients.get(i).ingredient+"\n"+
                    "Quantity : " +ingredients.get(i).quantity +"  ,  "
                    +"measure : "+ ingredients.get(i).measure ;
           text=text+"\n"+s;
            i++;
        }
        return text;
    }
}



