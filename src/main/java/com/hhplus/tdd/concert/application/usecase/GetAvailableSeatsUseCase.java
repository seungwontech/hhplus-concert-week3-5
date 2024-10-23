package com.hhplus.tdd.concert.application.usecase;

import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertScheduleRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.response.SeatRes;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class GetAvailableSeatsUseCase {

    private final ConcertRepository concertRepository;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;


    public SeatRes execute(Long concertId, Long concertScheduleId) {

        Concert concert = concertRepository.getConcertOrThrow(concertId);

        ConcertSchedule schedule = concertScheduleRepository.getConcertScheduleOrThrow(concertId, concertScheduleId);

        List<ConcertSeat> seats = concertSeatRepository.getConcertSeatsByScheduleOrThrow(concertId, concertScheduleId, "N");

        List<SeatRes.Seat> mapToSeatsResponse = mapToSeatResponse(seats);

        return buildSeatResponse(concert, concertScheduleId, schedule.getConcertDate(), mapToSeatsResponse);
    }

    // 좌석 정보를 응답 형식으로 매핑하는 메서드.
    public List<SeatRes.Seat> mapToSeatResponse(List<ConcertSeat> seats) {
        List<SeatRes.Seat> seatResponseList = new ArrayList<>();
        for (ConcertSeat seat : seats) {
            seatResponseList.add(SeatRes.Seat.of(seat.getConcertSeatId(), seat.getSeatNumber(), seat.getSeatPrice(), seat.getReserveYn()));
        }
        return seatResponseList;
    }

    public SeatRes buildSeatResponse(Concert concert, Long concertScheduleId, LocalDateTime concertDate, List<SeatRes.Seat> reservedSeats) {
        if (reservedSeats.isEmpty()) {
            log.error("콘서트 예약 가능한 좌석이 없습니다.", reservedSeats);
            throw new CoreException(ErrorType.CONCERT_SEAT_AVAILABLE_NOT_FOUND, reservedSeats);
        }
        return SeatRes.of(concert.getConcertId(), concertScheduleId, concertDate, reservedSeats);
    }
}
