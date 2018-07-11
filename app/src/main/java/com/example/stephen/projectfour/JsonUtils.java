package com.example.stephen.projectfour;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<String> parseStepID(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepIds = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipeArray.getString(position));
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(recipe.getString("steps"));
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                //get the json for the individual string
                String step = recipeSteps.getString(idx);
                Gson gson = new Gson();
                recipeStepIds.add(String.valueOf(gson.fromJson(step, RecipeStep.class).id));
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepIds;
    }

    public static List<String> parseStepDescription(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepShortDescriptions = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipeArray.getString(position));
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(recipe.getString("steps"));
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                //get the json for the individual string
                String step = recipeSteps.getString(idx);
                Gson gson = new Gson();
                recipeStepShortDescriptions.add(gson.fromJson(step, RecipeStep.class).shortDescription);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepShortDescriptions;
    }


    public static List<String> parseStepVerboseDescription(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepVerboseDescriptions = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipeArray.getString(position));
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(recipe.getString("steps"));
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                //get the json for the individual string
                String step = recipeSteps.getString(idx);
                Gson gson = new Gson();
                recipeStepVerboseDescriptions.add(gson.fromJson(step, RecipeStep.class).description);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepVerboseDescriptions;
    }

    public static List<String> parseStepVideo(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepVideoUrls = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipeArray.getString(position));
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(recipe.getString("steps"));
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                //get the json for the individual string
                String step = recipeSteps.getString(idx);
                Gson gson = new Gson();
                String videoUrl = gson.fromJson(step, RecipeStep.class).videoURL;
                Log.d("LOG", "asdf videoURL fromJSON UTILS" + videoUrl);
                if (videoUrl == null) videoUrl = "broken";
                recipeStepVideoUrls.add(videoUrl);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepVideoUrls;
    }

    public static List<String> parseStepsThumbnail(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepThumbnails = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipeArray.getString(position));
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(recipe.getString("steps"));
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                //get the json for the individual string
                String step = recipeSteps.getString(idx);
                Gson gson = new Gson();
                String thumb = gson.fromJson(step, RecipeStep.class).thumbnailURL;
                if (thumb == null) thumb = "broken";
                recipeStepThumbnails.add(thumb);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepThumbnails;
    }

    public static List<String> parseIngredientNames(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeIngredientNames = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the json object for an individual recipe
            JSONObject recipe = new JSONObject(recipeArray.getString(position));
            // Get an array for the recipe's ingredients
            JSONArray ingredientsArray = new JSONArray(recipe.getString("ingredients"));
            // Iterate though the ingredients
            for (int idx = 0; idx < ingredientsArray.length(); idx++) {
                // Get an ingredient (contains qty, measure, and ingredient)
                String ingredient = ingredientsArray.getString(idx);
                Gson gson = new Gson();
                recipeIngredientNames.add(gson.fromJson(ingredient, RecipeIngredient.class).ingredient);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing ingredients json data in JsonUtils");
        }
        return recipeIngredientNames;
    }

    public static List<String> parseIngredientQuantities(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeIngredientQuantities = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the json object for an individual recipe
            JSONObject recipe = new JSONObject(recipeArray.getString(position));
            // Get an array for the recipe's ingredients
            JSONArray ingredientsArray = new JSONArray(recipe.getString("ingredients"));
            // Iterate though the ingredients
            for (int idx = 0; idx < ingredientsArray.length(); idx++) {
                // Get an ingredient (contains qty, measure, and ingredient)
                String ingredient = ingredientsArray.getString(idx);
                Gson gson = new Gson();
                recipeIngredientQuantities.add(gson.fromJson(ingredient, RecipeIngredient.class).quantity);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing ingredients json data in JsonUtils");
        }
        return recipeIngredientQuantities;
    }

    public static List<String> parseIngredientMeasures(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeIngredientMeasures = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the json object for an individual recipe
            JSONObject recipe = new JSONObject(recipeArray.getString(position));
            // Get an array for the recipe's ingredients
            JSONArray ingredientsArray = new JSONArray(recipe.getString("ingredients"));
            // Iterate though the ingredients
            for (int idx = 0; idx < ingredientsArray.length(); idx++) {
                // Get an ingredient (contains qty, measure, and ingredient)
                String ingredient = ingredientsArray.getString(idx);
                Gson gson = new Gson();
                recipeIngredientMeasures.add(gson.fromJson(ingredient, RecipeIngredient.class).measure);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing ingredients json data in JsonUtils");
        }
        return recipeIngredientMeasures;
    }

    public static List<String> parseAttributes(String recipes, int i) {
        List<String> recipeAttributes = new ArrayList<>();
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the JSON object for the individual recipe
            JSONObject recipe = new JSONObject(recipeArray.getString(i));
            Gson gson = new Gson();
            Recipe recipeObject = gson.fromJson(recipe.toString(), Recipe.class);
            recipeAttributes.add(recipeObject.name);
            recipeAttributes.add(String.valueOf(recipeObject.id));
            recipeAttributes.add(String.valueOf(recipeObject.servings));
            recipeAttributes.add(recipeObject.image);
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing attributes json data in JsonUtils");
        }
        return recipeAttributes;
    }
}