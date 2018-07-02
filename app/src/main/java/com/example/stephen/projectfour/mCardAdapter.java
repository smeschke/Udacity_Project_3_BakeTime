package com.example.stephen.projectfour;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stephen.projectfour.data.Contract;

import java.util.ArrayList;
import java.util.List;

public class mCardAdapter extends RecyclerView.Adapter<mCardAdapter.mAdapterViewHolder> {

    //constant ID's for the View type for level or trick (S11.02)
    private static final int VIEW_TYPE_RECIPE = 0;
    //get context
    private final Context mContext;
    //set up the click handler
    final private mAdapterOnClickHandler mClickHandler;
    //create string for output list
    public ArrayList<String> mRecipeNames;
    public ArrayList<String> mRecipeServings;

    private List<String> mData;


    //get stuff on list from Main Activity
    public mCardAdapter(@NonNull Context context,
                        mAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    //when view holder is created, inflate the views
    @Override
    public mAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        layoutId = R.layout.recipe_card;
        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        view.setFocusable(true);
        return new mAdapterViewHolder(view);
    }

    //bind data to view holder
    @Override
    public void onBindViewHolder(mAdapterViewHolder holder, int position) {
        holder.recipeNameTextView.setText(mData.get(position));
    }

    //How many? The size of the output_list.
    @Override
    public int getItemCount() {
        return  mData.size();
    }

    //override getItemViewType (from S11.02)
    @Override
    public int getItemViewType(int position) {
        int returnType = VIEW_TYPE_RECIPE;
        return returnType;
    }

    // TODO (6) create swap cursor method to reset the data
    void swapCursor(Cursor data) {
        // Move through the cursor and extract the movie poster urls.
        List<String> movie_posters = new ArrayList<>();
        String previous_recipe_name = "";
        for (int i = 0; i < data.getCount(); i++) {
            data.moveToPosition(i);
            String recipe_name = data.getString(data.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_NAME));
            if (!previous_recipe_name.equals(recipe_name)) {
                movie_posters.add(recipe_name);
            }
            previous_recipe_name = recipe_name;
        }
        mData = movie_posters;
        Log.d("LOG", "mData from mCardAdapter" + mData.toString());
        notifyDataSetChanged();
    }

    //set up clicks
    public interface mAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    //setting up the recycler view, and clicks
    class mAdapterViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        //initialize views
        public final TextView recipeNameTextView;

        //super the views so that they can be bound - set click listener too
        mAdapterViewHolder(View view) {
            super(view);
            recipeNameTextView = (TextView) view.findViewById(R.id.recipe_card_recipe_name_text_view);
            itemView.setOnClickListener(this);
        }

        //when a user taps the list
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
