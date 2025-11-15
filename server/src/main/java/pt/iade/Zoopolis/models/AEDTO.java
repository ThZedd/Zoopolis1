package pt.iade.Zoopolis.models;

import java.time.LocalDateTime;

public class AEDTO {
    private int id;
    private LocalDateTime dateIn;
    private LocalDateTime dateOut;
    private String code;
    private AnimalDTO animal;
    private EnclosureDTO enclosure;

    // Constructor
    public AEDTO(int id, LocalDateTime dateIn, LocalDateTime dateOut, String code, AnimalDTO animal, EnclosureDTO enclosure) {
        this.id = id;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.code = code;
        this.animal = animal;
        this.enclosure = enclosure;
    }

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

    public AnimalDTO getAnimal() {
        return animal;
    }

    public void setAnimal(AnimalDTO animal) {
        this.animal = animal;
    }

    public EnclosureDTO getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(EnclosureDTO enclosure) {
        this.enclosure = enclosure;
    }
}
