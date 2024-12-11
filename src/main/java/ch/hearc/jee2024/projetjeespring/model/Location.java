package ch.hearc.jee2024.projetjeespring.model;

public record Location(int id, String name, String description, double latitude, double longitude) {
    public Location withId(int id) {
        return new Location(id, name, description, latitude, longitude);
    }
}
