package com.example.stephen.projectfour;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stephen.projectfour.data.Contract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class mCardAdapter extends RecyclerView.Adapter<mCardAdapter.mAdapterViewHolder> {

    private static final int VIEW_TYPE_RECIPE = 0;
    //get context
    private final Context mContext;
    //set up the click handler
    final private mAdapterOnClickHandler mClickHandler;
    private List<String> mData;
    private List<String> mImgUrlStrings;


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
        String img_url = mImgUrlStrings.get(position);
        Picasso.with(mContext).load(img_url).error(R.drawable.missing).into(holder.recipeImageView);
    }

    //How many? The size of the output_list.
    @Override
    public int getItemCount() {
        int mDataSize;
        try { mDataSize = mData.size(); }
        catch (Exception e) { mDataSize = 0; }
        return  mDataSize;
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
        List<String> image_urls = new ArrayList<>();
        String previous_recipe_name = "";
        for (int i = 0; i < data.getCount(); i++) {
            data.moveToPosition(i);
            String recipe_name = data.getString(data.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_NAME));
            String img_url = data.getString(data.getColumnIndex(
                    Contract.listEntry.COLUMN_RECIPE_IMAGE_URL));
            if (!previous_recipe_name.equals(recipe_name)) {
                movie_posters.add(recipe_name);
                image_urls.add(img_url);
            }
            previous_recipe_name = recipe_name;
        }
        mData = movie_posters;
        mImgUrlStrings = image_urls;
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
        public final ImageView recipeImageView;

        //super the views so that they can be bound - set click listener too
        mAdapterViewHolder(View view) {
            super(view);
            recipeImageView = (ImageView) view.findViewById(R.id.recipe_card_image_view);
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
