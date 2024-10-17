package com.hhplus.tdd.concert.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "CONCERT")
public class ConcertJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id")
    private Long concertId; // 콘서트 ID

    @Column(name = "concert_title", nullable = false)
    private String concertTitle; // 콘서트 제목

    // 정적 팩토리 메서드 of()
    public static ConcertJpaEntity of(Long concertId, String concertTitle) {
        return new ConcertJpaEntity(concertId, concertTitle);
    }
}
