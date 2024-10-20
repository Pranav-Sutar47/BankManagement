package org.example.finalbank;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class EventBus2 {
    private static EventBus2 instance;
    private EventHandler<Event> eventHandler;

    private EventBus2() {}

    public static EventBus2 getInstance() {
        if (instance == null) {
            instance = new EventBus2();
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
