package org.example.finalbank;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class EventBus3 {
    private static EventBus3 instance;
    private EventHandler<Event> eventHandler;

    private EventBus3() {}

    public static EventBus3 getInstance() {
        if (instance == null) {
            instance = new EventBus3();
        }
        return instance;
    }

    public void registerEventHandler(EventHandler<Event> eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void fireEvent(EventType<Event> eventType) {
        if (eventHandler != null) {
            Event event = new Event(eventType);
            eventHandler.handle(event);
        }
    }
}
