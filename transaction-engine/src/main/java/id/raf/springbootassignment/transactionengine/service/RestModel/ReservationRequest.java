package id.raf.springbootassignment.transactionengine.service.RestModel;

public class ReservationRequest {
    private Long userId;
    private int row;
    private int number;
    private String hallId;

    public String getHallId() {
        return hallId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }
}
