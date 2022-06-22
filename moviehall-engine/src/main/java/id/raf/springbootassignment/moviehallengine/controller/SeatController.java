package id.raf.springbootassignment.moviehallengine.controller;

import id.raf.springbootassignment.moviehallengine.model.Seat;
import id.raf.springbootassignment.moviehallengine.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SeatController {
    @Autowired
    SeatRepository seatRepository;

    @GetMapping("/seat")
    public ResponseEntity<List<Seat>> getAllSeat(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer number) {
        try {
            List<Seat> seats = new ArrayList<Seat>();
            if (row == null) {
                seats.addAll(seatRepository.findAll());
            } else {
                if (number == null) {
                    seats.addAll(seatRepository.findByRow(row));
                } else {
                    seats.addAll(seatRepository.findByRowAndNumber(row, number));
                }
            }
            return new ResponseEntity<>(seats, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seat/active")
    public ResponseEntity<List<Seat>> findActiveSeat() {
        try {
            List<Seat> seats = new ArrayList<Seat>(seatRepository.findByActive(true));
            return new ResponseEntity<>(seats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seat/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable("id") String id) {
        Optional<Seat> seat = seatRepository.findById(id);
        if (seat.isPresent()) {
            return new ResponseEntity<>(seat.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/seat")
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat) {
        try {
            Seat _new = seatRepository.save(new Seat(seat.getRow(), seat.getNumber(), seat.getHallId(), seat.isActive()));
            return new ResponseEntity<>(_new, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/seat/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable("id") String id, @RequestBody Seat seat) {
        Optional<Seat> old = seatRepository.findById(id);
        if (old.isPresent()) {
            Seat _seat = old.get();
            _seat.setRow(seat.getRow());
            _seat.setNumber(seat.getNumber());
            _seat.setHallId(seat.getHallId());
            _seat.setActive(seat.isActive());
            return new ResponseEntity<>(seatRepository.save(_seat), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/seat/{id}")
    public ResponseEntity<HttpStatus> deleteSeat(@PathVariable("id") String id) {
        try {
            seatRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seat/detail")
    public ResponseEntity<Seat> getSeatByRowNumberHallId(@RequestParam(required = true) Integer row, @RequestParam(required = true) Integer number,  @RequestParam(required = true) String hallId) {
        List<Seat> seat = seatRepository.findByRowAndNumberAndHallId(row, number, hallId);
        if (!seat.isEmpty()) {
            return new ResponseEntity<>(seat.get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
