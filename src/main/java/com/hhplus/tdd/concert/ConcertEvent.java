package com.hhplus.tdd.concert;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class ConcertEvent {
    private String eventId;
    private String type;
    private Map<String, Object> data;

    public static ConcertEvent toCompleteEvent(Map data) {
        return ConcertEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .type("COMPLETED")
                .data(data)
                .build();
    }

    public static ConcertEvent toErrorEvent(Map data) {
        return ConcertEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .type("ERROR")
                .data(data)
                .build();
    }
}
