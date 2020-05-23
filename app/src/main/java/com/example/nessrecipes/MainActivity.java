package com.example.nessrecipes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.recipeListView);
        fillInListView(listView);
        //check if null
    }

    @Override
    protected void onStart() {
        super.onStart();
        fillInListView(listView);
    }

    private void fillInListView(ListView recipeListView) {
        ArrayList<Recipe> recipes = getAllRecipes();
        RecipeListAdapter adapter = new RecipeListAdapter(this, R.layout.list_row, recipes);
        recipeListView.setAdapter(adapter);
    }

    private ArrayList<Recipe> getAllRecipes() {
        Cursor allData = myDB.getAllData();
        if (allData.getCount() == 0) {
            return null;
        }
        ArrayList<Recipe> recipes = new ArrayList<>();
        while (allData.moveToNext()) {
            getRecipeFromCursor(allData, recipes);
        }
        return recipes;
    }

    private void getRecipeFromCursor(Cursor data, ArrayList<Recipe> recipes) {
        Recipe recipe = new Recipe();
        recipe.setId(Long.parseLong(data.getString(0)));
        recipe.setName(data.getString(1));
        recipe.setIngredients(data.getString(2));
        recipe.setEstimatedTime(Integer.parseInt(data.getString(3)));
        recipe.setCategory(data.getString(4));
        recipe.setText(data.getString(5));
        recipes.add(recipe);
    }

    public void goToAddNewScreen(View view) {
        Intent createRecipeIntent = new Intent(this, CreateRecipeActivity.class);
        startActivity(createRecipeIntent);
    }

    public void openRecipe(long recipeId) {
        Intent viewRecipeIntent = new Intent(this, RecipeViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("recipeId", recipeId);
        viewRecipeIntent.putExtras(bundle);
        startActivity(viewRecipeIntent);
    }
}

