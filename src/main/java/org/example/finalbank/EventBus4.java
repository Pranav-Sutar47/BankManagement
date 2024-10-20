package org.example.finalbank;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class EventBus4 {
    private static EventBus4 instance;
    private EventHandler<Event> eventHandler;

    private EventBus4() {}

    public static EventBus4 getInstance() {
        if (instance == null) {
            instance = new EventBus4();
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
