package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.ConcertPayment;
import com.hhplus.tdd.concert.domain.repository.ConcertPaymentRepository;
import com.hhplus.tdd.concert.infra.ConcertPaymentJpaRepository;
import com.hhplus.tdd.concert.infra.entity.ConcertPaymentJpaEntity;
import com.hhplus.tdd.concert.infra.mapper.ConcertPaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ConcertPaymentRepositoryImpl implements ConcertPaymentRepository {

    private final ConcertPaymentJpaRepository concertPaymentJpaRepository;

    private final ConcertPaymentMapper concertPaymentMapper;


    @Override
    public void saveAll(List<ConcertPayment> concertPayments) {

        List<ConcertPaymentJpaEntity> entities = concertPayments.stream()
                .map(concertPaymentMapper::toEntity)
                .toList();
        concertPaymentJpaRepository.saveAll(entities);

    }

    @Override
    public List<ConcertPayment> findAll() {
        List<ConcertPaymentJpaEntity> entities = concertPaymentJpaRepository.findAll();
        return entities.stream()
                .map(concertPaymentMapper::toDomain)
                .collect(Collectors.toList());
    }
}
