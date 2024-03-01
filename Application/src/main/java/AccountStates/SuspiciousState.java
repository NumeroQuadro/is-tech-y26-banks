package AccountStates;

import AccountStates.AccountStatesInterfaces.AccountStatable;

public class SuspiciousState implements AccountStatable {
    private AccountState accountState;

    public SuspiciousState() {
        accountState = AccountState.SUSPICIOUS;
    }

    @Override
    public void moveToApprovedState() {
        this.accountState = AccountState.APPROVED;
    }

    @Override
    public void moveToSuspiciousState() { }

    @Override
    public AccountState getCurrentAccountState() {
        return this.accountState;
    }
}
