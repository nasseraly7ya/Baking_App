package com.example.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;



public class IngredientFragment extends Fragment {

    private ArrayList<Ingredients> ingredients;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ingredient, container, false);
        TextView textView = view.findViewById(R.id.ingrediantTextFragment);
        Bundle bundle = getArguments();
        int pos = bundle.getInt("pos",0);
        if (pos == 0){
            ingredients=bundle.getParcelableArrayList("ING");
            int i =0;
            while (i<ingredients.size()) {
                textView.append("ingredient : "+ingredients.get(i).ingredient+"\n"+
                        "Quantity : " +ingredients.get(i).quantity +"  ,  "
                        +"measure : "+ ingredients.get(i).measure );
                if (i <ingredients.size()-1) {
                    textView.append("\n"+"\n");

                }
                i++;
            }

        }
        return view;
    }


}
