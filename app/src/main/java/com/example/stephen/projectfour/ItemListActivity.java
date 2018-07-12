package com.example.stephen.projectfour;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stephen.projectfour.dummy.DummyContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity was created by selecting new activity --> master/detail flow.
 *
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    ArrayList<String> mOutputList;
    public static final String BROKEN = "broken";
    public final String INTENT_KEY = "output";
    public static final String SINGLE_PANE = "1";
    public static final String TWO_PANE = "2";
    public String DELIMITER;
    private static final String KEY_OUTPUT_STRING = "key_output_string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_steps_list);
        DELIMITER = getResources().getString(R.string.delimiter);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        mOutputList = getIntent().getExtras().getStringArrayList(INTENT_KEY);

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        if (savedInstanceState==null && mTwoPane){
            /*
            *  For these conditions to be met, a user has clicked on a recipe for the first time.
            *  Instead of showing a master list on the left, and an empty detail fragment
            *  on the right, it is better to show the recipe name and ingredients in the fragment.
            * */
            ItemDetailFragment fragment = new ItemDetailFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ItemDetailFragment.ARG_INDEX, 1);
            arguments.putStringArrayList(ItemDetailFragment.ARG_OUTPUT, mOutputList);
            fragment.setArguments(arguments);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList(KEY_OUTPUT_STRING, mOutputList);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
        private final ItemListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                int index = Integer.parseInt(item.id);
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(ItemDetailFragment.ARG_INDEX, index);
                    arguments.putString(ItemDetailFragment.ARG_PANE, TWO_PANE);
                    arguments.putStringArrayList(ItemDetailFragment.ARG_OUTPUT, mOutputList);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StepDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_INDEX, index);
                    intent.putExtra(ItemDetailFragment.ARG_PANE, SINGLE_PANE);
                    intent.putExtra(ItemDetailFragment.ARG_OUTPUT, mOutputList);
                    context.startActivity(intent);
                }
            }
        };
        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (position>0) position += 1;
            String text = split_string(mOutputList.get(position), DELIMITER)[0];
            String img_url = "";
            try{img_url = split_string(mOutputList.get(position), DELIMITER)[2];}
            catch (Exception e){img_url = BROKEN;}
            holder.mContentView.setText(text);
            Picasso.with(getApplicationContext()).load(img_url).error(R.drawable.missing).into(holder.mContentImageView);
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mOutputList.size()-1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;
            final ImageView mContentImageView;
            ViewHolder(View view) {
                super(view);
                mContentView = (TextView) view.findViewById(R.id.content);
                mContentImageView = (ImageView) view.findViewById(R.id.list_item_content_imageview);
            }
        }
    }

    /*
     *  This function takes a string that is separated by a key value and
     *  splits the string into a list.
     *  @param input_string - the string that needs to be split
     *  @param delimiter - the delimiter with which to split the string
     *  @return - a list of the split string
     * */
    public String[] split_string(String input_string, String delimiter) {
        String[] output = input_string.split(delimiter);
        return output;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Send the user back to the recipe cards
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
