package com.jsoko.events;

import com.jsoko.events.data.EventData;

public interface EventHandler {
    void onEventReceived(final Event event);

    void onEventWithDataReceived(final Event event, final EventData eventData);
}