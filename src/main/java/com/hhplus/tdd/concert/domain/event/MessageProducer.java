package com.hhplus.tdd.concert.domain.event;

import com.hhplus.tdd.concert.domain.model.ConcertEvent;

public interface MessageProducer {
    void send(ConcertEvent event, String eventId);
}
