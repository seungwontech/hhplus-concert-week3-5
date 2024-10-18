package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.ConcertPayment;
import com.hhplus.tdd.concert.domain.repository.ConcertPaymentRepository;
import com.hhplus.tdd.concert.infra.mapper.ConcertPaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ConcertPaymentRepositoryImpl implements ConcertPaymentRepository {

    private final ConcertPaymentRepository concertPaymentRepository;

    private final ConcertPaymentMapper concertPaymentMapper;


    @Override
    public void saveAll(List<ConcertPayment> concertPayments) {
        concertPaymentRepository.saveAll(concertPayments);
    }
}
