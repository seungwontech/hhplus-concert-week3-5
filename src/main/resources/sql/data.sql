INSERT INTO hhplus.USER (user_name)
VALUES ('홍길동'),
       ('김철수'),
       ('이영희');

INSERT INTO hhplus.WAITING_QUEUE (user_id, token, token_expiry, token_created, token_status)
VALUES (1, 'token12345', DATE_ADD(NOW(), INTERVAL 5 MINUTE), NOW(), 'ACTIVE'),
       (2, 'token54321', DATE_ADD(NOW(), INTERVAL 5 MINUTE), NOW(), 'ACTIVE'),
       (3, 'token67890', DATE_ADD(NOW(), INTERVAL 5 MINUTE), NOW(), 'INACTIVE');

INSERT INTO hhplus.CONCERT (concert_title)
VALUES ('2024 봄 콘서트'),
       ('여름 음악 축제'),
       ('가을 재즈 공연');

INSERT INTO hhplus.CONCERT_SCHEDULE (concert_id, concert_date, total_seats)
VALUES (1, '2024-05-01 19:00:00', 100),
       (2, '2024-06-15 20:00:00', 150),
       (3, '2024-09-10 18:00:00', 200);

INSERT INTO hhplus.CONCERT_SEAT (concert_schedule_id, concert_id, seat_number, seat_price, version, reserve_yn)
VALUES (1, 1, 1, 50000, 1, 'N'),
       (1, 1, 2, 50000, 1, 'N'),
       (1, 1, 3, 60000, 1, 'N'),
       (2, 2, 1, 70000, 1, 'N'),
       (2, 2, 2, 70000, 1, 'N'),
       (3, 3, 1, 80000, 1, 'N');

INSERT INTO hhplus.CONCERT_RESERVATION (user_id, concert_schedule_id, concert_seat_id, reservation_status,
                                        reservation_date, reservation_expiry)
VALUES (1, 1, 1, '예약완료', NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE)),
       (2, 1, 2, '예약대기', NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE)),
       (3, 2, 1, '예약완료', NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE));

INSERT INTO hhplus.PAYMENT (user_id, concert_reservation_id, payment_amount, payment_status, payment_date)
VALUES (1, 1, 50000, 'SUCCESS', NOW()),
       (2, 2, 50000, 'PENDING', NOW()),
       (3, 3, 70000, 'SUCCESS', NOW());

INSERT INTO hhplus.BALANCE (user_id, balance_amount, balance_updated)
VALUES (1, 300000, NOW()),
       (2, 150000, NOW()),
       (3, 50000, NOW());
