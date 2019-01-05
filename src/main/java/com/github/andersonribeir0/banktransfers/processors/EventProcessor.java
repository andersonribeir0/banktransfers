package com.github.andersonribeir0.banktransfers.processors;

import com.github.andersonribeir0.banktransfers.commands.Command;

public interface EventProcessor<T extends Command> {
    String process(T command);
}
