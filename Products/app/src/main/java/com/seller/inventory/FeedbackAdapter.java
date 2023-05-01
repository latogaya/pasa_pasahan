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


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, parent, false);
        }

        Feedbacks product = getItem(position);

        TextView nameTextView = view.findViewById(R.id.sellerFeedback);
        nameTextView.setText(product.getmSeller());

        TextView descriptionTextView = view.findViewById(R.id.itemFeedback);
        descriptionTextView.setText(product.getmItem());

        return view;
    }
}