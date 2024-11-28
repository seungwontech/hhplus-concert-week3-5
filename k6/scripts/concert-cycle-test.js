import http from 'k6/http';
import { check, sleep } from 'k6';
import { SharedArray } from 'k6/data';

export const options = {
    scenarios: {
        load_test: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '1s', target: 100 },
                { duration: '3s', target: 100 },
                { duration: '1s', target: 0 },
            ],
        },
    },
};

const BASE_URL = 'http://localhost:8080/api/concerts';
const QUEUE_URL = 'http://localhost:8080/api/waiting-queue';

const users = new SharedArray('users', function () {
    return Array.from({ length: 1000 }, (_, i) => i + 1);
});

function fetchToken(userId) {
    const queueRes = http.get(`${QUEUE_URL}/position`, {
        headers: {
            'user-id': userId,
        },
    });

    check(queueRes, {
        'Token fetched': (r) => r.status === 200
    });

    const token = queueRes.json().token;
    if (!token) {
        console.error(`Failed to fetch token for user ${userId}`);
    }
    return token;
}

function fetchSchedule(concertId, token) {
    const scheduleRes = http.get(`${BASE_URL}/${concertId}/schedules`, {
        headers: { Token: token },
    });

    check(scheduleRes, {
        'Concert schedules checked': (r) => r.status === 200,
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
        headers: { Token: token },
    });

    check(seatsRes, {'Seats availability checked': (r) => r.status === 200});

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

    check(reservationRes, {'Reservation made': (r) => r.status === 200});

    return reservationRes.json().concertReservationId;
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

    check(paymentRes, {'Payment completed': (r) => r.status === 200});

}

export default function () {
    const userId = users[Math.floor(Math.random() * users.length)];

    // Step 1: Fetch token
    const token = fetchToken(userId);

    if (!token) {
        console.error('Token fetching failed for user ' + userId);
        return;
    }

    const concertId = 1;

    // Step 2: Fetch schedule
    const scheduleId = fetchSchedule(concertId, token);
    if (!scheduleId) {
        console.error('No schedule available for concert ' + concertId);
        return;
    }
    sleep(1);

    // Step 3: Fetch seats
    const seatIds = fetchSeats(concertId, scheduleId, token);
    if (!seatIds || seatIds.length === 0) {
        console.error('No seats available for concert ' + concertId + ' and schedule ' + scheduleId);
        return;
    }

    const seatIndex = Math.floor(Math.random() * seatIds.length);
    const seatId = seatIds[seatIndex];
    sleep(1);

    // Step 4: Make reservation
    const reservationId = makeReservation(concertId, scheduleId, seatId, userId, token);
    if (!reservationId) {
        console.error('Reservation failed for user ' + userId);
        return;
    }
    sleep(2);

    // Step 5: Make payment
    makePayment(concertId, scheduleId, reservationId, seatId, userId, token);
    sleep(1);
}
