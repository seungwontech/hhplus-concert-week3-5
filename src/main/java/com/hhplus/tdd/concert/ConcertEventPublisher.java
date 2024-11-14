package com.hhplus.tdd.concert;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void notifyComplete(ConcertEvent concertEvent) {
        applicationEventPublisher.publishEvent(concertEvent);
    }

    public void notifyError(ConcertEvent concertEvent) {
        applicationEventPublisher.publishEvent(concertEvent);
    }
}
