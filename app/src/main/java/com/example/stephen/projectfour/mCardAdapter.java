package com.example.stephen.projectfour;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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


    //get stuff on list from Main Activity
    public mCardAdapter(@NonNull Context context,
                        mAdapterOnClickHandler clickHandler,
                        ArrayList<String> recipeNames, ArrayList<String> servings) {
        mContext = context;
        mClickHandler = clickHandler;
        mRecipeNames = recipeNames;
        mRecipeServings = servings;
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
        holder.recipeNameTextView.setText(mRecipeNames.get(position));
        holder.recipeServingsTextView.setText(mRecipeServings.get(position));
    }

    //How many? The size of the output_list.
    @Override
    public int getItemCount() {
        return mRecipeNames.size();
    }

    //override getItemViewType (from S11.02)
    @Override
    public int getItemViewType(int position) {
        int returnType = VIEW_TYPE_RECIPE;
        return returnType;
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
        public final TextView recipeServingsTextView;

        //super the views so that they can be bound - set click listener too
        mAdapterViewHolder(View view) {
            super(view);
            recipeNameTextView = (TextView) view.findViewById(R.id.recipe_card_recipe_name_text_view);
            recipeServingsTextView = (TextView) view.findViewById(R.id.recipe_card_servings_text_view);
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
