package com.hhplus.tdd.app.queue.adapter.controller;


import com.hhplus.tdd.app.queue.adapter.response.QueuePositionRes;
import com.hhplus.tdd.app.queue.adapter.response.QueueRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/waiting-queue/")
public class QueueController {

    // 유저 토큰 발급 API
    @PostMapping
    public ResponseEntity<QueueRes> createWaitingQueue(@RequestHeader("user-id") long userId) {
        QueueRes res = QueueRes.builder()
                .userId(1)
                .token("a1s2d3")
                .tokenCreated(LocalDateTime.now())
                .tokenExpiry(LocalDateTime.now().plusMinutes(5L))
                .tokenStatus("대기")
                .build();
        return ResponseEntity.ok(res);
    }

    // 대기열 순번 조회 API
    @GetMapping("/position")
    public ResponseEntity<QueuePositionRes> waitingQueuePosition(@RequestHeader("user-id") long userId) {
        QueuePositionRes res = QueuePositionRes.builder()
                .userId(1)
                .position(40)
                .token("a1s2d3")
                .tokenCreated(LocalDateTime.now())
                .tokenExpiry(LocalDateTime.now().plusMinutes(5L))
                .tokenStatus("대기")
                .build();
        return ResponseEntity.ok(res);
    }
}
