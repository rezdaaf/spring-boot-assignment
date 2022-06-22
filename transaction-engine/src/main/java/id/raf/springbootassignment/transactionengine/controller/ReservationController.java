package id.raf.springbootassignment.transactionengine.controller;

import id.raf.springbootassignment.transactionengine.model.Reservation;
import id.raf.springbootassignment.transactionengine.model.User;
import id.raf.springbootassignment.transactionengine.repository.ReservationRepository;
import id.raf.springbootassignment.transactionengine.repository.UserRepository;
import id.raf.springbootassignment.transactionengine.service.RestModel.ReservationRequest;
import id.raf.springbootassignment.transactionengine.service.RestModel.Seat;
import id.raf.springbootassignment.transactionengine.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ReservationController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("/reservation")
    public ResponseEntity<List<Reservation>> getAllReservations(@RequestParam(required = false) Long userId, @RequestParam(required = false) String seatId) {
        try {
            List<Reservation> reservations = new ArrayList<>();
            if (userId == null) {
                reservations.addAll(reservationRepository.findAll());
            } else {
                if (seatId == null) {
                    reservations.addAll(reservationRepository.findByUserId(userId));
                } else {
                    reservations.addAll(reservationRepository.findByUserIdAndSeatId(userId, seatId));
                }
            }
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reservation/active")
    public ResponseEntity<List<Reservation>> getAllActiveReservations(@RequestParam(required = false) Long userId, @RequestParam(required = false) String seatId) {
        try {
            List<Reservation> reservations = new ArrayList<>();
            if (userId == null) {
                reservations.addAll(reservationRepository.findByActive(true));
            } else {
                if (seatId == null) {
                    reservations.addAll(reservationRepository.findByUserIdAndActive(userId, true));
                } else {
                    reservations.addAll(reservationRepository.findByUserIdAndSeatIdAndActive(userId, seatId, true));
                }
            }
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<Reservation> getMovieHallById(@PathVariable("id") Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reservation")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        try {
            Optional<User> user = userRepository.findById(request.getUserId());
            if (user.isEmpty()) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            Seat seat = new RestService().getSeat(request.getRow(), request.getNumber(), request.getHallId());
            if (seat == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            if (!seat.isActive()) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            boolean isReserved = reservationRepository.findBySeatIdAndActive(seat.getId(), true).size() > 0;
            if (isReserved) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            boolean isThisUserReserved = reservationRepository.findByUserIdAndSeatIdAndActive(request.getUserId(), seat.getId(), true).size() > 0;
            if (isThisUserReserved) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            Reservation _new = reservationRepository.save(new Reservation(request.getUserId(), seat.getId(), seat.getHallId(), true));
            return new ResponseEntity<>(_new, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
