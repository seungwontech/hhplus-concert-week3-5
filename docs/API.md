## 1. 유저 토큰 발급 API
### 요청
- URL: /api/waiting-queue/
- Method: POST
- Headers:
- user-id: 사용자 ID (필수)
- Request Body: 없음
### 응답
- Status: 200 OK
- Response Body:
```
{
  "userId": 1,
  "token": "a1s2d3",
  "tokenCreated": "2024-10-10T10:00:00",
  "tokenExpiry": "2024-10-10T10:05:00",
  "tokenStatus": "대기"
}
```


## 2. 대기열 순번 조회 API
### 요청
- URL: /api/waiting-queue/position
- Method: GET
- Headers:
- user-id: 사용자 ID (필수)
- Request Body: 없음
### 응답
- Status: 200 OK
- Response Body:
```
{
  "userId": 1,
  "position": 40,
  "token": "a1s2d3",
  "tokenCreated": "2024-10-10T10:00:00",
  "tokenExpiry": "2024-10-10T10:05:00",
  "tokenStatus": "대기"
}
```


## 3. 예약 가능 날짜 조회 API
### 요청
- URL: /api/concerts/{concertId}/schedules/
- Method: GET
- Path Variables:
- concertId: 콘서트 ID (필수)
- Request Body: 없음
### 응답
- Status: 200 OK
- Response Body:
```
{
  "concertId": 1,
  "schedules": [
    {
      "concertDateId": 1,
      "scheduleAt": "2024-10-10T10:00:00"
    },
    {
      "concertDateId": 2,
      "scheduleAt": "2024-10-10T10:10:10"
    }
  ]
}
```


## 4. 예약 가능 좌석 조회 API
### 요청
- URL: /api/concerts/{concertId}/schedules/{concertScheduleId}/seats
- Method: GET
- Path Variables:
- concertId: 콘서트 ID (필수)
- concertScheduleId: 콘서트 스케줄 ID (필수)
- Request Body: 없음
### 응답
- Status: 200 OK
- Response Body:
```
{
  "concertId": 1,
  "scheduleId": 1,
  "seats": [
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


## 5. 좌석 예약 API
### 요청
- URL: /api/concerts/{concertId}/schedules/{concertScheduleId}/seats/reservation
  Method: POST
- Headers:
- Token: 인증 토큰 (필수)
- Path Variables:
- concertId: 콘서트 ID (필수)
- concertScheduleId: 콘서트 스케줄 ID (필수)
- Request Body:
```
 {
   "userId": 1,
   "seats": [1, 2]
 }
```
### 응답
- Status: 200 OK
- Response Body:
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


## 6. 콘서트 결제 API
### 요청
- URL: /api/concerts/{concertId}/schedules/{concertScheduleId}/seats/reservation/payment
- Method: POST
- Headers:
- Token: 인증 토큰 (필수)
- Path Variables:
- concertId: 콘서트 ID (필수)
- concertScheduleId: 콘서트 스케줄 ID (필수)
- Request Body:
```
 {
   "userId": 1,
   "concertScheduleId":1
 }
```
### 응답
- Status: 200 OK
- Response Body:
```
 {
   "paymentDate": "2024-10-10T10:10:10",
   "paymentAmount": 3000,
   "paymentStatus": "결제 완료"
 }
```


## 7. 잔액 충전 API
### 요청
- URL: /api/balances/balance/charge
- Method: PATCH
- Headers:
- user-id: 사용자 ID (필수)
- Path Variables: 없음
- Query Parameters: 없음
- Request Body: 
```
1000
```
- 충전할 금액 (예: 1000)

### 응답
- Status: 200 OK
- Respoonse Body:
```
 {  
   "userId": 1,  
   "amount": 4000
 }
```


## 8. 잔액 조회 API
### 요청
- URL: /api/balances/balance/
- Method: GET
- Headers:
- user-id: 사용자 ID (필수)
- Path Variables: 없음
- Query Parameters: 없음
- Request Body: 없음
### 응답
- Status: 200 OK
- Respoonse Body:
```
 {  
   "userId": 1,  
   "amount": 3000
 }
```