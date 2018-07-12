package com.example.stephen.projectfour;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.example.stephen.projectfour.data.Contract;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        mCardAdapter.mAdapterOnClickHandler {

    public mCardAdapter mAdapter;
    public RecyclerView mList;
    public final String LOG_KEY = "log";
    public final String DATA_HAS_BEEN_DOWNLAODED = "dbQueryKey";
    public final int SETTINGS_MODE = 0;
    public final int LOADER_ID = 42;
    public Cursor mCursor;
    private RecyclerView.LayoutManager mLayoutManager;
    public String DELIMITER;
    public final String INTENT_KEY = "output";
    public final int COLUMN_SIZE = 400;
    public final String NOT_USED = "value_not_used";
    public final String IS_TRUE = "true";
    public final String BROKEN = "broken";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the delimiter from strings
        DELIMITER = getResources().getString(R.string.delimiter);
        /*
         *   INFORMATION FLOW:
         *   1. The first time the app is run the JSON data is download, parsed,  and saved in a DB.
         *   2. On subsequent loads, the recipe data is quered from the DB.
         * */
        URL url = null;
        try {
            url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // Get access to the preferences
        SharedPreferences settings = getApplicationContext().getSharedPreferences(LOG_KEY, SETTINGS_MODE);
        SharedPreferences.Editor editor = settings.edit();
        // Check if the recipe data has been fetched from the website
        boolean dataAcquired = settings.getBoolean(DATA_HAS_BEEN_DOWNLAODED, false);
        // If connected, query the DB (only do this once)
        if (!dataAcquired && is_connected()) {
            // Update the shared preferences 
            editor.putBoolean(DATA_HAS_BEEN_DOWNLAODED, true).commit();
            delete_all(); // Clear the db
            new fetch().execute(url);
        }
        // If there is no connection, tell the user to connect
        if (!is_connected()) {
            //Toast.makeText(this, getResources().getString(R.string.no_connection),Toast.LENGTH_LONG).show();
            // Reviewer Suggestion: use snackbar instead of toast.
            // https://stackoverflow.com/questions/30978457/how-to-show-snackbar-when-activity-starts
            View parentLayout = findViewById(android.R.id.content);
            String text = getResources().getString(R.string.no_connection);
            Snackbar.make(parentLayout, text, Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .setDuration(72727)
                    .show();
        }

        // Calculate the number of columns for the gridview
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels / displayMetrics.scaledDensity;
        int cols = 1;
        while (width > COLUMN_SIZE) {
            cols += 1;
            width -= COLUMN_SIZE;
        }
        // Code for recycler view
        mList = findViewById(R.id.my_recycler_view);
        mLayoutManager = new GridLayoutManager(this, cols);
        mList.setLayoutManager(mLayoutManager);
        mAdapter = new mCardAdapter(MainActivity.this, MainActivity.this);
        mList.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    // Is there an internet connection?
    public boolean is_connected() {
        //https://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Objects.requireNonNull(connectivityManager).getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    ///////////////////////////////////START CURSOR LOADER METHODS /////////////////////////////////
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this,
                Contract.listEntry.CONTENT_URI,
                null,
                null,
                null,
                Contract.listEntry.COLUMN_TIMESTAMP);
    }

    // When loading is finished, swap in the new data
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        mAdapter.swapCursor(data);
    }

    // I don't think the loader ever gets reset.
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
    /////////////////////////////////// END CURSOR LOADER METHODS //////////////////////////////////

    // On user clicks, send to training
    @Override
    public void onClick(int index) {
        Intent toDetail = new Intent(this, ItemListActivity.class);
        toDetail.putStringArrayListExtra(INTENT_KEY, getOutputString(Integer.toString(index + 1)));
        startActivity(toDetail);
    }

    // Reviewer suggested moving this into a separate class, but I couldn't figure out how to make that work.
    ///////////////////////////////// START RECIPE DATA FETCH TASK /////////////////////////////////
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
            // Iterate though the list of recipes and add them to the DB
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
            }
        }
    }
    ///////////////////////////////// END RECIPE DATA FETCH TASK ///////////////////////////////////

    ////////////////////////////// START INSERT RECIPE INTO DB TASK ////////////////////////////////
    private void insert_recipes(List<String> attributes, List<String> ingredientNames,
                                List<String> ingredientQuantities, List<String> ingredientMeasures,
                                List<String> stepIds, List<String> stepShortDescriptions,
                                List<String> stepVerboseDescriptions, List<String> stepVideo,
                                List<String> stepThumbs) {
        // Get recipes name, id, servings, and image url
        String name = attributes.get(0);
        String id = attributes.get(1);
        String servings = attributes.get(2);
        String image_url = attributes.get(3);
        // Iterate though the steps and put them in the db
        for (int step_index = 0; step_index < stepIds.size(); step_index++) {
            String stepThumbnailUrl = stepThumbs.get(step_index);
            String stepShortDescription = stepShortDescriptions.get(step_index);
            String stepVerboseDescription = stepVerboseDescriptions.get(step_index);
            String stepVideoUrl = stepVideo.get(step_index);
            String stepId = stepIds.get(step_index);
            // Check for and fix null values
            if (name.equals("")) name = BROKEN;
            if (id.equals("")) id = BROKEN;
            if (image_url.equals("")) image_url = BROKEN;
            if (servings.equals("")) servings = BROKEN;
            if (stepThumbnailUrl.equals("")) stepThumbnailUrl = BROKEN;
            if (stepShortDescription.equals("")) stepShortDescription = BROKEN;
            if (stepVerboseDescription.equals("")) stepShortDescription = BROKEN;
            if (stepVideoUrl.equals("")) stepVideoUrl = BROKEN;
            if (stepId.equals("")) stepId = BROKEN;
            ContentValues cv = new ContentValues();
            cv.put(Contract.listEntry.COLUMN_RECIPE_NAME, name);
            cv.put(Contract.listEntry.COLUMN_RECIPE_ID, id);
            cv.put(Contract.listEntry.COLUMN_RECIPE_IMAGE_URL, image_url);
            cv.put(Contract.listEntry.COLUMN_RECIPE_SERVINGS, servings);
            cv.put(Contract.listEntry.COLUMN_IS_STEP, IS_TRUE);
            cv.put(Contract.listEntry.COLUMN_STEP_THUMBNAIL_URL, stepThumbnailUrl);
            cv.put(Contract.listEntry.COLUMN_STEP_SHORT_DESCRIPTION, stepShortDescription);
            cv.put(Contract.listEntry.COLUMN_STEP_VERBOSE_DESCRIPTION, stepVerboseDescription);
            cv.put(Contract.listEntry.COLUMN_STEP_VIDEO_URL, stepVideoUrl);
            cv.put(Contract.listEntry.COLUMN_STEP_ID, stepId);
            cv.put(Contract.listEntry.COLUMN_IS_INGREDIENT, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_ID, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_MEASURE, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_QUANTITY, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_NAME, NOT_USED);
            // Insert the content values via a ContentResolver
            getContentResolver().insert(Contract.listEntry.CONTENT_URI, cv);
        }
        // Iterate though the ingredients and put then in the db
        for (int idx = 0; idx < ingredientNames.size(); idx++) {
            String ingredientId = Integer.toString(idx);
            String ingredientMeasure = ingredientMeasures.get(idx);
            String ingredientQuantity = ingredientQuantities.get(idx);
            String ingredientName = ingredientNames.get(idx);
            // Check for and fix null values
            if (name.equals("")) name = BROKEN;
            if (id.equals("")) id = BROKEN;
            if (image_url.equals("")) image_url = BROKEN;
            if (servings.equals("")) servings = BROKEN;
            if (ingredientId.equals("")) ingredientId = BROKEN;
            if (ingredientMeasure.equals("")) ingredientMeasure = BROKEN;
            if (ingredientName.equals("")) ingredientName = BROKEN;
            if (ingredientQuantity.equals("")) ingredientQuantity = BROKEN;
            ContentValues cv = new ContentValues();
            cv.put(Contract.listEntry.COLUMN_RECIPE_NAME, name);
            cv.put(Contract.listEntry.COLUMN_RECIPE_ID, id);
            cv.put(Contract.listEntry.COLUMN_RECIPE_IMAGE_URL, image_url);
            cv.put(Contract.listEntry.COLUMN_RECIPE_SERVINGS, servings);
            cv.put(Contract.listEntry.COLUMN_IS_STEP, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_STEP_THUMBNAIL_URL, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_STEP_SHORT_DESCRIPTION, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_STEP_VERBOSE_DESCRIPTION, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_STEP_VIDEO_URL, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_STEP_ID, NOT_USED);
            cv.put(Contract.listEntry.COLUMN_IS_INGREDIENT, IS_TRUE);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_ID, ingredientId);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_MEASURE, ingredientMeasure);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_QUANTITY, ingredientQuantity);
            cv.put(Contract.listEntry.COLUMN_INGREDIENT_NAME, ingredientName);
            // Insert the content values via a ContentResolver
            getContentResolver().insert(Contract.listEntry.CONTENT_URI, cv);
        }
    }
    /////////////////////////////// END INSERT RECIPE INTO DB TASK ////////////////////////////////


    // Returns a Cursor for all the saved recipes in the database
    private Cursor getAllRecipes() {
        return getContentResolver().query(Contract.listEntry.CONTENT_URI,
                null, null, null, Contract.listEntry.COLUMN_TIMESTAMP);
    }

    /*
     *  This function takes the database and returns one recipe that is nicely formatted.
     *  @param index - the recipe id
     *  @return output - a list of the output to be sent to the detail activity
     */
    public ArrayList<String> getOutputString(String position) {
        // Make a list for ingredients and steps
        ArrayList<String> stepsList = new ArrayList<>();
        ArrayList<String> ingredientsList = new ArrayList<>();
        ArrayList<String> outputList = new ArrayList<>();
        boolean recipeAttributesWritten = false;
        Cursor cursor = getAllRecipes();
        /*
         *  Iterate through the db, and if it is an ingredient in this recipe,
         *  add it to the ingredients list.
         */
        for (int idx = 0; idx < cursor.getCount(); idx++) {
            cursor.moveToPosition(idx);
            String recipeId = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_ID));
            String recipeName = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_NAME));
            String recipeServings = cursor.getString(cursor.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_SERVINGS));
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
            // If (1) this is an ingredient and (2) the ingredient belongs to this recipe
            if (!ingredientName.equals(NOT_USED) && recipeId.equals(position)) {
                // Add the # servings to the list (only once)
                if (!recipeAttributesWritten) {
                    outputList.add(recipeName + DELIMITER +
                            getResources().getString(R.string.servings)+ recipeServings);
                    recipeAttributesWritten = true;
                }
                // Add the ingredients to the ingredientsList
                ingredientsList.add(ingredientName + " " + ingredientQuantity
                        + " " + ingredientMeasure);
            }
            String stepOutput = "";
            // If (1) this is a step, and (2) the step belongs to this recipe
            if (!stepShortDiscription.equals(NOT_USED) && recipeId.equals(position)) {
                stepOutput += stepShortDiscription + DELIMITER
                        + stepVerboseDescription + DELIMITER
                        + stepThumb + DELIMITER
                        + stepVideoUrl;
                // Add the step to the steps list
                stepsList.add(stepOutput);
            }
        }
        // Make the list of ingredients into a nice string with a buttleted list
        String ingredientsString = "";
        for (int idx = 0; idx < ingredientsList.size(); idx++) {
            ingredientsString += "\u2022 " + ingredientsList.get(idx) + "\n";
        }
        // Add the ingredients to the output list
        outputList.add(ingredientsString);
        // Add the steps to the output list
        for (int idx = 0; idx < stepsList.size(); idx++) {
            outputList.add(stepsList.get(idx));
        }
        // Return a list that contains all the information about a recipe
        return outputList;
    }

    // Delete everything in the database
    public void delete_all() {
        // Build uri with the movie json that needs to be deleted
        Uri uri = Contract.listEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath("1").build();
        getContentResolver().delete(uri, null, null);
    }
}