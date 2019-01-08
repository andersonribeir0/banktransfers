package com.github.andersonribeir0.banktransfers.events.dispatchers;

import com.github.andersonribeir0.banktransfers.events.Event;

public interface EventDispatcher {
    void dispatch(String key, Event event);
}
