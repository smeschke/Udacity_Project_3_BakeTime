package com.example.stephen.projectfour;

public class Recipe {
    public int id;
    public int servings;
    public String image;
    public String name;


    public Recipe(int id, String name, String ingredients, String steps,
                  int servings, String image){
        this.id = id;
        this.servings = servings;
        this.image = image;
        this.name = name;
    }
}
