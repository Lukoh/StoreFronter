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

package com.goforer.storefronter.utility;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;

import com.goforer.base.ui.activity.BaseActivity;
import com.goforer.customtabsclient.shared.CustomTabsHelper;
import com.goforer.storefronter.R;
import com.goforer.storefronter.model.data.Event;
import com.goforer.storefronter.model.data.Item;
import com.goforer.storefronter.model.data.Profile;
import com.goforer.storefronter.ui.activity.EventActivity;
import com.goforer.storefronter.ui.activity.MainActivity;

import java.util.List;

public enum  ActivityCaller {
    INSTANCE;

    public static final String EXTRA_PROFILE = "storefronter:profile";
    public static final String LINK_URL = "https://github.com/Lukoh/Fyber_challenge_android";
    public static final String HELP_URL = "https://github.com/Lukoh/Fyber_challenge_android";

    public static final int SELECTED_ITEM_POSITION = 1000;

    public Intent createIntent(Context context, Class<?> cls, boolean isNewTask) {
        Intent intent = new Intent(context, cls);

        if (isNewTask && !(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return intent;
    }

    public void callMain(Context context, Profile profile) {
        Intent intent = createIntent(context, MainActivity.class, true);
        intent.putExtra(EXTRA_PROFILE, profile);
        context.startActivity(intent);
    }

    public void callEvent(Context context) {
        Intent intent = createIntent(context, EventActivity.class, true);
        context.startActivity(intent);
    }

    public void callCart(Context context) {
        Intent intent = createIntent(context, EventActivity.class, true);
        context.startActivity(intent);
    }

    public void callEvent(Context context, final Event event) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(context.getResources()
                .getColor(R.color.colorPrimaryDark)).setShowTitle(true);
        builder.setCloseButtonIcon(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arrow_back));
        CustomTabsIntent customTabsIntent = builder.build();
        String packageName = CustomTabsHelper.getPackageNameToUse(context);
        CustomTabsHelper.addKeepAliveExtra(context, customTabsIntent.intent);
        CustomTabsClient.bindCustomTabsService(context.getApplicationContext(), packageName,
                new CustomTabsServiceConnection() {
                    @Override
                    public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                        client.warmup(0);

                        CustomTabsSession session = client.newSession(new CustomTabsCallback());
                        session.mayLaunchUrl(Uri.parse(event.getLink()), null, null);
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                    }
                });

        customTabsIntent.launchUrl((BaseActivity)context, Uri.parse(event.getLink()));
    }

    public void callCartItemInfo(Context context, List<Item> items, int position, int requestCode) {

    }

    public void callBrowser(Context context,  String url) {
        Intent intent = createIntent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        context.startActivity(intent);
    }

    public void callItemGallery(Context context, long itemId, String itemTitle) {
    }

    private Intent createIntent(String action) {
        Intent intent = new Intent(action);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }
}
