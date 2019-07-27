package com.example.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ListItems> {
    List<Steps> steps;
    Context context;
    List<String>names;
    public int pos;
     List<Ingredients> ingredients;
     FragmentManager fragmentManager;
     int id;


    public StepsAdapter(Context context, List<Steps> steps, List<String> names, List<Ingredients> ingredients, FragmentManager supportFragmentManager,int id) {
        this.context=context;
        this.steps = steps;
        this.names = names;
        this.ingredients=ingredients;
        this.fragmentManager = supportFragmentManager;
        this.id=id;

    }




    @NonNull
    @Override
    public ListItems onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layInt = R.layout.grid_layout;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(layInt,viewGroup,false);

        return new ListItems(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItems listItems, int i) {
        Log.v("PosADP",String.valueOf(names.size()));
if(i==0){
    listItems.name.setText("Ingredients");
}else {
    listItems.name.setText(steps.get(i).getShortDescription());
}

    }

    @Override
    public int getItemCount() {
        if ( steps == null)
            return 0;


        return steps.size();
    }


    public class ListItems extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView img;
        View prefView;

        public ListItems(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.NameText);
            name.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
            if (context.getResources().getBoolean(R.bool.landescabe)) {
                Bundle bundle = new Bundle();
                if (getAdapterPosition() == 0) {
                    bundle.putParcelableArrayList("ING", (ArrayList<? extends Parcelable>) ingredients);
                    Log.v("INGFO",ingredients.get(2).getQuantity());
                    Fragment ingredientFragment = new IngredientFragment();
                    ingredientFragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, ingredientFragment).commit();
                } else {
                    Fragment stepFragmen = new BlankFragment();
                    Steps step = steps.get(getAdapterPosition()-1);
                    bundle.putParcelable("steps", step);
                    stepFragmen.setArguments(bundle);

                    fragmentManager.beginTransaction().replace(R.id.fragmentFrame, stepFragmen).commit();

                }
            } else {
                Log.v("step", steps.get(getAdapterPosition()).toString());
                Log.v("length,D", String.valueOf(ingredients.size()));


                Intent intent = new Intent(context, StepsActivity.class);
                Steps[] stepsArray = (Steps[]) steps.toArray();
                intent.putParcelableArrayListExtra("Steps", (ArrayList<? extends Parcelable>) steps);
                intent.putParcelableArrayListExtra("ING", (ArrayList<? extends Parcelable>) ingredients);
                intent.putExtra("id",id);
                intent.setAction(String.valueOf(getAdapterPosition()));
                context.startActivity(intent);
                Log.v("VIEWID", String.valueOf(itemView.getId()));





            }
        }
    }
}

