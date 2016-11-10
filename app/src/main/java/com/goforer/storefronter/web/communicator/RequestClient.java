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

package com.goforer.storefronter.web.communicator;

import android.content.Context;

import com.goforer.base.model.event.ResponseEvent;
import com.goforer.storefronter.model.data.ResponseCartData;
import com.goforer.storefronter.model.data.ResponseCategoryData;
import com.goforer.storefronter.model.data.ResponseEventData;
import com.goforer.storefronter.utility.ConnectionUtils;
import com.goforer.storefronter.web.communicator.callback.BaseCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public enum RequestClient {
    INSTANCE;

    private static final long READ_TIME_OUT = 5;
    private static final long WRITE_TIME_OUT = 5;
    private static final long CONNECT_TIME_OUT = 5;

    private RequestMethod mRequestor;

    private String mRawResponseBody;

    public RequestMethod getRequestMethod() {
        if (mRequestor == null) {
           final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Connection", "keep-alive")
                            .header("Content-Encoding", "gzip")
                            .method(original.method(), original.body())
                            .build();

                    Response response = chain.proceed(request);

                    mRawResponseBody = response.body().string();

                    return response.newBuilder()
                            .body(ResponseBody.create(response.body().contentType(),
                                    mRawResponseBody)).build();
                }
            });

            OkHttpClient client = httpClient.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.storefronter.com/feed/v1/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mRequestor = retrofit.create(RequestMethod.class);
        }

        return mRequestor;
    }

    /**
     * Communicates responses of Category from Server or offline requests.
     * One and only one method will be invoked in response to a given request.
     */
    static public class CategoryCallback extends BaseCallback implements Callback<ResponseCategoryData> {
        private ResponseEvent mEvent;
        private Context mContext;

        protected CategoryCallback(ResponseEvent event, Context context) {
            mEvent = event;
            mContext = context;
        }

        @Override
        public void onResponse(Call<ResponseCategoryData> call,
                               retrofit2.Response<ResponseCategoryData> response) {
            if (!response.isSuccessful() || isResponseError(response.errorBody())) {
                try {
                    showErrorMessage(response.errorBody().string());
                    System.out.println(response.errorBody().string());
                } catch (IOException e) {
                    // do nothing
                }

                return;
            }

            if (mEvent != null) {
                mEvent.setResponseClient(response.body());
                mEvent.getResponseClient().setStatus(ResponseCategoryData.SUCCESSFUL);
                mEvent.parseInResponse();

                EventBus.getDefault().post(mEvent);
            }
        }

        @Override
        public void onFailure(Call<ResponseCategoryData> call, Throwable t) {
            boolean isDeviceEnabled = true;

            if (!ConnectionUtils.INSTANCE.isOnline(mContext)) {
                isDeviceEnabled = false;
            }

            if (mEvent != null) {
                mEvent.setResponseClient(new ResponseCategoryData());
                if (!isDeviceEnabled) {
                    mEvent.getResponseClient().setStatus(ResponseCategoryData.NETWORK_ERROR);
                } else {
                    mEvent.getResponseClient().setStatus(ResponseCategoryData.GENERAL_ERROR);
                }

                EventBus.getDefault().post(mEvent);
            }
        }
    }

    /**
     * Communicates responses of Event from Server or offline requests.
     * One and only one method will be invoked in response to a given request.
     */
    static public class EventCallback extends BaseCallback implements Callback<ResponseEventData> {
        private ResponseEvent mEvent;
        private Context mContext;

        protected EventCallback(ResponseEvent event, Context context) {
            mEvent = event;
            mContext = context;
        }

        @Override
        public void onResponse(Call<ResponseEventData> call,
                               retrofit2.Response<ResponseEventData> response) {
            if (!response.isSuccessful() || isResponseError(response.errorBody())) {
                try {
                    showErrorMessage(response.errorBody().string());
                    System.out.println(response.errorBody().string());
                } catch (IOException e) {
                    // do nothing
                }

                return;
            }

            if (mEvent != null) {
                mEvent.setResponseClient(response.body());
                mEvent.getResponseClient().setStatus(ResponseCategoryData.SUCCESSFUL);
                mEvent.parseInResponse();

                EventBus.getDefault().post(mEvent);
            }
        }

        @Override
        public void onFailure(Call<ResponseEventData> call, Throwable t) {
            boolean isDeviceEnabled = true;

            if (!ConnectionUtils.INSTANCE.isOnline(mContext)) {
                isDeviceEnabled = false;
            }

            if (mEvent != null) {
                mEvent.setResponseClient(new ResponseEventData());
                if (!isDeviceEnabled) {
                    mEvent.getResponseClient().setStatus(ResponseEventData.NETWORK_ERROR);
                } else {
                    mEvent.getResponseClient().setStatus(ResponseEventData.GENERAL_ERROR);
                }

                EventBus.getDefault().post(mEvent);
            }
        }
    }

    /**
     * Communicates responses of Event from Server or offline requests.
     * One and only one method will be invoked in response to a given request.
     */
    static public class CartCallback extends BaseCallback implements Callback<ResponseCartData> {
        private ResponseEvent mEvent;
        private Context mContext;

        protected CartCallback(ResponseEvent event, Context context) {
            mEvent = event;
            mContext = context;
        }

        @Override
        public void onResponse(Call<ResponseCartData> call,
                               retrofit2.Response<ResponseCartData> response) {
            if (!response.isSuccessful() || isResponseError(response.errorBody())) {
                try {
                    showErrorMessage(response.errorBody().string());
                    System.out.println(response.errorBody().string());
                } catch (IOException e) {
                    // do nothing
                }

                return;
            }

            if (mEvent != null) {
                mEvent.setResponseClient(response.body());
                mEvent.getResponseClient().setStatus(ResponseCartData.SUCCESSFUL);
                mEvent.parseInResponse();

                EventBus.getDefault().post(mEvent);
            }
        }

        @Override
        public void onFailure(Call<ResponseCartData> call, Throwable t) {
            boolean isDeviceEnabled = true;

            if (!ConnectionUtils.INSTANCE.isOnline(mContext)) {
                isDeviceEnabled = false;
            }

            if (mEvent != null) {
                mEvent.setResponseClient(new ResponseCartData());
                if (!isDeviceEnabled) {
                    mEvent.getResponseClient().setStatus(ResponseCartData.NETWORK_ERROR);
                } else {
                    mEvent.getResponseClient().setStatus(ResponseCartData.GENERAL_ERROR);
                }

                EventBus.getDefault().post(mEvent);
            }
        }
    }

    public interface RequestMethod {
        @GET("category/items.json")
        Call<ResponseCategoryData> getCategoryItems(
                @Query("category_type") int category_type,
                @Query("uid") String uid
        );

        @GET("event/events.json")
        Call<ResponseEventData> getEventList(
                @Query("uid") String uid
        );

        @GET("cart/items.json")
        Call<ResponseCartData> getCartItemList(
                @Query("uid") String uid
        );
    }
}
