package Accounts.Client;

import lombok.Getter;
import lombok.NonNull;

public record PhoneNumberModel(@Getter String countryCode, @Getter String areaCode, @Getter String subscriberNumber) {
    // TODO: add checkers to redundant + or - in input from user

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
