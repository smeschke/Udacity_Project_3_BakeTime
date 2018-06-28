package com.example.stephen.projectfour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stephen.projectfour.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "item_test";
    public String mData;

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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        String text = split_string(mData, "42069");
        ((TextView) rootView.findViewById(R.id.item_detail)).setText(text);
        return rootView;
    }

    /*
     *  This function takes a string that is separated by a key value and
     *  splits the string into a nicely formatted string that can be displayed as content.
     *  @param input_string - the string that needs to be split
     *  @param delimiter - the delimiter with which to split the string
     *  @return - a list of the split string
     * */
    public String split_string(String input_string, String delimiter) {
        String[] output = input_string.split(delimiter);
        String output_string = "";
        for (int idx = 0; idx < output.length; idx++){
            output_string += output[idx];
            output_string += "\n\n";
        }
        return output_string;
    }
}
