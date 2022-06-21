package id.raf.springbootassignment.moviehallengine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "seat")
public class Seat {
    @Id
    private String id;
    private int row;
    private int number;
    private String hallId;
    private boolean active;

    public Seat() { }

    public Seat(int row, int number, String hallid, boolean active) {
        this.row = row;
        this.number = number;
        this.hallId = hallid;
        this.active = active;
    }

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

    public void setRow(int row) {
        this.row = row;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Seat [id=" + id + ", row=" + row + ", number=" + number + ", hallId=" + hallId + ", active=" + active + "]";
    }
}
