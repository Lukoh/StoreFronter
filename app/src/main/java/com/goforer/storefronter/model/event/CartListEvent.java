package com.goforer.storefronter.model.event;

import com.goforer.base.model.event.ResponseListEvent;

/**
 * Created by USER on 2016-10-21.
 */

public class CartListEvent extends ResponseListEvent {
    public CartListEvent(boolean isNew) {
        super(isNew);
    }
}
