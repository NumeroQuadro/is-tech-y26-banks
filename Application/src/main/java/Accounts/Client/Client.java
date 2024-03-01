package Accounts.Client;

import lombok.Getter;
import lombok.Setter;

/**
 * Model class for the client
 */
@Getter
public class Client {
    private final String name;
    private final String surname;
    private final String email;
    private String passportNumber;
    private final int phoneNumberHash;

    public Client(String name, String surname, String email, String passportNumber, int phoneNumberHash) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passportNumber = passportNumber;
        this.phoneNumberHash = phoneNumberHash;
    }
}
