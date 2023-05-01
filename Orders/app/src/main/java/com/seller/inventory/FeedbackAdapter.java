package com.seller.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FeedbackAdapter extends ArrayAdapter<Feedbacks> {
    private Context mContext;
    private int mResource;
    private List<Feedbacks> mProducts;



    public FeedbackAdapter(Context context, int resource, List<Feedbacks> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mProducts = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inventory_recycler_view, parent, false);
        }

        TextView sellerTextView = convertView.findViewById(R.id.sellerFeedback);
        TextView itemTextView = convertView.findViewById(R.id.itemFeedback);
        TextView commentTextView = convertView.findViewById(R.id.commentsFeedback);

        Feedbacks feedback = getItem(position);
        sellerTextView.setText(feedback.getmSeller());
        itemTextView.setText(feedback.getmItem());
        commentTextView.setText(feedback.getComments());

        return convertView;
    }

}
