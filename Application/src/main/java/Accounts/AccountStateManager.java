package Accounts;

import AccountStates.AccountStatesInterfaces.AccountStatable;
import Accounts.Client.Client;

/**
 * Class for managing account states
 */
public class AccountStateManager {
    public void checkAndMoveAccountState(AccountStatable accountState, Client client) {
        if (client.getPassportNumber() == null || client.getEmail() == null) {
            accountState.moveToSuspiciousState();
        }
        else if (client.getPassportNumber().isEmpty() || client.getEmail().isEmpty()) {
            accountState.moveToSuspiciousState();
        }
        else {
            accountState.moveToApprovedState();
        }
    }
}
