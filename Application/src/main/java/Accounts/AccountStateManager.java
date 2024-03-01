package Accounts;

import AccountStates.AccountState;
import AccountStates.AccountStatesInterfaces.AccountStatable;
import Accounts.Client.Client;

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
