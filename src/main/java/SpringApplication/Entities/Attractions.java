package SpringApplication.Entities;

import javax.validation.constraints.*;

public class Attractions {

    private int id_attraction;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 300, message = "Duration cannot exceed 300 minutes")
    private int duration;

    @NotBlank(message = "Minimum age is required")
    @Min(value = 0, message = "Minimum age cannot be negative")
    @Max(value = 120, message = "Minimum age cannot be greater than 120")
    private int min_age;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @NotBlank(message = "Route is required")
    @Pattern(regexp = "Dragons Route|Smurfs Route", message = "Route must be 'Dragons Route' or 'Smurfs Route'")
    private String route;

    public Attractions() {
    }

    public Attractions(int id, String name, int duration, int min_age, String description, String route) {
        this.id_attraction = id;
        this.name = name;
        this.duration = duration;
        this.min_age = min_age;
        this.description = description;
        this.route = route;
    }

    public int getId_attraction() {
        return id_attraction;
    }

    public void setId_attraction(int id_attraction) {
        this.id_attraction = id_attraction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Attractions{" +
                "id_attraction=" + id_attraction +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", min_age=" + min_age +
                ", description='" + description + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
