package com.hhplus.tdd.concert.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "PAYMENT")
public class ConcertPaymentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId; // 결제 ID

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 ID

    @Column(name = "concert_reservation_id", nullable = false)
    private Long concertReservationId; // 콘서트 예약 ID

    @Column(name = "payment_amount", nullable = false)
    private int paymentAmount; // 결제 금액

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus; // 결제 상태

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate; // 결제 날짜

    public static ConcertPaymentJpaEntity of(Long userId, Long concertReservationId, int paymentAmount, String paymentStatus, LocalDateTime paymentDate) {
        ConcertPaymentJpaEntity concertPayment = new ConcertPaymentJpaEntity();
        concertPayment.userId = userId;
        concertPayment.concertReservationId = concertReservationId;
        concertPayment.paymentAmount = paymentAmount;
        concertPayment.paymentStatus = paymentStatus;
        concertPayment.paymentDate = paymentDate;
        return concertPayment;
    }


}
