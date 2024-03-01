package AccountStates;

import AccountStates.AccountStatesInterfaces.AccountStatable;

/**
 * Class for approved account state
 */
public class ApprovedAccount implements AccountStatable {
    private AccountState accountState;

    public ApprovedAccount() {
        this.accountState = AccountState.APPROVED;
    }


    @Override
    public void moveToApprovedState() { }

    @Override
    public void moveToSuspiciousState() {
        this.accountState = AccountState.SUSPICIOUS;
    }

    @Override
    public AccountState getCurrentAccountState() {
        return this.accountState;
    }
}
