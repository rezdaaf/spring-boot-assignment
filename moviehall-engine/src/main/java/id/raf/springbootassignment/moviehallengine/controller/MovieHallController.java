package id.raf.springbootassignment.moviehallengine.controller;

import id.raf.springbootassignment.moviehallengine.model.MovieHall;
import id.raf.springbootassignment.moviehallengine.model.Seat;
import id.raf.springbootassignment.moviehallengine.repository.MovieHallRepository;
import id.raf.springbootassignment.moviehallengine.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MovieHallController {
    @Autowired
    MovieHallRepository movieHallRepository;
    @Autowired
    SeatRepository seatRepository;

    @GetMapping("/movie_hall")
    public ResponseEntity<List<MovieHall>> getAllMovieHalls(@RequestParam(required = false) String name) {
        try {
            List<MovieHall> movieHalls = new ArrayList<MovieHall>();
            if (name == null) {
                movieHalls.addAll(movieHallRepository.findAll());
            } else {
                movieHalls.addAll(movieHallRepository.findByNameIgnoreCaseContaining(name));
            }
            return new ResponseEntity<>(movieHalls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movie_hall/active")
    public ResponseEntity<List<MovieHall>> findActiveMovieHall() {
        try {
            List<MovieHall> movieHalls = new ArrayList<MovieHall>(movieHallRepository.findByActive(true));
            return new ResponseEntity<>(movieHalls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movie_hall/{id}")
    public ResponseEntity<MovieHall> getMovieHallById(@PathVariable("id") String id) {
        Optional<MovieHall> movieHall = movieHallRepository.findById(id);
        if (movieHall.isPresent()) {
            return new ResponseEntity<>(movieHall.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/movie_hall")
    public ResponseEntity<MovieHall> createMovieHall(@RequestBody MovieHall movieHall) {
        try {
            MovieHall _new = movieHallRepository.save(new MovieHall(movieHall.getName(), movieHall.isActive()));
            return new ResponseEntity<>(_new, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/movie_hall/{id}")
    public ResponseEntity<MovieHall> updateMovieHall(@PathVariable("id") String id, @RequestBody MovieHall movieHall) {
        Optional<MovieHall> old = movieHallRepository.findById(id);
        if (old.isPresent()) {
            MovieHall _movieHall = old.get();
            _movieHall.setName(movieHall.getName());
            _movieHall.setActive(movieHall.isActive());
            return new ResponseEntity<>(movieHallRepository.save(_movieHall), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/movie_hall/{id}")
    public ResponseEntity<HttpStatus> deleteMovieHall(@PathVariable("id") String id) {
        try {
            movieHallRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movie_hall/{id}/seat")
    public ResponseEntity<List<Seat>> getAllSeatByMovieHallId(@PathVariable("id") String id) {
        Optional<MovieHall> movieHall = movieHallRepository.findById(id);
        if (movieHall.isPresent()) {
            return new ResponseEntity<>(seatRepository.findByHallId(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/movie_hall/{id}/active_seat")
    public ResponseEntity<List<Seat>> getAllActiveSeatByMovieHallId(@PathVariable("id") String id) {
        Optional<MovieHall> movieHall = movieHallRepository.findById(id);
        if (movieHall.isPresent()) {
            return new ResponseEntity<>(seatRepository.findByHallIdAndActive(id, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
