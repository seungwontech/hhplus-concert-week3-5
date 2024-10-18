package com.hhplus.tdd.waitingqueue.presentation.controller;


import com.hhplus.tdd.waitingqueue.domain.service.WaitingQueueService;
import com.hhplus.tdd.waitingqueue.presentation.response.QueuePositionRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/waiting-queue/")
public class WaitingQueueController {
    private final WaitingQueueService waitingQueueService;
    // 유저 토큰 발급 API
    @PostMapping
    public ResponseEntity<QueuePositionRes> createWaitingQueue(@RequestHeader("user-id") Long userId) {
        QueuePositionRes res = waitingQueueService.addWaitingQueue(userId);
        return ResponseEntity.ok(res);
    }

    // 대기열 순번 조회 API
    @GetMapping("/position")
    public ResponseEntity<QueuePositionRes> waitingQueuePosition(@RequestHeader("user-id") Long userId) {
        QueuePositionRes res = waitingQueueService.getWaitingQueuePosition(userId);
        return ResponseEntity.ok(res);
    }
}
