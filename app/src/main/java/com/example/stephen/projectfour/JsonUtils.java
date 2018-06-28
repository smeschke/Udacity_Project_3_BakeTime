package com.example.stephen.projectfour;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    /*
     *  Takes json data from internet and returns list of steps
     *  @param recipes  - json string containing all the recipes
     *  @param position - integer position of the desired recipe steps
     *  @return a string list containing the steps for the recipe
     */
    public static List<String> parseStepID(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepsList = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the string for the JSON for an individual recipe
            String recipe_json = recipeArray.getString(position);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipe_json);
            // Get the string for the steps
            String steps = recipe.getString("steps");
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(steps);
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                String step = recipeSteps.getString(idx);
                // Get the JSON object for the step
                JSONObject stepObject = new JSONObject(step);
                // Get the id for the step
                String stepId = stepObject.getString("id");
                // Get the short description
                String stepShortDescription = stepObject.getString("shortDescription");
                // Get the verbose description
                String stepVerboseDescription = stepObject.getString("description");
                // Get the videoURL
                String stepVideoUrl = stepObject.getString("videoURL");
                // Get the thumbnailURL
                String stepThumbnailUrl = stepObject.getString("thumbnailURL");
                // Add the description to the list
                recipeStepsList.add(stepId);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepsList;
    }

    public static List<String> parseStepDescription(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepsList = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the string for the JSON for an individual recipe
            String recipe_json = recipeArray.getString(position);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipe_json);
            // Get the string for the steps
            String steps = recipe.getString("steps");
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(steps);
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                String step = recipeSteps.getString(idx);
                // Get the JSON object for the step
                JSONObject stepObject = new JSONObject(step);
                // Get the id for the step
                String stepId = stepObject.getString("id");
                // Get the short description
                String stepShortDescription = stepObject.getString("shortDescription");
                // Get the verbose description
                String stepVerboseDescription = stepObject.getString("description");
                // Get the videoURL
                String stepVideoUrl = stepObject.getString("videoURL");
                // Get the thumbnailURL
                String stepThumbnailUrl = stepObject.getString("thumbnailURL");
                // Add the description to the list
                recipeStepsList.add(stepShortDescription);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepsList;
    }

    public static List<String> parseStepVerboseDescription(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepsList = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the string for the JSON for an individual recipe
            String recipe_json = recipeArray.getString(position);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipe_json);
            // Get the string for the steps
            String steps = recipe.getString("steps");
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(steps);
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                String step = recipeSteps.getString(idx);
                // Get the JSON object for the step
                JSONObject stepObject = new JSONObject(step);
                // Get the id for the step
                String stepId = stepObject.getString("id");
                // Get the short description
                String stepShortDescription = stepObject.getString("shortDescription");
                // Get the verbose description
                String stepVerboseDescription = stepObject.getString("description");
                // Get the videoURL
                String stepVideoUrl = stepObject.getString("videoURL");
                // Get the thumbnailURL
                String stepThumbnailUrl = stepObject.getString("thumbnailURL");
                // Add the description to the list
                recipeStepsList.add(stepVerboseDescription);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepsList;
    }

    public static List<String> parseStepVideo(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepsList = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the string for the JSON for an individual recipe
            String recipe_json = recipeArray.getString(position);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipe_json);
            // Get the string for the steps
            String steps = recipe.getString("steps");
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(steps);
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                String step = recipeSteps.getString(idx);
                // Get the JSON object for the step
                JSONObject stepObject = new JSONObject(step);
                // Get the id for the step
                String stepId = stepObject.getString("id");
                // Get the short description
                String stepShortDescription = stepObject.getString("shortDescription");
                // Get the verbose description
                String stepVerboseDescription = stepObject.getString("description");
                // Get the videoURL
                String stepVideoUrl = stepObject.getString("videoURL");
                // Get the thumbnailURL
                String stepThumbnailUrl = stepObject.getString("thumbnailURL");
                // Add the description to the list
                recipeStepsList.add(stepVideoUrl);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepsList;
    }

    public static List<String> parseStepsThumbnail(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeStepsList = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the string for the JSON for an individual recipe
            String recipe_json = recipeArray.getString(position);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipe_json);
            // Get the string for the steps
            String steps = recipe.getString("steps");
            // Make the steps into an array
            JSONArray recipeSteps = new JSONArray(steps);
            // Iterate through the steps and get the attributes of each step
            for (int idx = 0; idx < recipeSteps.length(); idx++) {
                String step = recipeSteps.getString(idx);
                // Get the JSON object for the step
                JSONObject stepObject = new JSONObject(step);
                // Get the id for the step
                String stepId = stepObject.getString("id");
                // Get the short description
                String stepShortDescription = stepObject.getString("shortDescription");
                // Get the verbose description
                String stepVerboseDescription = stepObject.getString("description");
                // Get the videoURL
                String stepVideoUrl = stepObject.getString("videoURL");
                // Get the thumbnailURL
                String stepThumbnailUrl = stepObject.getString("thumbnailURL");
                // Add the description to the list
                recipeStepsList.add(stepThumbnailUrl);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing json data in JsonUtils");
        }
        return recipeStepsList;
    }



    public static List<String> parseIngredientNames(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeIngredientsList = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the string for the JSON for an individual recipe
            String recipe_json = recipeArray.getString(position);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipe_json);
            // Get the string for the ingredients
            String recipeIngredients = recipe.getString("ingredients");
            // Make the recipeIngredients into an array
            JSONArray ingredientsArray = new JSONArray(recipeIngredients);
            // Interate though the ingredients
            for (int idx = 0; idx < ingredientsArray.length(); idx++) {
                // Get the first ingredient (contains qty, measure, and ingredient)
                String ingredient = ingredientsArray.getString(idx);
                // Get the json object for the ingredient
                JSONObject ingredientObject = new JSONObject(ingredient);
                // Get the name for the ingredient
                String ingredientName = ingredientObject.getString("ingredient");
                // Get the quantity for the ingredient
                String ingredientQuantity = ingredientObject.getString("quantity");
                // Get the measure type for the ingredient
                String ingredientMeasureType = ingredientObject.getString("measure");
                // Add the ingredient to the list
                recipeIngredientsList.add(ingredientName);
                //Log.d("LOG", "asdf " + ingredientName);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing ingredients json data in JsonUtils");
        }
        return recipeIngredientsList;
    }

    public static List<String> parseIngredientQuantities(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeIngredientsList = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the string for the JSON for an individual recipe
            String recipe_json = recipeArray.getString(position);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipe_json);
            // Get the string for the ingredients
            String recipeIngredients = recipe.getString("ingredients");
            // Make the recipeIngredients into an array
            JSONArray ingredientsArray = new JSONArray(recipeIngredients);
            // Interate though the ingredients
            for (int idx = 0; idx < ingredientsArray.length(); idx++) {
                // Get the first ingredient (contains qty, measure, and ingredient)
                String ingredient = ingredientsArray.getString(idx);
                // Get the json object for the ingredient
                JSONObject ingredientObject = new JSONObject(ingredient);
                // Get the name for the ingredient
                String ingredientName = ingredientObject.getString("ingredient");
                // Get the quantity for the ingredient
                String ingredientQuantity = ingredientObject.getString("quantity");
                // Get the measure type for the ingredient
                String ingredientMeasureType = ingredientObject.getString("measure");
                // Add the ingredient to the list
                recipeIngredientsList.add(ingredientQuantity);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing ingredients json data in JsonUtils");
        }
        return recipeIngredientsList;
    }

    public static List<String> parseIngredientMeasures(String recipes, int position) {
        // Create a new list of steps
        List<String> recipeIngredientsList = new ArrayList<>();
        // Try/Catch for json errors
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the string for the JSON for an individual recipe
            String recipe_json = recipeArray.getString(position);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipe_json);
            // Get the string for the ingredients
            String recipeIngredients = recipe.getString("ingredients");
            // Make the recipeIngredients into an array
            JSONArray ingredientsArray = new JSONArray(recipeIngredients);
            // Interate though the ingredients
            for (int idx = 0; idx < ingredientsArray.length(); idx++) {
                // Get the first ingredient (contains qty, measure, and ingredient)
                String ingredient = ingredientsArray.getString(idx);
                // Get the json object for the ingredient
                JSONObject ingredientObject = new JSONObject(ingredient);
                // Get the name for the ingredient
                String ingredientName = ingredientObject.getString("ingredient");
                // Get the quantity for the ingredient
                String ingredientQuantity = ingredientObject.getString("quantity");
                // Get the measure type for the ingredient
                String ingredientMeasureType = ingredientObject.getString("measure");
                // Add the ingredient to the list
                recipeIngredientsList.add(ingredientMeasureType);
            }
        } catch (Exception e) {
            Log.d("LOG", "asdf error parsing ingredients json data in JsonUtils");
        }
        return recipeIngredientsList;
    }

    public static List<String> parseAttributes(String recipes, int i) {
        List<String> recipeAttributes = new ArrayList<>();
        try {
            // Get an array of all the recipes
            JSONArray recipeArray = new JSONArray(recipes);
            // Get the string for the JSON for an individual recipe
            String recipe_json = recipeArray.getString(i);
            // Make that string into a json object
            JSONObject recipe = new JSONObject(recipe_json);
            // Parse the attributes from the individual recipe
            String recipeName = recipe.getString("name");
            String recipeId = recipe.getString("id");
            String recipeServings = recipe.getString("servings");
            String recipeImage = recipe.getString("image");
            recipeAttributes.add(recipeName);
            recipeAttributes.add(recipeId);
            recipeAttributes.add(recipeServings);
            recipeAttributes.add(recipeImage);
        }catch (Exception e){
            Log.d("LOG", "asdf error parsing attributes json data in JsonUtils");
        }
        return recipeAttributes;
    }
}
