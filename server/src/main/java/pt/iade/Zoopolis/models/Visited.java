package pt.iade.Zoopolis.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visited")
public class Visited {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vi_id")
    private int id;

    @Column(name = "vi_dtime")
    private LocalDateTime dtime;

    @ManyToOne
    @JoinColumn(
            name = "vi_per_id",
            referencedColumnName = "per_id",
            nullable = true
    )
    private Person person;

    @ManyToOne
    @JoinColumn(
            name = "vi_sa_id",
            referencedColumnName = "sa_id",
            nullable = true
    )
    private Sub_Area subArea;

    // Default constructor
    public Visited() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDtime() {
        return dtime;
    }

    public void setDtime(LocalDateTime dtime) {
        this.dtime = dtime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Sub_Area getSubArea() {
        return subArea;
    }

    public void setSubArea(Sub_Area subArea) {
        this.subArea = subArea;
    }
}
