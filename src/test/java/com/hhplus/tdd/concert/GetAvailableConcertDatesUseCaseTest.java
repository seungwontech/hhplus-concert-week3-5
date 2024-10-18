package com.hhplus.tdd.concert;

import com.hhplus.tdd.concert.application.usecase.GetAvailableConcertDatesUseCase;
import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.service.ConcertService;
import com.hhplus.tdd.concert.exception.ConcertErrorResult;
import com.hhplus.tdd.concert.exception.ConcertException;
import com.hhplus.tdd.concert.presentation.response.ScheduleRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class GetAvailableConcertDatesUseCaseTest {

    @Mock
    private ConcertService concertService;

    @InjectMocks
    private GetAvailableConcertDatesUseCase getAvailableConcertDatesUseCase;

    @Test
    void 예약가능한날짜조회_성공() {
        // given
        Long concertId = 1L;
        Concert concert = new Concert(concertId, "Test Concert");

        ConcertSchedule schedule1 = new ConcertSchedule(1L, concertId, LocalDateTime.of(2024, 10, 20, 19, 0), 2);
        ConcertSchedule schedule2 = new ConcertSchedule(2L, concertId, LocalDateTime.of(2024, 10, 20, 19, 0), 2);

        ConcertSeat seat1 = new ConcertSeat(1L, 1L, 1L, 1, 1000, "Y");
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, 1L, 2, 1000, "Y");
        ConcertSeat seat3 = new ConcertSeat(3L, 2L, 1L, 3, 1000, "N");

        doReturn(concert).when(concertService).getConcert(concertId);
        doReturn(Arrays.asList(schedule1, schedule2)).when(concertService).getConcertSchedules(concertId);
        doReturn(Arrays.asList(seat1, seat2, seat3)).when(concertService).getConcertSeats(concertId);

        // when
        ScheduleRes result = getAvailableConcertDatesUseCase.execute(concertId);

        // then
        assertNotNull(result);
        assertEquals(concertId, result.getConcertId());
        assertEquals(1, result.getSchedules().size());
    }

    @Test
    void 예약가능한날짜조회_실패_예약이가능한날짜가없음() {
        // given
        Long concertId = 1L;
        Concert concert = new Concert(concertId, "Test Concert");

        ConcertSchedule schedule1 = new ConcertSchedule(1L, concertId, LocalDateTime.of(2024, 10, 20, 19, 0), 2);

        ConcertSeat seat1 = new ConcertSeat(1L, 1L, 1L, 1, 1000, "Y");
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, 1L, 2, 1000, "Y");


        doReturn(concert).when(concertService).getConcert(concertId);
        doReturn(Arrays.asList(schedule1)).when(concertService).getConcertSchedules(concertId);
        doReturn(Arrays.asList(seat1, seat2)).when(concertService).getConcertSeats(concertId);

        // when & then
        ConcertException exception = assertThrows(ConcertException.class, () -> {
            getAvailableConcertDatesUseCase.execute(concertId);
        });
        assertEquals(ConcertErrorResult.CONCERT_SCHEDULE_AVAILABLE_NOT_FOUND, exception.getErrorResult());
    }
}
