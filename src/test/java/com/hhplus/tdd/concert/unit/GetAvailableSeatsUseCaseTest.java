package com.hhplus.tdd.concert.unit;

import com.hhplus.tdd.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertScheduleRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.response.SeatRes;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class GetAvailableSeatsUseCaseTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @InjectMocks
    private GetAvailableSeatsUseCase getAvailableSeatsUseCase;

    private final Long concertId = 1L;
    private final Long concertScheduleId = 1L;

    @Test
    public void 실행_성공() {
        // given
        Concert concert = new Concert(concertId, "2024 봄 콘서트");
        ConcertSchedule schedule = new ConcertSchedule(concertScheduleId, concertId, LocalDateTime.now(), 100);
        ConcertSeat seat1 = new ConcertSeat(1L, concertScheduleId, concertId, 1, 50000, "N", 1L);
        ConcertSeat seat2 = new ConcertSeat(2L, concertScheduleId, concertId, 2, 60000, "N", 1L);
        List<ConcertSeat> seats = Arrays.asList(seat1, seat2);

        doReturn(concert).when(concertRepository).getConcertOrThrow(concertId);
        doReturn(schedule).when(concertScheduleRepository).getConcertScheduleOrThrow(concertId, concertScheduleId);
        doReturn(seats).when(concertSeatRepository).getConcertSeatsByScheduleOrThrow(concertId, concertScheduleId, "N");

        // when
        SeatRes seatRes = getAvailableSeatsUseCase.execute(concertId, concertScheduleId);

        // then
        assertNotNull(seatRes);
        assertEquals(concertId, seatRes.getConcertId());
        assertEquals(concertScheduleId, seatRes.getConcertScheduleId());
        assertEquals(seats.size(), seatRes.getSeats().size());
    }


    @Test
    public void 좌석정보를응답형식으로매핑하는메서드_성공() {
        // given
        ConcertSeat seat1 = new ConcertSeat(1L, concertScheduleId, concertId, 1, 50000, "N", 1L);
        ConcertSeat seat2 = new ConcertSeat(2L, concertScheduleId, concertId, 2, 60000, "N", 1L);
        List<ConcertSeat> seats = Arrays.asList(seat1, seat2);

        // when
        List<SeatRes.Seat> seatResponses = getAvailableSeatsUseCase.mapToSeatResponse(seats);

        // then
        assertEquals(2, seatResponses.size());
        assertEquals(seat1.getConcertSeatId(), seatResponses.get(0).getConcertSeatId());
        assertEquals(seat2.getConcertSeatId(), seatResponses.get(1).getConcertSeatId());
    }

    @Test
    public void 좌석정보를빌드_성공() {
        // given
        List<SeatRes.Seat> reservedSeats = Collections.singletonList(SeatRes.Seat.of(1L, 1, 50000, "N"));
        Concert concert = new Concert(concertId, "2024 봄 콘서트");
        LocalDateTime concertDate = LocalDateTime.now();

        // when
        SeatRes seatRes = getAvailableSeatsUseCase.buildSeatResponse(concert, concertScheduleId, concertDate, reservedSeats);

        // then
        assertEquals(concertId, seatRes.getConcertId());
        assertEquals(concertScheduleId, seatRes.getConcertScheduleId());
        assertEquals(reservedSeats.size(), seatRes.getSeats().size());
    }

    @Test
    public void 좌석응답빌드좌석없음_실패() {
        // given
        List<SeatRes.Seat> reservedSeats = Collections.emptyList();
        Concert concert = new Concert(concertId, "2024 봄 콘서트");
        LocalDateTime concertDate = LocalDateTime.now();

        // when & then
        CoreException exception = assertThrows(CoreException.class, () -> getAvailableSeatsUseCase.buildSeatResponse(concert, concertScheduleId, concertDate, reservedSeats));
        assertEquals(ErrorType.CONCERT_SEAT_AVAILABLE_NOT_FOUND, exception.getErrorType());
    }
}
