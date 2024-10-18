package com.hhplus.tdd.balance.presentation.controller;

import com.hhplus.tdd.balance.domain.service.BalanceService;
import com.hhplus.tdd.balance.presentation.response.BalanceRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/balances")
public class BalanceController {
    private final BalanceService balanceService;
    // 잔액 조회
    @GetMapping("/balance/")
    public ResponseEntity<BalanceRes> balance(@RequestHeader("user-id") Long userId){
        BalanceRes res = balanceService.getBalance(userId);
        return ResponseEntity.ok(res);
    }

    // 잔액 충전
    @PutMapping("/balance/charge")
    public ResponseEntity<BalanceRes> charge(@RequestHeader("user-id") Long userId, @RequestBody int chargeAmount) {
        BalanceRes res = balanceService.charge(userId, chargeAmount);
        return ResponseEntity.ok(res);
    }

}
