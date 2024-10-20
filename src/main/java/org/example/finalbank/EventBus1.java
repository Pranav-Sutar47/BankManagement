package org.example.finalbank;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class EventBus1 {
    private static EventBus1 instance;
    private EventHandler<Event> eventHandler;

    private EventBus1() {}

    public static EventBus1 getInstance() {
        if (instance == null) {
            instance = new EventBus1();
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
