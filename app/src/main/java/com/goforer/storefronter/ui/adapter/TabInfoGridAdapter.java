/*
 * Copyright (C) 2015-2016 Lukoh Nam, goForer
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

package com.goforer.storefronter.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.goforer.base.ui.activity.BaseActivity;
import com.goforer.base.ui.adapter.BaseListAdapter;
import com.goforer.base.ui.holder.BaseViewHolder;
import com.goforer.storefronter.model.data.CategoryItem;
import com.goforer.storefronter.R;

import java.util.List;

import butterknife.BindView;

public class TabInfoGridAdapter extends BaseListAdapter<CategoryItem> {
    private Context mContext;

    public TabInfoGridAdapter(Context context, List<CategoryItem> items, int layoutResId,
                              boolean usedLoadingImage) {
        super(items, layoutResId);

        mContext = context;

        setUsedLoadingImage(usedLoadingImage);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return super.onCreateViewHolder(viewGroup, type);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view, int type) {
        return new TabInfoGridViewHolder(view, ((BaseActivity)mContext).resumed());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)){
            case VIEW_TYPE_FOOTER:
            case VIEW_TYPE_LOADING:
                return;
            default:
                super.onBindViewHolder(viewHolder, position);
        }
    }

    public static class TabInfoGridViewHolder extends BaseViewHolder<CategoryItem> {
        private CategoryItem mItem;

        private boolean mIsResumed;

        @BindView(R.id.iv_content)
        ImageView mContentImageView;
        @BindView(R.id.tv_title)
        TextView mTitleView;

        public TabInfoGridViewHolder(View itemView, boolean isResumed) {
            super(itemView);

            mIsResumed = isResumed;
        }

        @Override
        public void bindItemHolder(@NonNull final CategoryItem item, final int position) {
            mItem = item;

            Glide.with(mContext.getApplicationContext()).load(mItem.getImage())
                    .asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    mContentImageView.setImageBitmap(resource);
                }
            });

            mTitleView.setText(mItem.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIsResumed) {
                        //TODO:: Implement each operation when the item is clicked
                        // That will be great if StoreFronter is applied into Fyber Challenge as category activity
                    }
                }
            });
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
