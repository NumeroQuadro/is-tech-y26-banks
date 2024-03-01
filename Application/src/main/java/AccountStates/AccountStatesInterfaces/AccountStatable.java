package AccountStates.AccountStatesInterfaces;

import AccountStates.AccountState;

/**
 * Interface for managing account states
 */
public interface AccountStatable {
    void moveToApprovedState();
    void moveToSuspiciousState();
    AccountState getCurrentAccountState();
}
