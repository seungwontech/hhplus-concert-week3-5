import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    vus: 50,
    duration: '10s'
};

const BASE_URL = 'http://localhost:8080/api/concerts';
const tk_BASE_URL = 'http://localhost:8080/api/waiting-queue';

export function setup() {
    const userIds = Array.from({length: 50}, (_, index) => index + 1);
    const tokens = userIds.map((userId) => {
        const res = http.post(`${tk_BASE_URL}`, null, {
            headers: {'user-id': userId},
        });

        const token = res.json().token;
        if (!token) {
            console.log('Token generation failed for user userId ');
        }

        return token;
    });

    return {tokens};
}

function fetchSchedule(concertId, token) {
    const scheduleRes = http.get(`${BASE_URL}/${concertId}/schedules`, {
        headers: {Token: token},
    });
    check(scheduleRes, {
        'Schedule fetched': (r) => r.status === 200,
    });

    const schedules = scheduleRes.json().schedules;

    if (schedules.length === 0) {
        console.log('No available schedules for concert ' + concertId);
        return;
    }

    return schedules[0].concertScheduleId; // 첫 번째 날짜 ID 반환
}

function fetchSeats(concertId, scheduleId, token) {
    const seatsRes = http.get(`${BASE_URL}/${concertId}/schedules/${scheduleId}/seats`, {
        headers: {Token: token},
    });

    check(seatsRes, {
        'Seats fetched': (r) => r.status === 200,
    });

    const seats = seatsRes.json().seats.map((seat) => seat.concertSeatId);
    if (seats.length === 0) {
        console.log('No available seats for concert ' + concertId + ' and schedule ' + scheduleId);
        return;
    }

    return seats;
}

function makeReservation(concertId, scheduleId, concertSeatId, userId, token) {
    const reservationPayload = JSON.stringify({
        concertSeatIds: [concertSeatId],
        userId: userId,
    });

    const reservationRes = http.post(
        `${BASE_URL}/${concertId}/schedules/${scheduleId}/seats/reservation`,
        reservationPayload,
        {
            headers: {
                'Content-Type': 'application/json',
                Token: token,
            },
        }
    );
    check(reservationRes, {
        'Reservation successful': (r) => r.status === 200,
    });

    return reservationRes.json().concertReservationId; // 예약 ID 반환
}

function makePayment(concertId, scheduleId, reservationId, seatId, userId, token) {
    const paymentPayload = JSON.stringify({
        userId: userId,
        concertSeatIds: [seatId],
        concertReservationId: [reservationId],
    });

    const paymentRes = http.post(
        `${BASE_URL}/${concertId}/schedules/${scheduleId}/seats/reservation/payment`,
        paymentPayload,
        {
            headers: {
                'Content-Type': 'application/json',
                Token: token,
            },
        }
    );
    check(paymentRes, {
        'Payment successful': (r) => r.status === 200,
    });
}

export default function ({tokens}) {

    const token = tokens[__VU - 1];
    const userId = __VU;
    const concertId = 1;

    const scheduleId = fetchSchedule(concertId, token);
    sleep(1);

    const seatIds = fetchSeats(concertId, scheduleId, token);

    const seatIndex = Math.floor(Math.random() * seatIds.length);

    const seatId = seatIds[seatIndex]

    sleep(1);

    const reservationId = makeReservation(concertId, scheduleId, seatId, userId, token);

    sleep(2);

    makePayment(concertId, scheduleId, reservationId, seatId, userId, token);

    sleep(1);
}