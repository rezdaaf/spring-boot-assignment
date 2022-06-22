package id.raf.springbootassignment.transactionengine.repository;

import id.raf.springbootassignment.transactionengine.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findBySeatIdAndActive(String seatId, Boolean active);
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByUserIdAndActive(Long userId, Boolean active);
    List<Reservation> findByUserIdAndSeatId(Long userId, String seatId);
    List<Reservation> findByUserIdAndSeatIdAndActive(Long userId, String seatId, Boolean active);
    List<Reservation> findByActive(Boolean active);
}
