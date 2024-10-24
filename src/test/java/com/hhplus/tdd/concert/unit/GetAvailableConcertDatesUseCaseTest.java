package com.hhplus.tdd.concert.unit;

import com.hhplus.tdd.concert.application.usecase.GetAvailableConcertDatesUseCase;
import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertScheduleRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.response.ScheduleRes;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class GetAvailableConcertDatesUseCaseTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @InjectMocks
    private GetAvailableConcertDatesUseCase getAvailableConcertDatesUseCase;

    final Long concertId = 1L;
    final Concert concert = new Concert(concertId, "Test Concert");

    @Test
    void 예약가능한날짜조회_성공() {
        // given

        ConcertSchedule schedule1 = new ConcertSchedule(1L, concertId, LocalDateTime.of(2024, 10, 20, 19, 0), 2);
        ConcertSchedule schedule2 = new ConcertSchedule(2L, concertId, LocalDateTime.of(2024, 10, 21, 19, 0), 2);

        ConcertSeat seat1 = new ConcertSeat(1L, 1L, concertId, 1, 1000, "Y");
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, concertId, 2, 1000, "Y");
        ConcertSeat seat3 = new ConcertSeat(3L, 2L, concertId, 3, 1000, "N");

        doReturn(concert).when(concertRepository).getConcertOrThrow(concertId);
        doReturn(Arrays.asList(schedule1, schedule2)).when(concertScheduleRepository).getConcertSchedules(concertId);
        doReturn(Arrays.asList(seat1, seat2, seat3)).when(concertSeatRepository).getConcertSeats(concertId);

        // when
        ScheduleRes result = getAvailableConcertDatesUseCase.execute(concertId);

        // then
        assertNotNull(result);
        assertEquals(concertId, result.getConcertId());
        assertEquals(1, result.getSchedules().size()); // 예약 가능한 일정 1개
    }

    @Test
    void countReservedSeatsByScheduleId_예약좌석수계산_성공() {
        // given
        ConcertSeat seat1 = new ConcertSeat(1L, 1L, concertId, 1, 1000, "Y");
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, concertId, 2, 1000, "Y");
        ConcertSeat seat3 = new ConcertSeat(3L, 2L, concertId, 3, 1000, "Y");
        List<ConcertSeat> seats = List.of(seat1, seat2, seat3);
        System.out.println(seats.get(0));
        System.out.println(seats.get(1));
        System.out.println(seats.get(2));
        // when
        Map<Long, Integer> reservedSeatsMap = getAvailableConcertDatesUseCase.countReservedSeatsByScheduleId(seats);
        System.out.println(reservedSeatsMap.getOrDefault(1L, 0));
        System.out.println(reservedSeatsMap.getOrDefault(2L, 0));
        // then
        assertEquals(2, reservedSeatsMap.getOrDefault(1L, 0));
        assertEquals(1, reservedSeatsMap.getOrDefault(2L, 0));
    }

    @Test
    void filterAvailableSchedules_사용가능한일정필터링_성공() {
        // given
        ConcertSchedule schedule1 = new ConcertSchedule(1L, concertId, LocalDateTime.of(2024, 10, 20, 19, 0), 2);
        ConcertSchedule schedule2 = new ConcertSchedule(2L, concertId, LocalDateTime.of(2024, 10, 21, 19, 0), 2);
        Map<Long, Integer> reservedSeatsMap = Map.of(1L, 2, 2L, 1);

        // when
        List<ScheduleRes.Schedule> availableSchedules = getAvailableConcertDatesUseCase.filterAvailableSchedules(
                Arrays.asList(schedule1, schedule2), reservedSeatsMap);

        // then
        assertEquals(1, availableSchedules.size());
        assertEquals(schedule2.getConcertScheduleId(), availableSchedules.get(0).getConcertScheduleId());
    }

    @Test
    void buildScheduleResponse_스케줄생성_성공() {
        // given
        ConcertSchedule schedule1 = new ConcertSchedule(1L, concertId, LocalDateTime.of(2024, 10, 20, 19, 0), 2);
        List<ScheduleRes.Schedule> availableSchedules = List.of(ScheduleRes.Schedule.of(
                schedule1.getConcertScheduleId(),
                schedule1.getConcertDate(),
                schedule1.getTotalSeats()));

        // when
        ScheduleRes response = getAvailableConcertDatesUseCase.buildScheduleResponse
                (concert, availableSchedules);

        // then
        assertEquals(concert.getConcertId(), response.getConcertId());
        assertEquals(availableSchedules.size(), response.getSchedules().size());
    }


    @Test
    void 예약가능한날짜조회_실패_일정이_없음() {
        // given
        Long concertId = 1L;
        Concert concert = new Concert(concertId, "Test Concert");

        doReturn(concert).when(concertRepository).getConcertOrThrow(concertId);
        doReturn(Collections.emptyList()).when(concertScheduleRepository).getConcertSchedules(concertId);

        // when & then
        CoreException exception = assertThrows(CoreException.class, () -> {
            getAvailableConcertDatesUseCase.execute(concertId);
        });
        assertEquals(ErrorType.CONCERT_SCHEDULE_NOT_FOUND, exception.getErrorType());
    }

}
