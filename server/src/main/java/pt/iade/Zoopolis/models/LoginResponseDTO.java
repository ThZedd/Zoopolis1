package pt.iade.Zoopolis.models;

public class LoginResponseDTO {
    private int personId;
    private String token;

    public LoginResponseDTO(int personId, String token) {
        this.personId = personId;
        this.token = token;
    }

    // Getters e Setters
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
