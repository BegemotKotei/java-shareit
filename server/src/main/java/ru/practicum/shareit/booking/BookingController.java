package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.AnswerBookingDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<AnswerBookingDto> createBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody BookingDto bookingDto) {
        log.info("POST /bookings : create booking from DTO - {}, user ID {}", bookingDto, userId);
        return ResponseEntity.ok().body(bookingService.createBooking(userId, bookingDto));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<AnswerBookingDto> confirmationBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long bookingId,
            @RequestParam boolean approved) {
        log.info("PATCH /bookings/{} : update booking's status by ID - approved {}, user ID {}",
                bookingId, approved, userId);
        return ResponseEntity.ok().body(bookingService.confirmationBooking(userId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<AnswerBookingDto> getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                       @PathVariable Long bookingId) {
        log.info("GET /bookings/{} : get booking by ID by user ID {}", bookingId, userId);
        return ResponseEntity.ok().body(bookingService.getBooking(userId, bookingId));
    }

    @GetMapping
    public ResponseEntity<List<AnswerBookingDto>> getAllBookingByUser(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(value = "state", defaultValue = "ALL", required = false) String state,
            @RequestParam(value = "from", defaultValue = "0", required = false) int from,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        log.info("GET /bookings?state={}&from={}&size={} : get list of bookings by user ID {} with state",
                state, from, size, userId);
        return ResponseEntity.ok().body(bookingService.getAllBookingByUser(userId, state, PageRequest.of(from / size, size)));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<AnswerBookingDto>> getAllBookingByOwner(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(value = "state", defaultValue = "ALL", required = false) String state,
            @RequestParam(value = "from", defaultValue = "0", required = false) int from,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        log.info("GET /bookings/owner?state={}&from={}&size={} : get list of bookings by owner ID {} with state",
                state, from, size, userId);
        return ResponseEntity.ok().body(bookingService.getAllBookingByOwner(userId, state, PageRequest.of(from / size, size)));
    }

}