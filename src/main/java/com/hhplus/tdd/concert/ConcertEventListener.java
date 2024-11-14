package com.hhplus.tdd.concert;

import com.hhplus.tdd.balance.domain.model.Balance;
import com.hhplus.tdd.balance.domain.repository.BalanceRepository;
import com.hhplus.tdd.config.exception.CoreException;
import com.hhplus.tdd.config.exception.ErrorType;
import com.hhplus.tdd.waitingqueue.domain.model.WaitingQueue;
import com.hhplus.tdd.waitingqueue.domain.repository.WaitingQueueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertEventListener {

    private final WaitingQueueRepository waitingQueueRepository;
    private final BalanceRepository balanceRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void expireWaitingQueue(ConcertEvent event) {
        if (!"COMPLETED".equals(event.getType())){
            return;
        }

        String token = String.valueOf(event.getData().get("token"));
        WaitingQueue waitingQueue = waitingQueueRepository.getWaitingQueueToken(token);
        waitingQueue.expire();
        waitingQueueRepository.save(waitingQueue);
        log.info("Waiting queue expired for token: {}", token);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deductUserPoints(ConcertEvent event) {
        if (!"COMPLETED".equals(event.getType())) {
            return;
        }

        Long userId = (Long) event.getData().get("userId");
        int totalPrice = (int) event.getData().get("totalPrice");

        Balance balance = balanceRepository.getBalance(userId);
        if (balance == null) {
            throw new CoreException(ErrorType.BALANCE_NOT_FOUND, userId);
        }
        Balance updatedBalance = balance.use(totalPrice);
        balanceRepository.save(updatedBalance);

        log.info("User points deducted for userId: {}, amount: {}", userId, totalPrice);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void sendEmail(ConcertEvent event) {
        if (!"COMPLETED".equals(event.getType())) {
            return;
        }
        try {
            Thread.sleep(5000); // 5초
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("이메일로 보내기");
    }
}
