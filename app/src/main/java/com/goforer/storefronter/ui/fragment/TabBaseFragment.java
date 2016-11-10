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

package com.goforer.storefronter.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goforer.base.model.ListModel;
import com.goforer.base.model.data.ResponseBase;
import com.goforer.base.model.event.ResponseEvent;
import com.goforer.base.ui.decoration.GapItemDecoration;
import com.goforer.base.ui.fragment.RecyclerFragment;
import com.goforer.storefronter.R;
import com.goforer.storefronter.StoreFronter;
import com.goforer.storefronter.model.data.CategoryItem;
import com.goforer.storefronter.model.event.TabInfoDataEvent;
import com.goforer.storefronter.ui.adapter.TabInfoGridAdapter;
import com.goforer.storefronter.utility.CommonUtils;
import com.goforer.storefronter.utility.DisplayUtils;
import com.google.gson.JsonElement;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class TabBaseFragment extends RecyclerFragment<CategoryItem> {
    private static final String TAG = "TabBaseFragment";

    private static final int SPAN_COUNT = 3;
    private static final int SPAN_NUMBER_ONE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setItemHasFixedSize(true);

        refresh(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        super.setOnProcessListener(new RecyclerFragment.OnProcessListener() {
            @Override
            public void onCompleted(int result) {
                Log.i(TAG, "onCompleted");

                if (result == OnProcessListener.RESULT_ERROR) {
                    CommonUtils.showToastMessage(mContext, getString(R.string.toast_process_error), Toast.LENGTH_SHORT);
                    StoreFronter.closeApplication();
                }
            }

            @Override
            public void onScrolledToLast(RecyclerView recyclerView, int dx, int dy) {
                Log.i(TAG, "onScrolledToLast");
            }

            @Override
            public void onScrolling() {
                Log.i(TAG, "onScrolling");
            }

            @Override
            public void onScrolled() {
                Log.i(TAG, "onScrolled");
            }

            @Override
            public void onError(String message) {
                CommonUtils.showToastMessage(mContext, message, Toast.LENGTH_SHORT);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, SPAN_COUNT,
                GridLayoutManager.VERTICAL, false);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return SPAN_NUMBER_ONE;
            }
        };

        spanSizeLookup.setSpanIndexCacheEnabled(true);
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        return gridLayoutManager;
    }

    @Override
    protected RecyclerView.ItemDecoration createItemDecoration() {
        int gap = DisplayUtils.INSTANCE.dpToPx(mContext, 5);
        return new GapItemDecoration(GapItemDecoration.VERTICAL_LIST, gap) {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);

                if ((position + 1) % SPAN_COUNT == 1) {
                    outRect.set(mGap, mGap, 0, 0);
                } else if ((position + 1) % SPAN_COUNT == 2) {
                    outRect.set(mGap, mGap, 0, 0);
                } else if ((position + 1) % SPAN_COUNT == 0) {
                    outRect.set(mGap, mGap, mGap, 0);
                }
            }
        };
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new TabInfoGridAdapter(mContext, mItems,
                R.layout.grid_tab_info_item, false);
    }

    @Override
    protected ItemTouchHelper.Callback createItemTouchHelper() {
        return null;
    }

    @Override
    protected boolean isItemDecorationVisible() {
        return true;
    }

    @Override
    protected void requestData(boolean isNew) {
        try {
            TabInfoDataEvent event = new TabInfoDataEvent(isNew);
            event.setId(ResponseEvent.EVENT_ID_CATEGORY);
            requestTabInfo(isNew, event);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "requestData");
    }

    @Override
    protected void updateData() {
        /**
         * Please put some module to update new data here, instead of doneRefreshing() method if
         * there is some data to be updated in Server side.
         * I just put doneRefreshing() method because there is no data to be updated from Sever side.
         */
        doneRefreshing();

        Log.i(TAG, "updateData");
    }

    @Override
    protected List<CategoryItem> parseItems(JsonElement json) {
        return new ListModel<>(CategoryItem.class).fromJson(json);
    }

    @Override
    protected boolean isLastPage(int pageNum) {
        return true;
    }

    protected void requestTabInfo(boolean isNew, TabInfoDataEvent event)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(TabInfoDataEvent event) {
        switch(event.getResponseClient().getStatus()) {
            case ResponseBase.GENERAL_ERROR:
                CommonUtils.showToastMessage(mContext, getString(R.string.toast_server_error_phrase), Toast.LENGTH_SHORT);
                break;
            case ResponseBase.NETWORK_ERROR:
                CommonUtils.showToastMessage(mContext, getString(R.string.toast_disconnect_phrase), Toast.LENGTH_SHORT);
                break;
            case ResponseBase.RESPONSE_SIGNATURE_NOT_MATCH:
                CommonUtils.showToastMessage(mContext, getString(R.string.toast_response_signature_mismatch_phrase), Toast.LENGTH_SHORT);
                break;
            case ResponseBase.SUCCESSFUL:
                if (event.getResponseClient().getCount() == 0) {
                    CommonUtils.showToastMessage(mContext, getString(R.string.toast_no_data), Toast.LENGTH_SHORT);
                    return;
                }

                handleEvent(event);
                break;
        }
    }
}
