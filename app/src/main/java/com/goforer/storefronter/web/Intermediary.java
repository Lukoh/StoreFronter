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

package com.goforer.storefronter.web;

import android.content.Context;

import com.goforer.base.model.event.ResponseEvent;
import com.goforer.storefronter.model.data.ResponseCartData;
import com.goforer.storefronter.model.data.ResponseCategoryData;
import com.goforer.storefronter.model.data.ResponseEventData;
import com.goforer.storefronter.web.communicator.RequestClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * The intermediary class for sending a request to Server and returns a response.

 * <p>
 * This App adapts a Retrofit to communicate with Server(Retrofit Version 2).
 * Retrofit provides great way to HTTP calls by using annotations on the declared methods to
 * define how requests are made. (Retrofit is a great HTTP client for Android (and Java))
 *
 * A call that is busy writing its request or reading its response may receive a {@link IOException};
 * this is working as designed.
 *
 * ResponseCategoryData: Successful response body type.
 * </p>
 *
 */
public enum Intermediary {
    INSTANCE;

    public static int CATEGORY_FIRST_TYPE = 0;
    public static int CATEGORY_SECOND_TYPE = 1;
    public static int CATEGORY_THIRD_TYPE = 2;
    public static int CATEGORY_FORTH_TYPE = 4;

    public static final String UID = "spiderman";

    public void getCategoryItems(final Context context, int category_type, String uid,
                                 ResponseEvent event)  {

        Call<ResponseCategoryData> call = RequestClient.INSTANCE.getRequestMethod()
                .getCategoryItems(category_type, uid);
        call.enqueue(new RequestClient.CategoryCallback(event, context) {
            @Override
            public void onResponse(Call<ResponseCategoryData> call, Response<ResponseCategoryData> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ResponseCategoryData> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getEventList(final Context context, String uid, ResponseEvent event)  {

        Call<ResponseEventData> call = RequestClient.INSTANCE.getRequestMethod()
                .getEventList(uid);
        call.enqueue(new RequestClient.EventCallback(event, context) {
            @Override
            public void onResponse(Call<ResponseEventData> call, Response<ResponseEventData> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ResponseEventData> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    public void getCartItemList(final Context context, String uid, ResponseEvent event)  {

        Call<ResponseCartData> call = RequestClient.INSTANCE.getRequestMethod()
                .getCartItemList(uid);
        call.enqueue(new RequestClient.CartCallback(event, context) {
            @Override
            public void onResponse(Call<ResponseCartData> call, Response<ResponseCartData> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ResponseCartData> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}
