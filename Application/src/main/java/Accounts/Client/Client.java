package Accounts.Client;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Client {
    private String name;
    private String surname;
    private String email;
    private String passportNumber;
    private int phoneNumberHash;

    public Client(String name, String surname, String email, String passportNumber, int phoneNumberHash) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passportNumber = passportNumber;
        this.phoneNumberHash = phoneNumberHash;
    }
}
