package co.app.ebay.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class BookModel {

    @SerializedName("title")
    private String mTitle;
    @SerializedName("author")
    @Nullable
    private String mAuthor;
    @SerializedName("imageURL")
    private String mImageUrl;

    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getAuthor() {
        return mAuthor;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
