package pt.iade.Zoopolis.models;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fav_id", nullable = false, unique = true)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fav_animal", referencedColumnName = "ani_name", nullable = false)
    private Animal name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fav_ani_id", referencedColumnName = "ani_id", nullable = false)
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fav_per_id", referencedColumnName = "per_id", nullable = false)
    private Person person;

    public Favorite() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Animal getAnimalName() { return name; }
    public void setAnimalName(Animal name) { this.name = name; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }
}
