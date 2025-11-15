package pt.iade.Zoopolis.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_id")
    private int id;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "per_name", nullable = false)
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    @Column(name = "per_email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must have at least 6 characters")
    @Column(name = "per_password", nullable = false)
    private String password;

    @NotNull(message = "Gender must be specified")
    @Column(name = "per_gender", nullable = false)
    private String gender;

    @Column(name = "per_points", nullable = false)
    private int points = 0; // define valor default

    public Person() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
