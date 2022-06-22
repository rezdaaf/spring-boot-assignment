package id.raf.springbootassignment.moviehallengine.repository;

import java.util.List;
import id.raf.springbootassignment.moviehallengine.model.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeatRepository extends MongoRepository<Seat, String> {
    List<Seat> findByRow(int row);
    List<Seat> findByRowAndNumber(int row, int number);
    List<Seat> findByRowAndNumberAndHallId(int row, int number, String hallId);
    List<Seat> findByHallId(String hallId);
    List<Seat> findByActive(boolean active);
    List<Seat> findByHallIdAndActive(String hallId, boolean active);
}
