package Accounts.Client;

import lombok.Getter;
import lombok.NonNull;

/**
 * Model class for the phone number
 * @param countryCode
 * @param areaCode
 * @param subscriberNumber
 */
public record PhoneNumberModel(@Getter String countryCode, @Getter String areaCode, @Getter String subscriberNumber) {
    @Override
    public String toString() {
        return countryCode + areaCode + "-" + subscriberNumber;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public int compareTo(@NonNull PhoneNumberModel otherPhoneNumberModel) {
        if (this.countryCode.compareTo(otherPhoneNumberModel.countryCode) == 0) {
            if (this.areaCode.compareTo(otherPhoneNumberModel.areaCode) == 0) {
                return this.subscriberNumber.compareTo(otherPhoneNumberModel.subscriberNumber);
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}
