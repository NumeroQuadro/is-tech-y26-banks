package Accounts.Client;

import Banks.PercentageRateInterests;

public interface ClientNotifable {
    void notifyClient(PercentageRateInterests rateInterests, String accountNumber);
}
