package pt.iade.Zoopolis.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity")
public class Activity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_id")
    private int id;

    @Column(name = "ac_schedule")
    private LocalDateTime schedule;

    @Column(name = "ac_cap")
    private int capacity;

    @ManyToOne
    @JoinColumn(
            name = "ac_area_id",
            referencedColumnName = "area_id",
            nullable = true
    )
    private Area area;


}
