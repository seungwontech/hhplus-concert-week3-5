package com.hhplus.tdd.concert.infra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "PAYMENT")
public class PaymentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId; // 결제 ID

    @Column(nullable = false)
    private Long userId; // 사용자 ID

    @Column(nullable = false)
    private Long concertReservationId; // 콘서트 예약 ID

    @Column(nullable = false)
    private int paymentAmount; // 결제 금액

    @Column(nullable = false)
    private String paymentStatus; // 결제 상태

    @Column(nullable = false)
    private LocalDateTime paymentDate; // 결제 날짜

    public static PaymentJpaEntity of(Long userId, Long concertReservationId, int paymentAmount, String paymentStatus, LocalDateTime paymentDate) {
        PaymentJpaEntity concertPayment = new PaymentJpaEntity();
        concertPayment.userId = userId;
        concertPayment.concertReservationId = concertReservationId;
        concertPayment.paymentAmount = paymentAmount;
        concertPayment.paymentStatus = paymentStatus;
        concertPayment.paymentDate = paymentDate;
        return concertPayment;
    }


}
