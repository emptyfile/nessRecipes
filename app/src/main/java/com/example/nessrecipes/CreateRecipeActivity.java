package com.example.nessrecipes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class CreateRecipeActivity extends Activity {

    DatabaseHelper myDB;
    EditText nameTextField;
    EditText ingredientsTextField;
    EditText estimatedTimeTextField;
    EditText categoryTextField;
    EditText textTextField;
    Button addRecipeButton;
    Button viewAllButton;
    boolean isEditing;
    long recipeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_rercipe);
        myDB = new DatabaseHelper(this);

        nameTextField = (EditText) findViewById(R.id.nameTextField);
        ingredientsTextField = (EditText) findViewById(R.id.ingredientsTextField);
        estimatedTimeTextField = (EditText) findViewById(R.id.estimatedTimeTextField);
        categoryTextField = (EditText) findViewById(R.id.categoryTextField);
        textTextField = (EditText) findViewById(R.id.textTextField);
        addRecipeButton = (Button) findViewById(R.id.addRecipeButton);
        viewAllButton = (Button) findViewById(R.id.viewAllButton);
        addData();
        viewAll();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            onAddingNewRecipe();
        } else {
            onEditingRecipe(bundle);
        }
    }

    private void onEditingRecipe(Bundle bundle) {
        isEditing = true;
        recipeId = bundle.getLong("recipeId");
        Cursor recipeCursor = myDB.getById(recipeId);
        if (recipeCursor.getCount() == 0) {
            onBackPressed();
            finish();
        }
        if (recipeCursor.moveToNext()) {
            recipeId = Long.parseLong(recipeCursor.getString(0));
            nameTextField.setText(recipeCursor.getString(1));
            ingredientsTextField.setText(recipeCursor.getString(2));
            estimatedTimeTextField.setText(recipeCursor.getString(3));
            categoryTextField.setText(recipeCursor.getString(4));
            textTextField.setText(recipeCursor.getString(5));
            addRecipeButton.setText(R.string.edit_recipe_button);
        }
    }

    private void onAddingNewRecipe() {
        clearFields();
        addRecipeButton.setText(R.string.add_recipe_button);
        isEditing = false;
    }

    public void addData() {
        addRecipeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameTextField.getText().toString();
                        String ingredients = ingredientsTextField.getText().toString();
                        int estimatedTime = Integer.parseInt(estimatedTimeTextField.getText().toString());
                        String category = categoryTextField.getText().toString();
                        String text = textTextField.getText().toString();
                        boolean isInserted;
                        if (isEditing)  {
                            isInserted = myDB.updateData(recipeId, name, ingredients, estimatedTime, category, text);
                        } else {
                            isInserted = myDB.insertData(name, ingredients, estimatedTime, category, text);
                        }
                        if (isInserted) {
                            Toast.makeText(CreateRecipeActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            clearFields();
                            goToMainActivity();
                        } else {
                            Toast.makeText(CreateRecipeActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void goToMainActivity() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    private void clearFields() {
        nameTextField.setText("");
        ingredientsTextField.setText("");
        estimatedTimeTextField.setText("");
        categoryTextField.setText("");
        textTextField.setText("");
    }

    public void viewAll() {
        viewAllButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor allData = myDB.getAllData();
                        if (allData.getCount() == 0) {
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (allData.moveToNext()) {
                            buffer.append(String.format("Id :%s\n", allData.getString(0)));
                            buffer.append(String.format("Name :%s\n", allData.getString(1)));
                            buffer.append(String.format("Ingredients :%s\n", allData.getString(2)));
                            buffer.append(String.format("Estimated time :%s\n", allData.getString(3)));
                            buffer.append(String.format("Category :%s\n", allData.getString(4)));
                            buffer.append(String.format("Text :%s\n\n", allData.getString(5)));
                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
