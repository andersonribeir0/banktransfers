package com.github.andersonribeir0.banktransfers.utils;

import org.apache.kafka.common.header.Headers;

import java.util.stream.StreamSupport;

public class KafkaUtils {

    public static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
