package org.example.finalbank;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class EventBus {
    private static EventBus instance;
    private EventHandler<Event> eventHandler;

    private EventBus() {}

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
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
