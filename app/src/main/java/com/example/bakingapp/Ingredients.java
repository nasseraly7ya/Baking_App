package com.example.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

    public  class Ingredients implements Parcelable {

        String quantity, measure, ingredient;

        public Ingredients(String quantity, String measure, String ingredient) {
            this.quantity = quantity;
            this.measure = measure;
            this.ingredient = ingredient;
        }

        protected Ingredients(Parcel in) {
            quantity = in.readString();
            measure = in.readString();
            ingredient = in.readString();
        }

        public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
            @Override
            public Ingredients createFromParcel(Parcel in) {
                return new Ingredients(in);
            }

            @Override
            public Ingredients[] newArray(int size) {
                return new Ingredients[size];
            }
        };

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(quantity);
            dest.writeString(measure);
            dest.writeString(ingredient);
        }
    }
