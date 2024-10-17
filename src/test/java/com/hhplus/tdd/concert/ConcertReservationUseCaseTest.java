package com.hhplus.tdd.concert;

import com.hhplus.tdd.concert.application.usecase.ConcertReservationUseCase;
import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.service.ConcertService;
import com.hhplus.tdd.concert.presentation.request.ConcertReservationReq;
import com.hhplus.tdd.concert.presentation.response.ConcertReservationRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConcertReservationUseCaseTest {
    @InjectMocks
    private ConcertReservationUseCase concertReservationUseCase;

    @Mock
    private ConcertService concertService;

    @Test
    void 좌석예약요청_성공() {
        // given
        Long concertId = 1L;
        Long concertScheduleId = 1L; // concertScheduleId 추가
        Long userId = 1L; // userId 추가
        Concert concert = new Concert(concertId, "Concert Title");
        ConcertSeat seat1 = new ConcertSeat(1L, concertScheduleId, 1L, 1, 40000, "N");
        ConcertSeat seat2 = new ConcertSeat(2L, concertScheduleId, 1L, 2, 40000, "N");

        ConcertReservationReq reservationReq = new ConcertReservationReq(userId, new Long[]{1L, 2L}); // 필요한 필드 추가

        // Mockito doReturn 사용
        doReturn(concert).when(concertService).getConcert(concertId);
        doReturn(Arrays.asList(seat1, seat2)).when(concertService).getConcertSeatsBySchedule(concertId, concertScheduleId);

        // when
        ConcertReservationRes response = concertReservationUseCase.execute(concertId, concertScheduleId, reservationReq);

        // then
        assertEquals(userId, response.getUserId());
        assertEquals("Concert Title", response.getConcertName());
        assertEquals(2, response.getSeats().size());
        assertEquals(80000, response.getTotalPrice());

        verify(concertService).saveReservations(anyList());
    }
}
