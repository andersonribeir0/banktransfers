package com.github.andersonribeir0.banktransfers.events;

import java.util.UUID;

public abstract class Event {

    private final String id;
    private final EventType eventType;

    public Event(EventType eventType) {
        this.id = UUID.randomUUID().toString();
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public EventType getEventType() {
        return eventType;
    }

}
