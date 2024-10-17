package com.hhplus.tdd.concert.domain.repository;

import com.hhplus.tdd.concert.domain.model.ConcertReservation;

import java.util.List;

public interface ConcertReservationRepository {
    void saveAll(List<ConcertReservation> reservations);

    List<ConcertReservation> findAll();
}
