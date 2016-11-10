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

package com.goforer.base.model.event;

import com.goforer.base.model.data.ResponseBase;

public class ResponseEvent {
    public static final int EVENT_ID_CATEGORY = 0;
    public static final int EVENT_ID_OFFER = 1;
    public static final int EVENT_ID_COMMENT = 2;
    public static final int EVENT_ID_PROFILE = 3;
    public static final int EVENT_ID_GALLERY = 4;
    public static final int EVENT_ID_EVENT = 5;

    protected ResponseBase mResponseClient;
    protected String mTag;
    protected int mId;

    public boolean isMine(String tag){
        return tag == null || tag.equals(mTag);
    }

    public void parseInResponse() {
    }

    public ResponseBase getResponseClient() { return mResponseClient; }

    public String getTag() { return mTag; }

    public int getId() {
        return mId;
    }

    public void setResponseClient(ResponseBase responseClient) { mResponseClient = responseClient; }

    public void setTag(String tag) { mTag = tag; }

    public void setId(int id) {
        mId = id;
    }
}
