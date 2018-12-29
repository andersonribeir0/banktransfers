package com.github.andersonribeir0.banktransfers.events;

import java.util.UUID;

public abstract class Event {

    private final String id;

    public Event() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}
