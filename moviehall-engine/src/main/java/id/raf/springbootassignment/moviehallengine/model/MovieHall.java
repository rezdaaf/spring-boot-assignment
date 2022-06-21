package id.raf.springbootassignment.moviehallengine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "moviehall")
public class MovieHall {
    @Id
    private String id;
    private String name;
    private boolean active;

    public MovieHall() { }

    public MovieHall(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "MovieHall [id=" + id + ", name=" + name + ", active=" + active + "]";
    }
}
