package pt.iade.Zoopolis.models;

import jakarta.persistence.*;

@Entity
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ani_id", nullable = false, unique = true)
    private int id;

    @Column(name = "ani_name", nullable = false, unique = true)
    private String name;

    @Column(name = "ani_ci_name")
    private String ciName;

    @Column(name = "ani_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "ani_weight")
    private float weight;

    @Column(name = "ani_height")
    private float height;

    @Column(name = "ani_length")
    private float length;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ani_cla_id", referencedColumnName = "cla_id")
    private Class classe;

    @Column(name = "imageurl")
    private String imageUrl;

    public Animal() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCiName() { return ciName; }
    public void setCiName(String ciName) { this.ciName = ciName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }

    public float getLength() { return length; }
    public void setLength(float length) { this.length = length; }

    public Class getClasse() { return classe; }
    public void setClasse(Class classe) { this.classe = classe; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
