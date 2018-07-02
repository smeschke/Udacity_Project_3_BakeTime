package com.example.stephen.projectfour;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.stephen.projectfour.data.Contract;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        mCardAdapter.mAdapterOnClickHandler {

    public mCardAdapter mAdapter;
    public RecyclerView mList;
    public final String LOG_KEY = "log";
    public final String DB_HAS_BEEN_QUERIED_KEY = "dbQueryKey";
    public final int SETTINGS_MODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("LOG", "asdf Onc Create");

        URL url = null;
        try {
            url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        delete_all();

        // Get access to the preferences
        SharedPreferences settings = getApplicationContext().
                getSharedPreferences(LOG_KEY, SETTINGS_MODE);
        SharedPreferences.Editor editor = settings.edit();
        // Check if the DB has been fetched from the TMDB API
        boolean dbHasBeenQueried = settings.getBoolean(DB_HAS_BEEN_QUERIED_KEY, false);

        // If connected, query the DB (only do this once)
        //if (!dbHasBeenQueried) {
        Log.d("LOG", "asdf On Create DB Load");
        editor.putBoolean(DB_HAS_BEEN_QUERIED_KEY, true).commit();
        new fetch().execute(url);
        //}

        //code for recycler view
        mList = (RecyclerView) findViewById(R.id.my_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mList.setLayoutManager(layoutManager);
        mList.setHasFixedSize(true);
    }

    //on user clicks, send to training
    @Override
    public void onClick(int index) {
        Toast.makeText(this, Integer.toString(index), Toast.LENGTH_SHORT).show();
        Intent toDetail = new Intent(this, ItemListActivity.class);
        // Add an output String Array list extra that has all the recipes information
        // This keeps all the DB calls on in the MainActivity
        toDetail.putStringArrayListExtra("output", getOutputString(Integer.toString(index+1)));
        startActivity(toDetail);
    }

    // Fetch recipe data from internet
    class fetch extends AsyncTask<URL, Void, String>

    {
        // Do in background gets the json recipe data from internet
        @Override
        protected String doInBackground(URL... urls) {
            String fetchResults = null;
            try {
                fetchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Return the results to the onPostExecute method
            return fetchResults;
        }

        // On post execute task
        @Override
        protected void onPostExecute(String recipes) {
            // parse out the json
            // Iterate through each recipe and add it to the DB
            ArrayList<String> recipeNames = new ArrayList<>();
            ArrayList<String> recipeServings = new ArrayList<>();
            for (int idx = 0; idx < 4; idx++) {
                List<String> stepIds = JsonUtils.parseStepID(recipes, idx);
                List<String> stepShortDescriptions = JsonUtils.parseStepDescription(recipes, idx);
                List<String> stepVerboseDescription = JsonUtils.parseStepVerboseDescription(recipes, idx);
                List<String> stepVideo = JsonUtils.parseStepVideo(recipes, idx);
                List<String> stepThumbs = JsonUtils.parseStepsThumbnail(recipes, idx);
                List<String> ingredientNames = JsonUtils.parseIngredientNames(recipes, idx);
                List<String> ingredientQuantities = JsonUtils.parseIngredientQuantities(recipes, idx);
                List<String> ingredientMeasures = JsonUtils.parseIngredientMeasures(recipes, idx);
                List<String> attributes = JsonUtils.parseAttributes(recipes, idx);

                insert_recipes(attributes, ingredientNames, ingredientQuantities, ingredientMeasures, stepIds, stepShortDescriptions, stepVerboseDescription, stepVideo, stepThumbs);

                recipeNames.add(attributes.get(0));
                recipeServings.add("Servings: " + attributes.get(2));
            }
            mAdapter = new mCardAdapter(MainActivity.this, MainActivity.this, recipeNames, recipeServings);
            mList.setAdapter(mAdapter);
        }
    }

    private void insert_recipes(List<String> attributes, List<String> ingredientNames,
                                List<String> ingredientQuantities, List<String> ingredientMeasures,
                                List<String> stepIds, List<String> stepShortDescriptions,
                                List<String> stepVerboseDescriptions, List<String> stepVideo,
                                List<String> stepThumbs) {

        // get attributes
        String name = attributes.get(0);
        String id = attributes.get(1);
        String servings = attributes.get(2);
        String image_url = attributes.get(3);


        // Iterate though the steps and put then in the db
        for (int step_index = 0; step_index < stepIds.size(); step_index++) {

            String stepThumbnailUrl = stepThumbs.get(step_index);
            String stepShortDescription = stepShortDescriptions.get(step_index);
            String stepVerboseDescription = stepVerboseDescriptions.get(step_index);
            String stepVideoUrl = stepVideo.get(step_index);
            String stepId = stepIds.get(step_index);

            // check for and fix null values
            if (name.equals("")) name = "broken";
            if (id.equals("")) id = "broken";
            if (image_url.equals("")) image_url = "broken";
            if (servings.equals("")) servings = "broken";

            if (stepThumbnailUrl.equals("")) stepThumbnailUrl = "broken";
            if (stepShortDescription.equals("")) stepShortDescription = "broken";
            if (stepVerboseDescription.equals("")) stepShortDescription = "broken";
            if (stepVideoUrl.equals("")) stepVideoUrl = "broken";
            if (stepId.equals("")) stepId = "broken";

            ContentValues cv = new ContentValues();
            cv.put(Contract.listEntry.COLUMN_RECIPE_NAME, name);
            cv.put(Contract.listEntry.COLUMN_RECIPE_ID, id);
            cv.put(Contract.listEntry.COLUMN_RECIPE_IMAGE_URL, image_url);
            cv.put(Contract.listEntry.COLUMN_RECIPE_SERVINGS, servings);

            cv.put(Contract.listEntry.COLUMN_IS_STEP, "true");
            cv.put(Contract.listEntry.COLUMN_STEP_THUMBNAIL_URL, stepThumbnailUrl);
            cv.put(Contract.listEntry.COLUMN_STEP_SHORT_DESCRIPTION, stepShortDescription);
            cv.put(Contract.listEntry.COLUMN_STEP_VERBOSE_DESCRIPTION, stepVerboseDescription);
            cv.put(Contract.listEntry.COLUMN_STEP_VIDEO_URL, stepVideoUrl);
            cv.put(Contract.listEntry.COLUMN_STEP_ID, stepId);

            cv.put(Contract.listEntry.COLUMN_IS_INGREDIENT, "false");
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_ID, "test");
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_MEASURE, "test");
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_QUANTITY, "test");
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_NAME, "test");

            // Insert the content values via a ContentResolver
            // Is the a database operation on the main thread? Sorry Layla.
            getContentResolver().insert(Contract.listEntry.CONTENT_URI, cv);
            // Tell the user a movie has been saved as favorite
        }
        // Iterate though the ingredients and put then in the db
        for (int idx = 0; idx < ingredientNames.size(); idx++) {


            String ingredientId = Integer.toString(idx);
            String ingredientMeasure = ingredientMeasures.get(idx);
            String ingredientQuantity = ingredientQuantities.get(idx);
            String ingredientName = ingredientNames.get(idx);

            // check for and fix null values
            if (name.equals("")) name = "broken";
            if (id.equals("")) id = "broken";
            if (image_url.equals("")) image_url = "broken";
            if (servings.equals("")) servings = "broken";

            if (ingredientId.equals("")) ingredientId = "broken";
            if (ingredientMeasure.equals("")) ingredientMeasure = "broken";
            if (ingredientName.equals("")) ingredientName = "broken";
            if (ingredientQuantity.equals("")) ingredientQuantity = "broken";

            ContentValues cv = new ContentValues();
            cv.put(Contract.listEntry.COLUMN_RECIPE_NAME, name);
            cv.put(Contract.listEntry.COLUMN_RECIPE_ID, id);
            cv.put(Contract.listEntry.COLUMN_RECIPE_IMAGE_URL, image_url);
            cv.put(Contract.listEntry.COLUMN_RECIPE_SERVINGS, servings);

            cv.put(Contract.listEntry.COLUMN_IS_STEP, "false");
            cv.put(Contract.listEntry.COLUMN_STEP_THUMBNAIL_URL, "test");
            cv.put(Contract.listEntry.COLUMN_STEP_SHORT_DESCRIPTION, "test");
            cv.put(Contract.listEntry.COLUMN_STEP_VERBOSE_DESCRIPTION, "test");
            cv.put(Contract.listEntry.COLUMN_STEP_VIDEO_URL, "test");
            cv.put(Contract.listEntry.COLUMN_STEP_ID, "test");

            cv.put(Contract.listEntry.COLUMN_IS_INGREDIENT, "true");
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_ID, ingredientId);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_MEASURE, ingredientMeasure);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_QUANTITY, ingredientQuantity);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_NAME, ingredientName);

            // Insert the content values via a ContentResolver
            // Is the a database operation on the main thread? Sorry Layla.
            getContentResolver().insert(Contract.listEntry.CONTENT_URI, cv);
            // Tell the user a movie has been saved as favorite
        }
    }


    // Returns a Cursor for all the saved favorite movies in the database
    private Cursor getAllRecipes() {
        return getContentResolver().query(Contract.listEntry.CONTENT_URI,
                null, null, null, Contract.listEntry.COLUMN_TIMESTAMP);
    }

    /* This function takes the database and returns one recipe that is nicely formatted.
     *  @param index - the recipe id
     *  @return output - a list of the output to be sent to the detail activity
     */
    public ArrayList<String> getOutputString(String position) {
        // make a list for ingredients and steps
        ArrayList<String> stepsList = new ArrayList<>();
        ArrayList<String> ingredientsList = new ArrayList<>();
        ArrayList<String> outputList = new ArrayList<>();
        boolean recipeAttributesWritten = false;
        Cursor cursor = getAllRecipes();
        Log.d("LOG", "asdf, cursor size: " + Integer.toString(cursor.getCount()));

        // iterate through the db, and if it is an ingredient in this recipe,
        // add it to the ingredients list
        for (int idx = 0; idx < cursor.getCount(); idx++) {
            cursor.moveToPosition(idx);
            String recipeId = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_ID));
            String recipeName = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_NAME));
            String recipeServings = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_SERVINGS));
            String recipeImg = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_IMAGE_URL));

            String ingredientName = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_INGREDIENT_NAME));
            String ingredientQuantity = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_INGREDIENT_QUANTITY));
            String ingredientMeasure = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_INGREDIENT_MEASURE));

            String stepThumb = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_STEP_THUMBNAIL_URL));
            String stepShortDiscription = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_STEP_SHORT_DESCRIPTION));
            String stepVerboseDescription = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_STEP_VERBOSE_DESCRIPTION));
            String stepVideoUrl = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_STEP_VIDEO_URL));

            if (!ingredientName.equals("test") && recipeId.equals(position)) {
                if (!recipeAttributesWritten) {
                    outputList.add(recipeName + "42069" + "Servings: " + recipeServings);
                    recipeAttributesWritten = true;
                }
                ingredientsList.add(ingredientName + " " + ingredientQuantity
                        + " " + ingredientMeasure);
            }
            String stepOutput = "";
            if (!stepShortDiscription.equals("test") && recipeId.equals(position)) {
                stepOutput += stepShortDiscription + "42069"
                        + stepVerboseDescription + "42069"
                        + stepThumb + "42069"
                        + stepVideoUrl;
                stepsList.add(stepOutput);
            }
        }
        String ingredientsString = "";
        for (int idx = 0; idx < ingredientsList.size(); idx++) {
            ingredientsString += "\u2022 " + ingredientsList.get(idx) + "\n";
        }
        outputList.add(ingredientsString);
        for (int idx = 0; idx < stepsList.size(); idx++) {
            outputList.add(stepsList.get(idx));
        }
        return outputList;
    }

    // Delete the movies that are going to be updated
    public void delete_all() {
        // Build uri with the movie json that needs to be deleted
        Uri uri = Contract.listEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath("1").build();
        // This goes to the top part of the 'delete' method in the Provider class,
        // because the paths is len <3.
        getContentResolver().delete(uri, null, null);
    }

}

