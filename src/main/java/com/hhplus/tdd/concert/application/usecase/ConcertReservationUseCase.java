package com.hhplus.tdd.concert.application.usecase;

import com.hhplus.tdd.concert.domain.model.*;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertReservationRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.request.ConcertReservationReq;
import com.hhplus.tdd.concert.presentation.response.ConcertReservationRes;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConcertReservationUseCase {

    private final ConcertRepository concertRepository;

    private final ConcertReservationRepository concertReservationRepository;

    private final ConcertSeatRepository concertSeatRepository;

    @Transactional
    public ConcertReservationResult execute(Long concertId, Long concertScheduleId, ConcertReservationReq reservationReq) {
        try {
            Concert concert = concertRepository.getConcertOrThrow(concertId);

            List<ConcertSeat> seats = concertSeatRepository.getConcertSeatsByScheduleOrThrow(concertId, concertScheduleId, "N");

            Map<Long, ConcertSeat> seatMap = mapSeatByIds(seats, reservationReq.getConcertSeatIds());

            List<ConcertReservation> reservations = createReservationList(concertScheduleId, reservationReq.getConcertSeatIds(), reservationReq.getUserId());

            concertReservationRepository.saveAll(reservations);

            List<ConcertSeat> updatedSeats = markSeatsAsReserved(concertId, concertScheduleId, reservationReq.getConcertSeatIds());

            concertSeatRepository.saveAll(updatedSeats);

            return buildReservationResult(concert, reservations, seatMap);
        } catch (OptimisticLockException e) {
            throw new CoreException(ErrorType.CONCERT_SEAT_ALREADY_RESERVED, "이미 예약된 좌석입니다.");
        }
    }

    // 예약할 좌석 ID와 좌석을 맵핑하는 메서드
    public Map<Long, ConcertSeat> mapSeatByIds(List<ConcertSeat> seats, Long[] concertSeatIds) {
        Map<Long, ConcertSeat> seatMap = new HashMap<>();
        Set<Long> concertSeatIdSet = new HashSet<>(Arrays.asList(concertSeatIds));

        for (ConcertSeat seat : seats) {
            if (concertSeatIdSet.contains(seat.getConcertSeatId())) {
                seatMap.put(seat.getConcertSeatId(), seat);
            }
        }
        return seatMap;
    }

    // 좌석의 예약 여부를 'Y' 상태로 업데이트하고, 매핑된 좌석 리스트를 반환하는 메서드.
    public List<ConcertSeat> markSeatsAsReserved(Long concertId, Long concertScheduleId, Long[] concertSeatIds) {

        List<ConcertSeat> concertSeats = concertSeatRepository.findByConcertIdAndConcertScheduleIdAndConcertSeatIdIn(concertId, concertScheduleId, concertSeatIds);

        for (ConcertSeat seat : concertSeats) {
            if (!"N".equals(seat.getReserveYn())) {
                throw new CoreException(ErrorType.CONCERT_SEAT_ALREADY_RESERVED, "이미 예약된 좌석이 있습니다.");
            }
            seat.setAsReserved();
        }

        return concertSeats;
    }

    public List<ConcertReservation> createReservationList(Long concertScheduleId, Long[] concertSeatIds, Long userId) {
        List<ConcertReservation> reservations = new ArrayList<>();

        for (Long seatId : concertSeatIds) {
            ConcertReservation reservation = ConcertReservation.of(
                    null
                    , userId
                    , concertScheduleId
                    , seatId
                    , String.valueOf(ReservationStatus.WAITING)
                    , LocalDateTime.now()
                    , LocalDateTime.now().plusMinutes(5)

            );
            reservations.add(reservation);
        }

        return reservations;
    }

    public ConcertReservationResult buildReservationResult(Concert concert, List<ConcertReservation> reservations, Map<Long, ConcertSeat> seatMap) {
        List<ConcertReservationRes.Seat> seatResponseList = new ArrayList<>();
        int totalPrice = 0;

        for (ConcertReservation reservation : reservations) {
            Long reservedSeatId = reservation.getConcertSeatId();
            ConcertSeat seatInfo = seatMap.get(reservedSeatId);

            if (seatInfo != null) {
                ConcertReservationRes.Seat seatResponse = ConcertReservationRes.Seat.of(
                        seatInfo.getSeatNumber()
                        , reservation.getReservationStatus()
                        , seatInfo.getSeatPrice()
                );
                seatResponseList.add(seatResponse);
                totalPrice += seatResponse.getSeatPrice();
            }
        }

        return ConcertReservationResult.of(reservations.get(0).getUserId(), concert.getConcertTitle(), seatResponseList, totalPrice);
    }

}
