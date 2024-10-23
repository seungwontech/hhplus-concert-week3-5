package com.hhplus.tdd.concert.infra.impl;

import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.repository.ConcertScheduleRepository;
import com.hhplus.tdd.concert.infra.ConcertScheduleJpaRepository;
import com.hhplus.tdd.concert.infra.entity.ConcertScheduleJpaEntity;
import com.hhplus.tdd.concert.infra.mapper.ConcertScheduleMapper;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {

    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

    private final ConcertScheduleMapper concertScheduleMapper;

    @Override
    public List<ConcertSchedule> getConcertSchedulesOrThrow(Long concertId) {
        List<ConcertScheduleJpaEntity> entities = concertScheduleJpaRepository.findByConcertId(concertId);
        if (entities.isEmpty()) {
            log.warn("콘서트의 일정이 없습니다. concertId: {}", concertId);
            throw new CoreException(ErrorType.CONCERT_SCHEDULE_NOT_FOUND, concertId);
        }
        return entities.stream()
                .map(concertScheduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ConcertSchedule getConcertScheduleOrThrow(Long concertId, Long concertScheduleId) {
        ConcertScheduleJpaEntity entity = concertScheduleJpaRepository.findByConcertIdAndConcertScheduleId(concertId, concertScheduleId);
        if (entity == null) {
            log.warn("콘서트의 일정이 없습니다. concertId: {}, concertScheduleId: {}", concertId, concertScheduleId);
            throw new CoreException(ErrorType.CONCERT_SCHEDULE_NOT_FOUND, concertScheduleId);
        }
        return concertScheduleMapper.toDomain(entity);
    }


    @Override
    public void saveAll(List<ConcertSchedule> schedules) {
        List<ConcertScheduleJpaEntity> entities = schedules.stream()
                .map(concertScheduleMapper::toEntity)
                .toList();
        concertScheduleJpaRepository.saveAll(entities);
    }

}
