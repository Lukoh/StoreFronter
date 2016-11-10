/*
 * Copyright (C) 2016 Lukoh Nam, goForer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.goforer.storefronter.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.goforer.base.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Item extends BaseModel implements Parcelable {
    @SerializedName("catergory")
    private CategoryItem mCategory;
    @SerializedName("id")
    private long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("events")
    private List<Event> mEvents;
    @SerializedName("Comments")
    private List<Comment> mComments;
    @SerializedName("gallery_count")
    private int mGalleryCount;

    public CategoryItem getCaegroy() {
        return mCategory;
    }

    public long getId() {
        return mId;
    }

    public String getImage() {
        return mImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPrice() {
        return mPrice;
    }

    public List<Event> getEvents() {
        return mEvents;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public int getGalleryCount() {
        return mGalleryCount;
    }

    public void setGalleryCount(int count) {
        mGalleryCount = count;
    }

    public void setEvents(List<Event> events) {
        mEvents = events;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public Item() {
    }

    protected Item(Parcel in) {
        mCategory = in.readParcelable(CategoryItem.class.getClassLoader());
        mId = in.readLong();
        mImage = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mPrice = in.readString();
        mEvents = new ArrayList<>();
        in.readTypedList(mEvents, Event.CREATOR);
        mComments = new ArrayList<>();
        in.readTypedList(mComments, Comment.CREATOR);
        mGalleryCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mCategory, flags);
        dest.writeLong(mId);
        dest.writeString(mImage);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mPrice);
        dest.writeTypedList(mEvents);
        dest.writeTypedList(mComments);
        dest.writeInt(mGalleryCount);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CategoryItem> CREATOR = new Parcelable.Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel in) {
            return new CategoryItem(in);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };
}
