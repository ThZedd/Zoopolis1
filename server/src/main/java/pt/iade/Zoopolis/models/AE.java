package pt.iade.Zoopolis.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ae")
public class AE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ae_id")
    private int id;

    @Column(name = "ae_dt_in")
    private LocalDateTime dateIn;

    @Column(name = "ae_dt_out")
    private LocalDateTime dateOut;

    @Column(name = "ae_code")
    private String code;

    @ManyToOne
    @JoinColumn(
            name = "ae_ani_id",
            referencedColumnName = "ani_id",
            nullable = true
    )
    private Animal animal;

    @ManyToOne
    @JoinColumn(
            name = "ae_enc_id",
            referencedColumnName = "enc_id",
            nullable = true
    )
    private Enclosure enclosure;

    // Default constructor
    public AE() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDateTime dateIn) {
        this.dateIn = dateIn;
    }

    public LocalDateTime getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDateTime dateOut) {
        this.dateOut = dateOut;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }
}
