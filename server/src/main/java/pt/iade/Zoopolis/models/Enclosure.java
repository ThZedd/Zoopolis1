package pt.iade.Zoopolis.models;

import jakarta.persistence.*;

@Entity
@Table(name = "enclosure")
public class Enclosure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enc_id")
    private int id;

    @Column(name = "enc_name")
    private String name;

    @Column(name = "enc_aniclass")
    private String animalClass;

    @Column(name = "enc_sup_amount")
    private int supportedAmount;

    @Column(name = "enc_lat")
    private double latitude;

    @Column(name = "enc_long")
    private double longitude;

    @ManyToOne
    @JoinColumn(
            name = "enc_sa_id",
            referencedColumnName = "sa_id",
            nullable = true
    )
    private Sub_Area subArea;

    // Default constructor
    public Enclosure() {}

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

    public String getAnimalClass() {
        return animalClass;
    }

    public void setAnimalClass(String animalClass) {
        this.animalClass = animalClass;
    }

    public int getSupportedAmount() {
        return supportedAmount;
    }

    public void setSupportedAmount(int supportedAmount) {
        this.supportedAmount = supportedAmount;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Sub_Area getSubArea() {
        return subArea;
    }

    public void setSubArea(Sub_Area subArea) {
        this.subArea = subArea;
    }
}
