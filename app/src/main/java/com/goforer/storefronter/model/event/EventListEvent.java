package com.goforer.storefronter.model.event;

import com.goforer.base.model.event.ResponseListEvent;

/**
 * Created by USER on 2016-10-20.
 */

public class EventListEvent extends ResponseListEvent {
    public EventListEvent(boolean isNew) {
        super(isNew);
    }
}
