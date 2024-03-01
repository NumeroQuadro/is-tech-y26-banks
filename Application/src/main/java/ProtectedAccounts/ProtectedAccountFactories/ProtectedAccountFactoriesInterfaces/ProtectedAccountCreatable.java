package ProtectedAccounts.ProtectedAccountFactories.ProtectedAccountFactoriesInterfaces;

import Accounts.Client.Client;
import Accounts.Client.ClientBuilder;
import Banks.PercentageRateInterests;
import ProtectedAccounts.ProtectedCreditAccount;
import ProtectedAccounts.ProtectedDebitAccount;
import ProtectedAccounts.ProtectedDepositAccount;

public interface ProtectedAccountCreatable {
    ProtectedDebitAccount createProtectedDebitAccount(double doubtfulLimit, Client client);
    ProtectedDepositAccount createProtectedDepositAccount(PercentageRateInterests rateInterests, Client client, double doubtfulLimit);
    ProtectedCreditAccount createProtectedCreditAccount(double doubtfulLimit, Client client);
}
