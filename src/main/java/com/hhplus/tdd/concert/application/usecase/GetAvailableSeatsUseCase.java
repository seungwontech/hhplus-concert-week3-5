package com.hhplus.tdd.concert.application.usecase;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.service.ConcertService;
import com.hhplus.tdd.concert.exception.ConcertErrorResult;
import com.hhplus.tdd.concert.exception.ConcertException;
import com.hhplus.tdd.concert.presentation.response.SeatRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetAvailableSeatsUseCase {

    private final ConcertService concertService;

    public SeatRes execute(Long concertId, Long concertScheduleId) {

        Concert concert = concertService.getConcert(concertId);

        ConcertSchedule concertSchedule = concertService.getConcertSchedule(concertId, concertScheduleId);

        List<ConcertSeat> seats = concertService.getConcertSeatsBySchedule(concertId, concertScheduleId);

        List<SeatRes.Seat> reservedSeats = filterReservedSeats(seats);

        return buildSeatResponse(concert, concertScheduleId, concertSchedule.getConcertDate(), reservedSeats);
    }

    private List<SeatRes.Seat> filterReservedSeats(List<ConcertSeat> seats) {
        List<SeatRes.Seat> reservedSeats = new ArrayList<>();
        for (ConcertSeat seat : seats) {
            if ("N".equals(seat.getReserveYn())) {
                reservedSeats.add(SeatRes.Seat.of(seat.getConcertSeatId(), seat.getSeatNumber(), seat.getSeatPrice(), seat.getReserveYn()));
            }
        }
        return reservedSeats;
    }

    private SeatRes buildSeatResponse(Concert concert, Long concertScheduleId, LocalDateTime concertDate, List<SeatRes.Seat> reservedSeats) {
        if (reservedSeats.isEmpty()) {
            throw new ConcertException(ConcertErrorResult.CONCERT_SEAT_AVAILABLE_NOT_FOUND);
        }
        return SeatRes.of(concert.getConcertId(), concertScheduleId, concertDate, reservedSeats);
    }
}
