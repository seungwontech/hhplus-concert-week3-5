package com.hhplus.tdd.concert.domain.repository;

import com.hhplus.tdd.concert.domain.model.ConcertPayment;

import java.util.List;

public interface ConcertPaymentRepository {
    void saveAll(List<ConcertPayment> concertPayments);
}
