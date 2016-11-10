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

import android.content.Context;
import android.widget.Toast;

import com.goforer.storefronter.model.data.Profile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonUtils {
    private static Profile mProfile;

    public static Profile getProfile() {
        return mProfile;
    }

    public static void setProfile(Profile profile) {
        mProfile = profile;
    }

    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(c.getTime());
    }

    public static void showToastMessage(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
}
