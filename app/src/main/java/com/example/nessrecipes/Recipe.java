package com.example.nessrecipes;

public class Recipe {

    private long id;

    private String name;

    private String ingredients;

    private int estimatedTime;

    private String category;

    private String text;

    public Recipe() {
    }

    public Recipe(long id, String name, String ingredients, int estimatedTime, String category, String text) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.estimatedTime = estimatedTime;
        this.category = category;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", category='" + category + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
