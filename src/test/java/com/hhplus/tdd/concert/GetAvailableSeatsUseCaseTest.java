package com.hhplus.tdd.concert;

import com.hhplus.tdd.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.service.ConcertService;
import com.hhplus.tdd.concert.exception.ConcertErrorResult;
import com.hhplus.tdd.concert.exception.ConcertException;
import com.hhplus.tdd.concert.presentation.response.SeatRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class GetAvailableSeatsUseCaseTest {

    @Mock
    private ConcertService concertService;

    @InjectMocks
    private GetAvailableSeatsUseCase getAvailableSeatsUseCase;

    @Test
    void 예약가능한좌석조회_성공() {
        // given
        Concert concert = new Concert(1L, "콘서트 A");
        ConcertSchedule concertSchedule = new ConcertSchedule(1L, concert.getConcertId(), LocalDateTime.now(), 50);
        List<ConcertSeat> concertSeats = Arrays.asList(
                new ConcertSeat(1L, concertSchedule.getConcertScheduleId(), 1L, 1, 40000, "N"),
                new ConcertSeat(2L, concertSchedule.getConcertScheduleId(), 1L, 2, 50000, "Y"),
                new ConcertSeat(3L, concertSchedule.getConcertScheduleId(), 1L, 3, 60000, "N")
        );

        given(concertService.getConcert(anyLong())).willReturn(concert);
        given(concertService.getConcertSchedule(anyLong(), anyLong())).willReturn(concertSchedule);
        given(concertService.getConcertSeatsBySchedule(anyLong(), anyLong())).willReturn(concertSeats);

        // when
        SeatRes seatRes = getAvailableSeatsUseCase.execute(concert.getConcertId(), concertSchedule.getConcertScheduleId());
        System.out.println(seatRes.getSeats().get(0).getSeatPrice());
        // then
        assertNotNull(seatRes);
        assertEquals(concert.getConcertId(), seatRes.getConcertId());
        assertEquals(concertSchedule.getConcertScheduleId(), seatRes.getConcertScheduleId());
        assertEquals(2, seatRes.getSeats().size()); // 예약 가능한 좌석이 2개 존재

        SeatRes.Seat seat1 = seatRes.getSeats().get(0);
        assertEquals(1L, seat1.getConcertSeatId());
        assertEquals(1, seat1.getSeatNumber());
        assertEquals(40000, seat1.getSeatPrice());
        assertEquals("N", seat1.getReserveYn());

        SeatRes.Seat seat2 = seatRes.getSeats().get(1);
        assertEquals(3L, seat2.getConcertSeatId());
        assertEquals(3, seat2.getSeatNumber());
        assertEquals(60000, seat2.getSeatPrice());
        assertEquals("N", seat2.getReserveYn());

    }

    @Test
    void 예약가능한좌석조회_실패_예약가능한좌석이없음() {
        // given
        Concert concert = new Concert(1L, "콘서트 A");
        ConcertSchedule concertSchedule = new ConcertSchedule(1L, 1L, LocalDateTime.now(), 50);

        ConcertSeat seat1 = new ConcertSeat(1L, 1L, 1L, 1, 1000, "Y");
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, 1L, 2, 1000, "Y");

        doReturn(concert).when(concertService).getConcert(1L);
        doReturn(concertSchedule).when(concertService).getConcertSchedule(1L, 1L);
        doReturn(Arrays.asList(seat1, seat2)).when(concertService).getConcertSeatsBySchedule(1L, 1L);

        // when & then
        ConcertException exception = assertThrows(ConcertException.class, () -> {
            getAvailableSeatsUseCase.execute(concert.getConcertId(), concertSchedule.getConcertScheduleId());
        });

        assertEquals(ConcertErrorResult.CONCERT_SEAT_AVAILABLE_NOT_FOUND, exception.getErrorResult());
    }
}
