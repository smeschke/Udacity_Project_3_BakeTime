package com.example.stephen.projectfour.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    // Authority --> Which Content Provider to access?
    public static final String AUTHORITY = "com.example.stephen.projectfour";
    // Base content URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    // Paths for accessing data
    public static final String PATH_MOVIES = "recipes";

    public static final class listEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "mylist";
        public static final String COLUMN_TIMESTAMP = "timestamp";

        public static final String COLUMN_RECIPE_NAME = "recipe_name";
        public static final String COLUMN_RECIPE_SERVINGS = "recipe_servings";
        public static final String COLUMN_RECIPE_IMAGE_URL = "recipe_img_url";
        public static final String COLUMN_RECIPE_ID  = "recipe_id";

        public static final String COLUMN_IS_STEP = "is_step";
        public static final String COLUMN_STEP_ID = "step_id";
        public static final String COLUMN_STEP_SHORT_DESCRIPTION = "step_short_description";
        public static final String COLUMN_STEP_VERBOSE_DESCRIPTION = "step_verbose_description";
        public static final String COLUMN_STEP_VIDEO_URL = "step_video_url";
        public static final String COLUMN_STEP_THUMBNAIL_URL = "step_thumbnail_url";

        public static final String COLUMN_IS_INGREDIENT = "is_ingredient";
        public static final String COLUMN_INGREDIENT_ID = "movie_released";
        public static final String COLUMN_INGREDIENT_QUANTITY = "movie_reviews";
        public static final String COLUMN_INGREDIENT_MEASURE = "poster_path";
        public static final String COLUMN_INGREDIENT_NAME = "favorite";
    }
}