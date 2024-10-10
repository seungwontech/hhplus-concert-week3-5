## 1. 유저 토큰 발급 API  
 >  설명: 특정 유저에게 새로운 대기열 토큰을 발급  
 >  HTTP Method: POST  
 >  URL: /api/concert/token/{userId}  

### Request:  
 >  Path Parameter:  
 >  userId (Long): 발급할 유저의 ID

### Response:  
   ```
   {
   "userId": 1,
   "token": "a1b2c3",
   "expiryTime": "2024-10-11T10:05:00",
   "createdAt": "2024-10-11T10:00:00"
   }
   ```
### Error Response:
>   404 Not Found: 유저 ID를 찾을 수 없음
---

## 2. 토큰 조회 API
 >  설명: 유저의 대기열 토큰을 조회  
 >  HTTP Method: GET  
 >  URL: /api/concert/queue/token  
 
### Request:
 >  Query Parameter:
 >  userId (Long): 조회할 유저의 ID
### Response:
   ```
   {
   "userId": 1,
   "token": "a1b2c3",
   "expiryTime": "2024-10-11T10:05:00",
   "createdAt": "2024-10-11T10:00:00"
   }
   ```
   ### Error Response:
> 404 Not Found: 유저 ID 또는 토큰을 찾을 수 없음
---

### 3. 대기열 순번 조회 API
 >  설명: 유저가 대기열에서 배정받은 순번을 조회  
 >  HTTP Method: GET  
 >  URL: /api/concert/queue/position
 
 ###  Request:
>  Query Parameter:  
>   queueTokenId (Long): 대기열 토큰의 ID

###   Response:
 ```
   {
     "queueToken": 1,
     "position": 40
   }
```
  ###  Error Response:
   > 404 Not Found: 대기열 토큰을 찾을 수 없음
----


## 4. 예약 가능 날짜 조회 API
  > 설명: 특정 콘서트의 예약 가능 날짜를 조회  
  >  HTTP Method: GET  
  > URL: /api/concert/{concertId}/schedule/
  ### Request:
> Path Parameter:  
>   concertId (Long): 콘서트 ID

  ###  Header:
   >TOKEN (String): 인증 토큰
  ### Response:
 ```
   {
       "concertId": 1,
       "concertSchedules": [
           {
           "concertDateId": 1,
           "scheduleAt": "2024-10-10T10:00:00"
           },
           {
           "concertDateId": 2,
           "scheduleAt": "2024-10-10T12:00:00"
           }
       ]
   }
   ```
   ### Error Response:
   > 404 Not Found: 콘서트 ID를 찾을 수 없음
---
## 5. 예약 가능 좌석 조회 API
   >설명: 특정 콘서트의 예약 가능 좌석을 조회  
   >HTTP Method: GET  
   >URL: /api/concert/{concertId}/schedule/{concertDateId}/seat
   ### Request:
   >Path Parameter:  
   >concertId (Long): 콘서트 ID  
   >concertDateId (Long): 콘서트 날짜 ID  
   ### Header:
   >TOKEN (String): 인증 토큰
   ### Response:
   ```
   {
       "concertId": 1,
       "concertDateId": 1,
       "concertSeats": [
           {
           "seatId": 1,
           "seatNumber": 1,
           "reserveYn": "N"
           },
           {
           "seatId": 2,
           "seatNumber": 2,
           "reserveYn": "N"
           }
       ]
   }
   ```
   #### Error Response:
  > 404 Not Found: 콘서트 또는 날짜 ID를 찾을 수 없음
--- 
## 6. 좌석 예약 API
   >설명: 유저가 특정 콘서트의 좌석을 예약  
   >HTTP Method: POST  
   >URL: /api/concert/reservations
   ### Request:
   >### Header:  
   >TOKEN (String): 인증 토큰  
   >Query Parameter:  
   >userId (Long): 유저 ID  
   >concertDateId (Long): 콘서트 날짜 ID  
   >seatIds (Long[]): 예약할 좌석 ID 배열
   
   ### Response:
   ```
   {
       "userId": 1,
       "concertName": "임영웅가을콘서트",
       "totalPrice": 4000,
       "seats": [
           {
               "seatNumber": 1,
               "status": "예약완료",
               "seatPrice": 2000
           },
           {
               "seatNumber": 2,
               "status": "예약완료",
               "seatPrice": 2000
           }
       ]
   }
   ```
   ### Error Response:
   > 404 Not Found: 유저, 날짜, 또는 좌석 ID를 찾을 수 없음
    
--- 
## 7. 결제 API
   > 설명: 좌석 예약 후 결제 처리  
   > HTTP Method: POST  
   > URL: /api/concert/payment  
  ###  Request:
   >Header:
   >TOKEN (String): 인증 토큰
   >Query Parameter:
   >userId (Long): 유저 ID
   >reservationId (Long): 예약 ID
   ### Response:
   ```
   {
    "paidAt": "2024-10-11T10:05:00",
    "amount": 3000,
    "status": "결제 완료"
   }
   ```
   ### Error Response:
   > 404 Not Found: 유저 또는 예약 ID를 찾을 수 없음
---
## 8. 잔액 조회 API
  > 설명: 유저의 잔액을 조회  
  > HTTP Method: GET  
  > URL: /api/concert/user/balance/
   ### Request:
   >Query Parameter:
   >userId (Long): 유저 ID
   ### Response:
```
   {
     "userId": 1,
     "amount": 3000
   }
``` 
  ### Error Response:
   > 404 Not Found: 유저 ID를 찾을 수 없음
---
## 9. 잔액 충전 API
  > 설명: 유저의 잔액을 충전  
  > HTTP Method: PATCH  
  > URL: /api/concert/user/balance/charge/{userId}
  ###  Request:
   >Path Parameter:
  > userId (Long): 잔액을 충전할 유저 ID
  ###  Response:
``` 
   {
     "userId": 1,
     "amount": 3000
   }
```    
   ### Error Response:
   > 404 Not Found: 유저 ID를 찾을 수 없음
   