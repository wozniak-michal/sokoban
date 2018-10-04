package com.jsoko.controllers;

import com.badlogic.gdx.utils.Disposable;
import com.jsoko.events.Event;
import com.jsoko.events.EventHandler;
import com.jsoko.events.data.EventData;

import java.util.HashMap;
import java.util.LinkedList;

public class EventController implements Disposable {

    private HashMap<Event, LinkedList<EventHandler>> handlers;

    public EventController() {
        this.handlers = new HashMap<>();
        this.registerEvents();
    }

    private void registerEvents() {
        for (Event e : Event.values()) {
            handlers.put(e, new LinkedList<EventHandler>());
        }
    }

    public void registerForEvent(EventHandler handler, Event event) {
        LinkedList<EventHandler> eventHandlers = handlers.get(event);

        if (eventHandlers == null) {
            throw new NullPointerException("EventHandler list is null, event not registered!");
        }

        eventHandlers.add(handler);
    }

    public void registerForMultipleEvents(EventHandler handler, Event... events) {
        for (Event event : events) {
            registerForEvent(handler, event);
        }
    }

    public void registerForEvents(EventHandler handler) {
        for (Event event : handlers.keySet()) {
            registerForEvent(handler, event);
        }
    }

    public void publishEvent(final Event event) {
        System.out.println("[EVENT BROADCAST] Event published: " + event);

        for (EventHandler handler : handlers.get(event)) {
            handler.onEventReceived(event);
        }
    }

    public void publishEventWithData(final Event event, final EventData data) {
        System.out.println("[EVENT BROADCAST] Event with data published: " + event);

        for (EventHandler handler : handlers.get(event)) {
            handler.onEventWithDataReceived(event, data);
        }
    }

    @Override
    public void dispose() {
        for (LinkedList<EventHandler> handler : handlers.values()) {
            handler.clear();
        }
        handlers.clear();
    }
}
