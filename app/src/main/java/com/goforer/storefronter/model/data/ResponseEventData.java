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

import com.goforer.base.model.data.ResponseBase;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class ResponseEventData extends ResponseBase {
    @SerializedName("pages")
    private int mPages;
    @SerializedName("events")
    private JsonElement mEvents;

    public int getPages() {
        return mPages;
    }

    public JsonElement getEvents() {
        return mEvents;
    }
}
