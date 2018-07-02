package com.example.stephen.projectfour;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements
        View.OnClickListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "item_test";
    public static final String ARG_INDEX = "item_index";
    public static final String ARG_INGREDIENTS = "item_ingredients";
    public String mData;
    public int mIndex;
    public String mIngredients;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = getArguments().getString(ARG_ITEM);
        mIndex = getArguments().getInt(ARG_INDEX) - 1;
        mIngredients = getArguments().getString(ARG_INGREDIENTS);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mIndex == 0) {

            View rootView = inflater.inflate(R.layout.item_detail_index_zero, container, false);
            mPlayerView = rootView.findViewById(R.id.playerViewRecipe);
            // Load the question mark as the background image until the user answers the question.
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.question_mark));
            // Initialize the player.
            initializePlayer(Uri.parse("broken"));
            String[] string_list = mData.split("42069");
            String text = string_list[0] + "\n\n" + string_list[1];
            ((TextView) rootView.findViewById(R.id.item_detail_recipe)).setText(text);

            Button button = (Button) rootView.findViewById(R.id.addButton);
            button.setOnClickListener(this);
            return rootView;
        } else {
            View rootView = inflater.inflate(R.layout.item_detail, container, false);

            String text = "";
            String videoUri = "broken";
            String[] string_list = mData.split("42069");
            //if the index is zero, this is the title, so show the recipe name and servings only
            if (mIndex == 0) text = string_list[0] + "\n\n" + string_list[1];
            //if the index is 1, show the ingredients list
            if (mIndex == 1) text = mData;
            //if the index is > 1, it is a step so initialize the player and
            if (mIndex > 1) {
                text = string_list[1];
                videoUri = string_list[3];
            }

            mPlayerView = rootView.findViewById(R.id.playerView);

            // Load the question mark as the background image until the user answers the question.
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.question_mark));
            // Initialize the player.
            initializePlayer(Uri.parse(videoUri));
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(text);
            return rootView;
        }
    }


    /**
     * Initialize ExoPlayer.
     *
     * This is from the Udacity Exoplayer Lesson by Nikita
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    /*
    *  The onClick method handles the button that adds the ingredients for this
    *  recipe to the widget.
    *
    *  @param mIngredients is already a class variable
    *  @returns nothing, but there is a Toast to the user
    * */
    @Override
    public void onClick(View v) {
        Log.d("LOG", "asdf widget button"+mIngredients);


        // recipe ingredients widget
        String ingredients = mIngredients;
        Context context = getActivity();
        Toast.makeText(context, "Ingredients added to widget", Toast.LENGTH_SHORT).show();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        ComponentName thisWidget = new ComponentName(context, recipe_widget.class);
        remoteViews.setTextViewText(R.id.appwidget_text, ingredients);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
