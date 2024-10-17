package com.hhplus.tdd.concert;

import com.hhplus.tdd.concert.domain.model.*;
import com.hhplus.tdd.concert.domain.repository.ConcertRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertReservationRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertScheduleRepository;
import com.hhplus.tdd.concert.domain.repository.ConcertSeatRepository;
import com.hhplus.tdd.concert.domain.service.ConcertService;
import com.hhplus.tdd.concert.exception.ConcertErrorResult;
import com.hhplus.tdd.concert.exception.ConcertException;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @Mock
    private ConcertReservationRepository concertReservationRepository;


    @Test
    public void 콘서트조회_성공() {
        // Given
        Long concertId = 1L;
        Concert expectedConcert = Concert.of(concertId, "Concert Title");
        doReturn(expectedConcert).when(concertRepository).getConcert(concertId);

        // When
        Concert result = concertService.getConcert(concertId);

        // Then
        assertNotNull(result);
        assertEquals(expectedConcert.getConcertId(), result.getConcertId());
        assertEquals(expectedConcert.getConcertTitle(), result.getConcertTitle());
    }

    @Test
    public void 콘서트조회_실패_존재하지않은콘서트() {
        // Given
        Long concertId = 1L;
        doReturn(null).when(concertRepository).getConcert(concertId);

        // When & Then
        ConcertException exception = assertThrows(ConcertException.class, () -> {
            concertService.getConcert(concertId);
        });
        assertEquals(ConcertErrorResult.CONCERT_NOT_FOUND, exception.getErrorResult());
    }

    @Test
    public void 콘서트날짜조회_성공() {
        // Given
        Long concertId = 1L;
        List<ConcertSchedule> expectedSchedules = List.of(
                ConcertSchedule.of(1L, concertId, LocalDateTime.of(2024, 10, 20, 19, 0), 100),
                ConcertSchedule.of(2L, concertId, LocalDateTime.of(2024, 10, 21, 19, 0), 100)
        );
        doReturn(expectedSchedules).when(concertScheduleRepository).getConcertSchedules(concertId);

        // When
        List<ConcertSchedule> result = concertService.getConcertSchedules(concertId);

        // Then
        assertNotNull(result);
        assertEquals(expectedSchedules.size(), result.size());
        assertEquals(expectedSchedules.get(0).getConcertScheduleId(), result.get(0).getConcertScheduleId());
        assertEquals(expectedSchedules.get(1).getConcertScheduleId(), result.get(1).getConcertScheduleId());
    }

    @Test
    public void 콘서트날짜상세조회_성공() {
        // Given
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        ConcertSchedule expectedSchedule = ConcertSchedule.of(concertScheduleId, concertId, LocalDateTime.of(2024, 10, 21, 19, 0), 100);
        doReturn(expectedSchedule).when(concertScheduleRepository).getConcertSchedule(concertId, concertScheduleId);

        // When
        ConcertSchedule result = concertService.getConcertSchedule(concertId, concertScheduleId);

        // Then
        assertNotNull(result);
        assertEquals(expectedSchedule.getConcertId(), result.getConcertId());
        assertEquals(expectedSchedule.getConcertScheduleId(), result.getConcertScheduleId());

    }

    @Test
    public void 콘서트날짜조회_실패_일정없음() {
        // Given
        Long concertId = 1L;
        doReturn(Collections.emptyList()).when(concertScheduleRepository).getConcertSchedules(concertId);

        // When & Then
        ConcertException exception = assertThrows(ConcertException.class, () -> {
            concertService.getConcertSchedules(concertId);
        });
        assertEquals(ConcertErrorResult.CONCERT_SCHEDULE_NOT_FOUND, exception.getErrorResult());
    }

    @Test
    public void 콘서트좌석조회_성공() {
        Long concertScheduleId = 1L;
        Long concertId = 1L;
        List<ConcertSeat> expectedSeats = List.of(
                ConcertSeat.of(1L, concertScheduleId, concertId, 1, 2000, "N"),
                ConcertSeat.of(2L, concertScheduleId, concertId, 2, 2000, "Y")
        );
        doReturn(expectedSeats).when(concertSeatRepository).getConcertSeats(concertId);

        List<ConcertSeat> result = concertService.getConcertSeats(concertId);

        assertNotNull(result);
        assertEquals(expectedSeats.size(), result.size());
        assertEquals(expectedSeats.get(0).getConcertSeatId(), result.get(0).getConcertSeatId());
        assertEquals(expectedSeats.get(1).getConcertSeatId(), result.get(1).getConcertSeatId());
    }

    @Test
    public void 콘서트좌석조회_실패_좌석없음() {
        Long concertId = 1L;
        doReturn(Collections.emptyList()).when(concertSeatRepository).getConcertSeats(concertId);

        ConcertException exception = assertThrows(ConcertException.class, () -> {
            concertService.getConcertSeats(concertId);
        });
        assertEquals(ConcertErrorResult.CONCERT_SEAT_NOT_FOUND, exception.getErrorResult());
    }

    @Test
    public void 콘서트해당날짜의좌석조회_성공() {
        Long concertScheduleId = 1L;
        Long concertId = 1L;
        List<ConcertSeat> expectedSeats = List.of(
                ConcertSeat.of(1L, concertScheduleId, concertId, 1, 2000, "N"),
                ConcertSeat.of(2L, concertScheduleId, concertId, 2, 2000, "Y")
        );
        doReturn(expectedSeats).when(concertSeatRepository).getConcertSeatsBySchedule(concertId, concertScheduleId);

        List<ConcertSeat> result = concertService.getConcertSeatsBySchedule(concertId, concertScheduleId);

        assertNotNull(result);
        assertEquals(expectedSeats.size(), result.size());
        assertEquals(expectedSeats.get(0).getConcertSeatId(), result.get(0).getConcertSeatId());
        assertEquals(expectedSeats.get(1).getConcertSeatId(), result.get(1).getConcertSeatId());
    }

    @Test
    void 좌석예약_성공() {
        // given
        List<ConcertReservation> reservations = Arrays.asList(
                new ConcertReservation(1L, 1L, 1L, 1L
                        , String.valueOf(ReservationStatus.WAITING)
                        , LocalDateTime.now()
                        , LocalDateTime.now().plusMinutes(5)),
                new ConcertReservation(2L, 1L, 1L, 2L
                        , String.valueOf(ReservationStatus.WAITING)
                        , LocalDateTime.now()
                        , LocalDateTime.now().plusMinutes(5))
        );

        // when
        doNothing().when(concertReservationRepository).saveAll(reservations);

        concertService.saveReservations(reservations);

        // then
        verify(concertReservationRepository, times(1)).saveAll(reservations);

    }


    @Test
    public void shouldUpdateSeatReservedYnToY() {
        // given
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long[] concertSeatIds = {1L, 2L, 3L};

        List<ConcertSeat> mockSeats = Arrays.asList(
                new ConcertSeat(1L, concertScheduleId, concertId, 10, 100, "N"),
                new ConcertSeat(2L, concertScheduleId, concertId, 11, 100, "N"),
                new ConcertSeat(3L, concertScheduleId, concertId, 12, 100, "N")
        );

        given(concertSeatRepository.findByConcertIdAndConcertScheduleIdAndSeatIdIn(concertId, concertScheduleId, concertSeatIds))
                .willReturn(mockSeats);

        // when
        concertService.saveSeatReserved(concertId, concertScheduleId, concertSeatIds);

        for (ConcertSeat seat : mockSeats) {
            assertEquals("Y", seat.updateReserveYn("Y").getReserveYn());
        }
    }
}
