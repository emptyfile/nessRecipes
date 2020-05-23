package com.example.nessrecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecipeListAdapter extends ArrayAdapter<Recipe> {

    private static final String TAG = "RecipeListAdapter";

    Context context;
    int resource;

    public RecipeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Recipe> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final long id = getItem(position).getId();
        String name = getItem(position).getName();
        String ingredients = getItem(position).getIngredients();
        int estimatedTime = getItem(position).getEstimatedTime();
        String category = getItem(position).getCategory();
        String text = getItem(position).getText();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);
        TextView nameTV = (TextView) convertView.findViewById(R.id.listMainTitle);
        TextView categoryTV = (TextView) convertView.findViewById(R.id.listCategory);
        TextView estimatedTimeTV = (TextView) convertView.findViewById(R.id.listEstimatedTime);
        nameTV.setText(name);
        categoryTV.setText(category);
        estimatedTimeTV.setText(formatTime(estimatedTime));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).openRecipe(id);
            }
        });
        return convertView;
    }

    private String formatTime(int estimatedTime) {
        int minutes = estimatedTime;
        int hours = (int)Math.floor(minutes / 60);
        if (hours != 0) {
            minutes = minutes % (hours * 60);
            return String.format("%d ч. %d мин.", hours, minutes);
        }
        return String.format("%d мин.", estimatedTime);
    }
}
