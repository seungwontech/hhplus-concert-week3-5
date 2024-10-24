package com.hhplus.tdd.concert.integrate;

import com.hhplus.tdd.concert.application.usecase.ConcertReservationUseCase;
import com.hhplus.tdd.concert.domain.model.ConcertReservation;
import com.hhplus.tdd.concert.domain.model.ConcertReservationResult;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.model.ReservationStatus;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertReservationRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.request.ConcertReservationReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcertReservationUseCaseIntegrationTest {

    @Autowired
    private ConcertReservationUseCase concertReservationUseCase;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertReservationRepository concertReservationRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    private Long concertId;
    private Long concertScheduleId;
    private Long userId;
    private Long[] concertSeatIds;

    @BeforeEach
    void setUp() {
        concertId = 1L;
        concertScheduleId = 1L;
        userId = 1L;
        concertSeatIds = new Long[]{1L, 2L};
    }

    @Test
    void testExecuteReservation() {
        // given
        ConcertReservationReq request = ConcertReservationReq.of(userId, concertSeatIds);

        // when
        ConcertReservationResult response = concertReservationUseCase.execute(concertId, concertScheduleId, request);

        // then
        assertThat(response.getUserId()).isEqualTo(userId);
        assertThat(response.getConcertName()).isEqualTo("봄 콘서");
        assertThat(response.getSeats()).hasSize(concertSeatIds.length);

        // 예약이 제대로 저장되었는지 검증
        List<ConcertReservation> reservations = concertReservationRepository.findAll();
        assertThat(reservations).hasSize(concertSeatIds.length);
        for (ConcertReservation reservation : reservations) {
            assertThat(reservation.getUserId()).isEqualTo(userId);
            assertThat(reservation.getConcertScheduleId()).isEqualTo(concertScheduleId);
            assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.WAITING.toString());
        }

        // 좌석 예약 상태 검증
        List<ConcertSeat> updatedSeats = concertSeatRepository.findByConcertIdAndConcertScheduleIdAndConcertSeatIdIn(concertId, concertScheduleId, concertSeatIds);
        for (ConcertSeat seat : updatedSeats) {
            assertThat(seat.getReserveYn()).isEqualTo("Y");
        }
    }
}
