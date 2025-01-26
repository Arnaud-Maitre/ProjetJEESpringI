package ch.hearc.jee2024.projetjeespring.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "review")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Review {

    public Review() {}

    @Override
    public String toString() {
        return this.id + " " + this.rating + " " + this.comment;
    }

    public Review(Long id, int rating, String comment, Location location) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.location = location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name="location_id", nullable=false)
    @JsonIdentityReference()
    private Location location;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return true;
        if (!(obj instanceof Review other))
            return false;
        return (Objects.equals(this.id, other.id) && Objects.equals(this.rating, other.rating) && Objects.equals(this.comment, other.comment) && Objects.equals(this.location, other.location));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, comment, location);
    }
}
