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

public class Event extends BaseModel implements Parcelable {
    @SerializedName("id")
    private long mId;
    @SerializedName("category_item_id")
    private long mCategoryItemId;
    @SerializedName("name")
    private String mName;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("content")
    private String mContent;
    @SerializedName("link")
    private String mLink;
    @SerializedName("validity_date")
    private String mValidityDate;
    @SerializedName("thumbnail")
    private Images mImages;

    public Event() {
    }

    public long getId(){
        return mId;
    }

    public long getCategoryItemId() {
        return mCategoryItemId;
    }

    public String getName() {
        return mName;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public String getLink() {
        return mLink;
    }

    public String getValidityDate() {
        return mValidityDate;
    }

    public Images getImages() {
        return mImages;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setCategoryItemId(long id) {
        mCategoryItemId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public void setValidityDate(String date) {
        mValidityDate = date;
    }

    public void setImages(Images images) {
        mImages = images;
    }

    protected Event(Parcel in) {
        mId = in.readLong();
        mCategoryItemId = in.readLong();
        mName= in.readString();
        mTitle = in.readString();
        mContent = in.readString();
        mLink = in.readString();
        mValidityDate = in.readString();
        mImages = in.readParcelable(Images.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeLong(mCategoryItemId);
        dest.writeString(mName);
        dest.writeString(mTitle);
        dest.writeString(mContent);
        dest.writeString(mLink);
        dest.writeString(mValidityDate);
        dest.writeParcelable(mImages, flags);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public final static class Images implements Parcelable {
        @SerializedName("thumbnail")
        private String mThumbnail;
        @SerializedName("original")
        private String mOriginal;

        public Images() {

        }

        public String getThumbnail() {
            return mThumbnail;
        }

        public String getOriginal() {
            return mOriginal;
        }

        public void setThumbnail(String url) {
            mThumbnail = url;
        }

        public void setOriginal(String url) {
            mOriginal = url;
        }

        protected Images(Parcel in) {
            mThumbnail = in.readString();
            mOriginal = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mThumbnail);
            dest.writeString(mOriginal);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
            @Override
            public Images createFromParcel(Parcel in) {
                return new Images(in);
            }

            @Override
            public Images[] newArray(int size) {
                return new Images[size];
            }
        };
    }
}
