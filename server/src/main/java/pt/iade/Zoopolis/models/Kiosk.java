package pt.iade.Zoopolis.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "kiosk")
public class Kiosk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kio_id")
    private int id;

    @Column(name = "kio_name")
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "kio_area_id",
            referencedColumnName = "area_id",
            nullable = true
    )
    private Area area;
}
