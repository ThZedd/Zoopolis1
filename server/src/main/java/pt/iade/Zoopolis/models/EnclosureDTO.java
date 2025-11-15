package pt.iade.Zoopolis.models;

public class EnclosureDTO {
    private int id;
    private String name;
    private String animalClass;
    private int supportedAmount;
    private double latitude;
    private double longitude;

    // Constructor
    public EnclosureDTO(int id, String name, String animalClass, int supportedAmount, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.animalClass = animalClass;
        this.supportedAmount = supportedAmount;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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
}
