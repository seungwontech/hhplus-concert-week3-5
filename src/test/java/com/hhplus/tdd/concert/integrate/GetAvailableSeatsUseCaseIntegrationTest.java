package com.hhplus.tdd.concert.integrate;

import com.hhplus.tdd.concert.application.usecase.GetAvailableSeatsUseCase;
import com.hhplus.tdd.concert.presentation.response.SeatRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GetAvailableSeatsUseCaseIntegrationTest {

    @Autowired
    private GetAvailableSeatsUseCase getAvailableSeatsUseCase;

    @Test
    void testExecuteSuccess() {
        // given
        Long concertId = 1L;
        Long concertScheduleId = 1L;

        // when
        SeatRes response = getAvailableSeatsUseCase.execute(concertId, concertScheduleId);

        // then
        assertThat(response.getConcertId()).isEqualTo(concertId);
        assertThat(response.getConcertScheduleId()).isEqualTo(concertScheduleId);
        assertThat(response.getSeats()).isNotEmpty();

        assertEquals(2, response.getSeats().size());
        assertThat(response.getSeats().get(0).getReserveYn()).isEqualTo("N");
        assertThat(response.getSeats().get(0).getSeatNumber()).isEqualTo(1);
    }

}
