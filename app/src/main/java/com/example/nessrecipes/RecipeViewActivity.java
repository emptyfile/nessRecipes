package com.example.nessrecipes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class RecipeViewActivity extends Activity {

    DatabaseHelper myDB;
    TextView nameTV;
    TextView categoryTV;
    TextView estimatedTimeTV;
    TextView ingredientsTV;
    TextView textTV;
    long currentRecipeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe);
        myDB = new DatabaseHelper(this);

        nameTV = (TextView) findViewById(R.id.recipeViewName);
        categoryTV = (TextView) findViewById(R.id.recipeViewCategory);
        estimatedTimeTV = (TextView) findViewById(R.id.recipeViewEstimatedTime);
        ingredientsTV = (TextView) findViewById(R.id.recipeViewIngredients);
        textTV = (TextView) findViewById(R.id.recipeViewText);

        fillInRecipeData();
    }

    private void fillInRecipeData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            onBackPressed();
            finish();
        }
        currentRecipeId = bundle.getLong("recipeId");
        Cursor recipeCursor = myDB.getById(currentRecipeId);
        if (recipeCursor.getCount() == 0) {
            showMessage("Nothing found");
            onBackPressed();
            finish();
        }
        if (recipeCursor.moveToNext()) {
            nameTV.setText(recipeCursor.getString(1));
            ingredientsTV.setText(recipeCursor.getString(2));
            estimatedTimeTV.setText(recipeCursor.getString(3));
            categoryTV.setText(recipeCursor.getString(4));
            textTV.setText(recipeCursor.getString(5));
        }
    }

    public void showMessage(String message) {
        Toast.makeText(RecipeViewActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void goToEditScreen(View view) {
        Intent editRecipeIntent = new Intent(this, CreateRecipeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("recipeId", currentRecipeId);
        editRecipeIntent.putExtras(bundle);
        startActivity(editRecipeIntent);
    }
}
