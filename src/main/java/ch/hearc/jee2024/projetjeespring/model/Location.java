package ch.hearc.jee2024.projetjeespring.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Location {

    public Location() {
    }

    public Location(Long id, String name, String description, Double lat, Double lon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = lat;
        this.longitude = lon;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))
            return true;
        if (!(obj instanceof Location other))
            return false;
        return (Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name) && Objects.equals(this.description, other.description) && Objects.equals(this.latitude, other.latitude) && Objects.equals(this.longitude, other.longitude));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, latitude, longitude);
    }
}
