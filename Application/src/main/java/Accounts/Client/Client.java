package Accounts.Client;

import lombok.Getter;
import lombok.Setter;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;


@Getter
public class Client {
    @Setter private String name;
    @Setter private String surname;
    @Setter private String email;
    @Setter private String passportNumber;
    private int phoneNumberHash;

    public Client(String name, String surname, String email, String passportNumber, PhoneNumberModel phoneNumberModel) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passportNumber = passportNumber;
        this.phoneNumberHash = phoneNumberModel.hashCode();
    }

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
}
