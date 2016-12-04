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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goforer.base.ui.activity.BaseActivity;
import com.goforer.base.ui.adapter.BaseListAdapter;
import com.goforer.base.ui.helper.ItemTouchHelperListener;
import com.goforer.base.ui.holder.BaseViewHolder;
import com.goforer.base.ui.holder.DefaultViewHolder;
import com.goforer.base.ui.view.SquircleImageView;
import com.goforer.storefronter.R;
import com.goforer.storefronter.model.action.MoveItemAction;
import com.goforer.storefronter.model.data.Event;
import com.goforer.storefronter.utility.ActivityCaller;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class EventListAdapter extends BaseListAdapter<Event> implements ItemTouchHelperListener {
    private Context mContext;

    public EventListAdapter(Context context, final List<Event> items, int layoutResId,
                            boolean usedLoadingImage) {
        super(items, layoutResId);

        setUsedLoadingImage(usedLoadingImage);
        mContext = context;
    }

    @Override
    public int getItemCount() {
        int count  = super.getItemCount();

        if (isReachedToLastPage()) {
            return count + 1;
        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount() - 1;

        if (isReachedToLastPage() && position == itemCount) {
            return VIEW_TYPE_FOOTER;
        } else if (position == itemCount) {
            return VIEW_TYPE_LOADING;
        }

        return VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view;

        switch (type) {
            case VIEW_TYPE_FOOTER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_last_item,
                        viewGroup, false);
                return new DefaultViewHolder(view);
            case VIEW_TYPE_LOADING:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.list_loading_item, viewGroup, false);
                return new DefaultViewHolder(view);
            default:
                return super.onCreateViewHolder(viewGroup, type);
        }
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view, int type) {
        return new EventListViewHolder(view, getItems(), ((BaseActivity)mContext).resumed());
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

    @Override
    public void onItemDismiss(int position) {
        getItems().remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getItems(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
        return true;
    }

    @Override
    public void onItemDrag(int actionState) {
        MoveItemAction action = new MoveItemAction();
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            action.setType(MoveItemAction.ITEM_MOVED_START);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_IDLE){
            action.setType(MoveItemAction.ITEM_MOVED_END);
        }

        EventBus.getDefault().post(action);
    }

    final static class EventListViewHolder extends BaseViewHolder<Event> {
        private List<Event> mEventItems;

        private boolean mIsResumed;

        @BindView(R.id.iv_image)
        SquircleImageView mImageView;
        @BindView(R.id.tv_category)
        TextView mCategoryView;
        @BindView(R.id.tv_contents)
        TextView mContentsView;
        @BindView(R.id.tv_title)
        TextView mTitleView;
        @BindView(R.id.tv_validity_date)
        TextView mValidityDateView;

        EventListViewHolder(View itemView, List<Event> items, boolean isResumed) {
            super(itemView);

            mEventItems = items;
            mIsResumed = isResumed;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void bindItemHolder(@NonNull final Event event, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIsResumed) {
                        ActivityCaller.INSTANCE.callEvent(mContext, event);
                    }
                }
            });

            mImageView.setImage(event.getImages().getThumbnail());
            mCategoryView.setText(event.getName());
            mTitleView.setText(event.getTitle());
            mContentsView.setText(event.getContent());
            mValidityDateView.setText(event.getValidityDate());
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
