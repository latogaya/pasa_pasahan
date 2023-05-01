package com.seller.inventory;

import android.os.Parcel;
import android.os.Parcelable;


public class Feedbacks implements Parcelable {

    private String mSeller;
    private String mItem;
    private String mComments;

    public Feedbacks(String seller, String item, String comments) {
        mSeller = seller;
        mItem = item;
        mComments = comments;

    }

    protected Feedbacks(Parcel in) {
        mSeller = in.readString();
        mItem = in.readString();
        mComments = in.readString();

    }

    public static final Creator<Feedbacks> CREATOR = new Creator<Feedbacks>() {
        @Override
        public Feedbacks createFromParcel(Parcel in) {
            return new Feedbacks(in);
        }

        @Override
        public Feedbacks[] newArray(int size) {
            return new Feedbacks[size];
        }
    };


    public String getmSeller() {
        return mSeller;
    }
    public String getmItem() {
        return mItem;
    }
    public String getComments() {
        return mComments;
    }

    public void setComments(String comments) {
        mComments = comments;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSeller);
        parcel.writeString(mItem);
        parcel.writeString(mComments);

    }
}
