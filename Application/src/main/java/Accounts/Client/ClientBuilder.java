package Accounts.Client;

import lombok.Getter;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import lombok.Setter;


/**
 * Client class represents a client of the bank.
 */
@Getter
public class ClientBuilder {
    @Setter private String name;
    @Setter private String surname;
    @Setter private String email;
    @Setter private String passportNumber;
    private int phoneNumberHash;

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            var numberProto = phoneUtil.parse(phoneNumber, "RU");
            String internationalFormat = phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            this.phoneNumberHash = internationalFormat.hashCode();

        }
        catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e);
        }
    }

    public Client buildClient() {
        if (this.name == null || this.surname == null) {
            throw new IllegalArgumentException("Client has no name or surname");
        }

        return new Client(this.name, this.surname, this.email, this.passportNumber, this.phoneNumberHash);
    }
}
