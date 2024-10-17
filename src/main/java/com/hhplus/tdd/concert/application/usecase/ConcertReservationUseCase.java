package com.hhplus.tdd.concert.application.usecase;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertReservation;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.model.ReservationStatus;
import com.hhplus.tdd.concert.domain.service.ConcertService;
import com.hhplus.tdd.concert.presentation.request.ConcertReservationReq;
import com.hhplus.tdd.concert.presentation.response.ConcertReservationRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ConcertReservationUseCase {

    private final ConcertService concertService;

    public ConcertReservationRes execute(Long concertId, Long concertScheduleId, ConcertReservationReq reservationReq) {
        Concert concert = concertService.getConcert(concertId);

        List<ConcertSeat> seats = concertService.getConcertSeatsBySchedule(concertId, concertScheduleId);

        Map<Long, ConcertSeat> seatMap = createSeatMap(seats, reservationReq.getConcertSeatIds());

        List<ConcertReservation> reservations = reserveSeats(concertId, concertScheduleId, reservationReq.getConcertSeatIds(), reservationReq.getUserId());

        concertService.saveReservations(reservations);

        concertService.saveSeatReserved(concertId, concertScheduleId, reservationReq.getConcertSeatIds());

        return buildReservationResponse(concert, reservations, seatMap);
    }


    private Map<Long, ConcertSeat> createSeatMap(List<ConcertSeat> seats, Long[] concertSeatIds) {
        Map<Long, ConcertSeat> seatMap = new HashMap<>();
        Set<Long> concertSeatIdSet = new HashSet<>(Arrays.asList(concertSeatIds));

        for (ConcertSeat seat : seats) {
            // 예약할 좌석 ID와 매칭되는 좌석만 추가
            if (concertSeatIdSet.contains(seat.getConcertSeatId())) {
                seatMap.put(seat.getConcertSeatId(), seat);
            }
        }
        return seatMap;
    }

    private List<ConcertReservation> reserveSeats(Long concertId, Long concertScheduleId, Long[] concertSeatIds, Long userId) {
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

    private ConcertReservationRes buildReservationResponse(Concert concert, List<ConcertReservation> reservations, Map<Long, ConcertSeat> seatMap) {
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

        return ConcertReservationRes.of(reservations.get(0).getUserId(), concert.getConcertTitle(), seatResponseList, totalPrice);
    }
}
