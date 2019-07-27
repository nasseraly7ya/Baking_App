package com.example.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ListItems> {
    List<Recipe> recipes;
    Context context;
    public int pos;





    public MyAdapter(Context context,List<Recipe> recipes) {
        this.context=context;
        this.recipes = recipes;

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
        listItems.name.setText(recipes.get(i).getName());
        Log.v("element "+String.valueOf(i)+" : ",recipes.get(i).getName());

    }

    @Override
    public int getItemCount() {
        if ( recipes == null)
            return 0;


        return recipes.size();
    }


    public class ListItems extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;

        public ListItems(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.NameText);
            name.setOnClickListener(this);


        }



        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context,RecipeDetail.class);
            Log.v("lenthadp",String.valueOf(recipes.size()));
            intent.putParcelableArrayListExtra("Recipe", (ArrayList<? extends Parcelable>) recipes);
            Recipe recipe = recipes.get(getAdapterPosition());
            context.getSharedPreferences(context.getString(R.string.appwidget_text), Context.MODE_PRIVATE).
                    edit().putString(context.getString(R.string.appwidget_text),"kjklj").apply();
            intent.setAction(String.valueOf(getAdapterPosition()));
            context.startActivity(intent);



        }
    }

}

