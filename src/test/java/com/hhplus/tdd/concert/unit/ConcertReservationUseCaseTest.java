package com.hhplus.tdd.concert.unit;

import com.hhplus.tdd.concert.application.usecase.ConcertReservationUseCase;
import com.hhplus.tdd.concert.domain.model.Concert;
import com.hhplus.tdd.concert.domain.model.ConcertReservation;
import com.hhplus.tdd.concert.domain.model.ConcertSeat;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertReservationRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.presentation.request.ConcertReservationReq;
import com.hhplus.tdd.concert.presentation.response.ConcertReservationRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ConcertReservationUseCaseTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ConcertReservationRepository concertReservationRepository;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @InjectMocks
    private ConcertReservationUseCase concertReservationUseCase;

    @Test
    void execute_성공() {
        // given
        Long concertId = 1L;
        Long concertScheduleId = 2L;
        Long userId = 3L;
        Long[] seatIds = {1L, 2L};

        Concert concert = new Concert(concertId, "2024 봄 콘서트");
        ConcertSeat seat1 = new ConcertSeat(1L, concertScheduleId, concertId, 1, 50000, "N");
        ConcertSeat seat2 = new ConcertSeat(2L, concertScheduleId, concertId, 2, 60000, "N");
        List<ConcertSeat> seats = List.of(seat1, seat2);

        ConcertReservationReq reservationReq = new ConcertReservationReq(userId, seatIds);

        doReturn(concert).when(concertRepository).getConcert(concertId);
        doReturn(seats).when(concertSeatRepository).getConcertSeatsBySchedule(concertId, concertScheduleId, "N");
        doNothing().when(concertSeatRepository).saveAll(anyList());
        // when
        ConcertReservationRes result = concertReservationUseCase.execute(concertId, concertScheduleId, reservationReq);

        // then
        assertEquals(userId, result.getUserId());
        assertEquals(concert.getConcertTitle(), result.getConcertName());
        assertEquals(2, result.getSeats().size());
        assertEquals(110000, result.getTotalPrice()); // 좌석 가격 합계
    }


    @Test
    void mapSeatByIds_성공() {
        // given
        Long[] seatIds = {1L, 2L};
        ConcertSeat seat1 = new ConcertSeat(1L, 1L, 1L, 1, 50000, "N");
        ConcertSeat seat2 = new ConcertSeat(2L, 1L, 1L, 2, 60000, "N");
        List<ConcertSeat> seats = List.of(seat1, seat2);

        // when
        Map<Long, ConcertSeat> seatMap = concertReservationUseCase.mapSeatByIds(seats, seatIds);

        // then
        assertEquals(2, seatMap.size());
        assertEquals(seat1, seatMap.get(1L));
        assertEquals(seat2, seatMap.get(2L));
    }


    @Test
    void markSeatsAsReserved_성공() {
        // given
        Long concertId = 1L;
        Long concertScheduleId = 2L;
        Long[] seatIds = {1L, 2L};

        ConcertSeat seat1 = new ConcertSeat(1L, concertScheduleId, concertId, 1, 50000, "N");
        ConcertSeat seat2 = new ConcertSeat(2L, concertScheduleId, concertId, 2, 60000, "N");
        List<ConcertSeat> seats = List.of(seat1, seat2);

        doReturn(seats).when(concertSeatRepository).findByConcertIdAndConcertScheduleIdAndConcertSeatIdIn(concertId, concertScheduleId, seatIds);

        // when
        List<ConcertSeat> updatedSeats = concertReservationUseCase.markSeatsAsReserved(concertId, concertScheduleId, seatIds);

        // then
        assertEquals(2, updatedSeats.size());
        assertEquals("Y", updatedSeats.get(0).getReserveYn());
        assertEquals("Y", updatedSeats.get(1).getReserveYn());
    }

    @Test
    void createReservationList_성공() {
        // given
        Long concertScheduleId = 2L;
        Long[] seatIds = {1L, 2L};
        Long userId = 3L;

        // when
        List<ConcertReservation> reservations = concertReservationUseCase.createReservationList(concertScheduleId, seatIds, userId);

        // then
        assertEquals(2, reservations.size());
        assertEquals(userId, reservations.get(0).getUserId());
        assertEquals(concertScheduleId, reservations.get(0).getConcertScheduleId());
        assertEquals(seatIds[0], reservations.get(0).getConcertSeatId());
        assertEquals("WAITING", reservations.get(0).getReservationStatus());
    }

    @Test
    void buildReservationResult_성공() {
        // given
        Concert concert = new Concert(1L, "2024 봄 콘서트");
        ConcertReservation reservation = ConcertReservation.of(null, 3L, 2L, 1L, "WAITING", LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
        Map<Long, ConcertSeat> seatMap = new HashMap<>();
        seatMap.put(1L, new ConcertSeat(1L, 2L, 1L, 1, 50000, "Y"));

        // when
        ConcertReservationRes result = concertReservationUseCase.buildReservationResult(concert, List.of(reservation), seatMap);

        // then
        assertEquals(3L, result.getUserId());
        assertEquals(concert.getConcertTitle(), result.getConcertName());
        assertEquals(1, result.getSeats().size());
        assertEquals(50000, result.getTotalPrice());
    }

}
