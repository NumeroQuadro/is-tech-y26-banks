package AccountStates.AccountStatesInterfaces;

import AccountStates.AccountState;

public interface AccountStatable {
    void moveToApprovedState();
    void moveToSuspiciousState();
    AccountState getCurrentAccountState();
}
