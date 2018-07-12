package com.example.stephen.projectfour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class StepDetailActivity extends AppCompatActivity {

    public final String INTENT_KEY = "output";
    public ArrayList<String> mOutputList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SavedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            int index = getIntent().getExtras().getInt(ItemDetailFragment.ARG_INDEX);
            String pane = getIntent().getExtras().getString(ItemDetailFragment.ARG_PANE);
            mOutputList = getIntent().getExtras().getStringArrayList(ItemDetailFragment.ARG_OUTPUT);
            Bundle arguments = new Bundle();
            arguments.putInt(ItemDetailFragment.ARG_INDEX, index);
            arguments.putString(ItemDetailFragment.ARG_PANE, pane);
            arguments.putStringArrayList(ItemDetailFragment.ARG_OUTPUT, mOutputList);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Send the user back to the list of steps.
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
