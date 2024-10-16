package com.hhplus.tdd.app.balance.adapter.controller;

import com.hhplus.tdd.app.balance.adapter.response.BalanceRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/balances")
public class BalanceController {

    // 잔액 조회
    @GetMapping("/balance/")
    public ResponseEntity<BalanceRes> balance(@RequestHeader("user-id") long userId){

        BalanceRes res = BalanceRes.builder()
                .userId(userId)
                .amount(3000)
                .build();

        return ResponseEntity.ok(res);
    }

    // 잔액 충전
    @PatchMapping("/balance/charge")
    public ResponseEntity<BalanceRes> charge(@RequestHeader("user-id") long userId, @RequestBody long chargeAmount) {
        BalanceRes res = BalanceRes.builder()
                .userId(userId)
                .amount(3000 + chargeAmount)
                .build();
        return ResponseEntity.ok(res);
    }

}
