package id.raf.springbootassignment.moviehallengine.repository;

import java.util.List;
import id.raf.springbootassignment.moviehallengine.model.MovieHall;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieHallRepository extends MongoRepository<MovieHall, String> {
    List<MovieHall> findByNameIgnoreCaseContaining(String name);
    List<MovieHall> findByActive(boolean active);
}
