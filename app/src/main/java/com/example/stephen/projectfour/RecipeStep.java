package com.example.stephen.projectfour;

public class RecipeStep {

    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;
    public int id;

    public RecipeStep(String shortDescription, String description,
                      String videoUrl, String thumbnailUrl, int id){
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoUrl;
        this.thumbnailURL = thumbnailUrl;
        this.id  = id;
    }
}