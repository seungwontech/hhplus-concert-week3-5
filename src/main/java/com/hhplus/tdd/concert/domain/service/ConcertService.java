package com.hhplus.tdd.concert.domain.service;

import com.hhplus.tdd.concert.domain.model.*;
import com.hhplus.tdd.concert.domain.repository.*;
import com.hhplus.tdd.concert.exception.ConcertErrorResult;
import com.hhplus.tdd.concert.exception.ConcertException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final ConcertReservationRepository concertReservationRepository;
    private final ConcertPaymentRepository concertPaymentRepository;

    // 콘서트 조회
    public Concert getConcert(Long concertId) {
        Concert result = concertRepository.getConcert(concertId);
        if (result == null) {
            throw new ConcertException(ConcertErrorResult.CONCERT_NOT_FOUND);
        }
        return result;
    }

    // 콘서트 날짜 조회
    public List<ConcertSchedule> getConcertSchedules(Long concertId) {
        List<ConcertSchedule> schedules = concertScheduleRepository.getConcertSchedules(concertId);
        if (schedules.isEmpty()) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SCHEDULE_NOT_FOUND);
        }
        return schedules;
    }

    // 콘서트 좌석 조회
    public List<ConcertSeat> getConcertSeats(Long concertId) {
        List<ConcertSeat> seats = concertSeatRepository.getConcertSeats(concertId);
        if (seats.isEmpty()) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SEAT_NOT_FOUND);
        }
        return seats;
    }


    public ConcertSchedule getConcertSchedule(Long concertId, Long concertScheduleId) {
        ConcertSchedule schedule = concertScheduleRepository.getConcertSchedule(concertId, concertScheduleId);
        if (schedule == null) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SCHEDULE_NOT_FOUND);
        }
        return schedule;
    }


    public List<ConcertSeat> getConcertSeatsBySchedule(Long concertId, Long concertScheduleId) {
        List<ConcertSeat> seats = concertSeatRepository.getConcertSeatsBySchedule(concertId, concertScheduleId);
        if (seats == null) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SEAT_NOT_FOUND);
        }
        return seats;
    }

    public void saveReservations(List<ConcertReservation> reservations) {
        concertReservationRepository.saveAll(reservations);
    }


    // 스케줄러 좌석에 대한 임시 배정 해지
    public void deleteReservations() {
        List<ConcertReservation> reservations = concertReservationRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        for (ConcertReservation reservation : reservations) {
            if (reservation.getReservationExpiry().isBefore(now)) {
                reservation.updateCanceled(String.valueOf(ReservationStatus.CANCELED));
            }
        }

        concertReservationRepository.saveAll(reservations);
    }

    public void saveSeatReserved(Long concertId, Long concertScheduleId, Long[] concertSeatIds) {
        List<ConcertSeat> concertSeats = concertSeatRepository.findByConcertIdAndConcertScheduleIdAndSeatIdIn(concertId, concertScheduleId, concertSeatIds);

        List<ConcertSeat> updateSeats = new ArrayList<>();
        for (ConcertSeat seat : concertSeats) {
            updateSeats.add(seat.updateReserveYn("Y"));
        }

        concertSeatRepository.saveAll(updateSeats);
    }


    public void saveConcertPayments(List<ConcertPayment> concertPayments) {
        concertPaymentRepository.saveAll(concertPayments);
    }

    public List<ConcertSeat> getConcertSeatIdIn(Long[] concertSeatIds) {
        return concertSeatRepository.getConcertSeatIdIn(concertSeatIds);
    }
}

