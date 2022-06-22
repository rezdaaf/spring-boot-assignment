package id.raf.springbootassignment.transactionengine.service.RestModel;
import java.io.Serializable;

public class Seat implements Serializable {
    private String id;
    private int row;
    private int number;
    private String hallId;
    private boolean active;

    public String getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public String getHallId() {
        return hallId;
    }

    public boolean isActive() {
        return active;
    }
}
