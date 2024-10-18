package com.hhplus.tdd.concert.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Concert {
    private Long concertId;       // 콘서트 ID
    private String concertTitle;  // 콘서트 제목

    public static Concert of(Long concertId, String concertTitle) {
        return new Concert(concertId, concertTitle);
    }
}
