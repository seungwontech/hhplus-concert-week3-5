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
        // given: 콘서트 ID와 일정 ID
        Long concertId = 1L;
        Long concertScheduleId = 1L;

        // when: 유스케이스 실행
        SeatRes response = getAvailableSeatsUseCase.execute(concertId, concertScheduleId);

        // then: 좌석 정보가 정확히 반환되는지 검증
        assertThat(response.getConcertId()).isEqualTo(concertId);
        assertThat(response.getConcertScheduleId()).isEqualTo(concertScheduleId);
        assertThat(response.getSeats()).isNotEmpty();

        assertEquals(2, response.getSeats().size());
        assertThat(response.getSeats().get(0).getReserveYn()).isEqualTo("N");
        assertThat(response.getSeats().get(0).getSeatNumber()).isEqualTo(1);
    }

}
