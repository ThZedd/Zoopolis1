package pt.iade.Zoopolis.models;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_area")
public class Sub_Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sa_id")
    private int id;

    @Column(name = "sa_name")
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "sa_area_id",
            referencedColumnName = "area_id",
            nullable = true
    )
    private Area area;

    // Default constructor
    public Sub_Area() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
