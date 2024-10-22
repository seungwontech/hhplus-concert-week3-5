package com.hhplus.tdd.concert.integrate;

import com.hhplus.tdd.concert.application.usecase.GetAvailableConcertDatesUseCase;
import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertSchedule;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertScheduleRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.response.ScheduleRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GetAvailableConcertDatesUseCaseIntegrationTest {

    @Autowired
    private GetAvailableConcertDatesUseCase getAvailableConcertDatesUseCase;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertScheduleRepository concertScheduleRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @Test
    void testExecuteSuccess() {

        Concert concert = new Concert(1L, "봄 콘서");
        concertRepository.save(concert);
        Long concertId = concert.getConcertId();

        ConcertSchedule schedule1 = new ConcertSchedule(1L, concertId, LocalDateTime.now(), 50);
        ConcertSchedule schedule2 = new ConcertSchedule(2L, concertId, LocalDateTime.now().plusDays(1), 50);
        concertScheduleRepository.saveAll(List.of(schedule1, schedule2));

        ConcertSeat seat1 = new ConcertSeat(1L, schedule1.getConcertScheduleId(), concertId, 1, 1000, "Y");
        ConcertSeat seat2 = new ConcertSeat(2L, schedule1.getConcertScheduleId(), concertId, 2, 1000, "Y");
        ConcertSeat seat3 = new ConcertSeat(3L, schedule2.getConcertScheduleId(), concertId, 1, 1000, "N");
        concertSeatRepository.saveAll(List.of(seat1, seat2, seat3));
        // When
        ScheduleRes response = getAvailableConcertDatesUseCase.execute(concertId);
        // Then
        assertEquals(1L, response.getConcertId());
        assertEquals(2, response.getSchedules().size());

        ScheduleRes.Schedule schedule3 = response.getSchedules().get(0);
        ScheduleRes.Schedule schedule4 = response.getSchedules().get(1);

        assertEquals(1L, schedule3.getConcertScheduleId());
        assertEquals(2L, schedule4.getConcertScheduleId());
        assertEquals(50, schedule3.getTotalSeats());
        assertEquals(50, schedule4.getTotalSeats());
    }
}
