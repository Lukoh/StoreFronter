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

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import com.goforer.base.ui.decoration.RemoverItemDecoration;
import com.goforer.base.ui.fragment.RecyclerFragment;
import com.goforer.base.ui.helper.RecyclerItemTouchHelperCallback;
import com.goforer.storefronter.R;
import com.goforer.storefronter.model.action.MoveItemAction;
import com.goforer.storefronter.model.data.Item;
import com.goforer.storefronter.model.event.CartListEvent;
import com.goforer.storefronter.model.event.EventListEvent;
import com.goforer.storefronter.ui.adapter.CartListAdapter;
import com.goforer.storefronter.utility.CommonUtils;
import com.goforer.storefronter.web.Intermediary;
import com.google.gson.JsonElement;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class CartListFragment  extends RecyclerFragment<Item> {
    private static final String TAG = "EventListFragment";

    private CartListAdapter mAdapter;

    private int mTotalPageNum;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTotalPageNum = 1;
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
                if (result == OnProcessListener.RESULT_ERROR) {
                    CommonUtils.showToastMessage(mContext, getString(R.string.toast_process_error), Toast.LENGTH_SHORT);
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

        return new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new RemoverItemDecoration(Color.RED);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return mAdapter =  new CartListAdapter(mContext, mItems, R.layout.list_cart_item, true);
    }

    @Override
    protected ItemTouchHelper.Callback createItemTouchHelper() {
        return new RecyclerItemTouchHelperCallback(mContext, mAdapter, Color.RED);
    }

    @Override
    protected boolean isItemDecorationVisible() {
        return true;
    }

    @Override
    protected void requestData(boolean isNew) {
        try {
            requestCartItemList(isNew);
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
    protected List<Item> parseItems(JsonElement json) {
        mTotalPageNum = getTotalPage();
        return new ListModel<>(Item.class).fromJson(json);
    }

    @Override
    protected boolean isLastPage(int pageNum) {
        return (mTotalPageNum == pageNum);

    }

    protected void requestCartItemList(boolean isNew)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        EventListEvent event = new EventListEvent(isNew);
        event.setId(ResponseEvent.EVENT_ID_EVENT);

        Intermediary.INSTANCE.getCartItemList(mContext.getApplicationContext(),
                Intermediary.UID, event);
    }

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(CartListEvent event) {
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

    @SuppressWarnings("")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAction(MoveItemAction action) {
        if (action.getType() == MoveItemAction.ITEM_MOVED_START) {
            getRefreshLayout().setRefreshing(false);
            getRefreshLayout().setEnabled(false);
        } else {
            getRefreshLayout().setEnabled(true);
        }
    }
}
