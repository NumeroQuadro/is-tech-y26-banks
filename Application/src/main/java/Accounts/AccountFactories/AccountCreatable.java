package Accounts.AccountFactories;

import Accounts.Client.Client;
import Accounts.CreditAccount;
import Accounts.DebitAccount;
import Accounts.DepositAccount;
import interfaces.InterestCalculable;

/**
 * Interface for creating accounts
 */
public interface AccountCreatable {
    CreditAccount createCreditAccount(String accountNumber, Client client, double initialBalance);
    DebitAccount createDebitAccount(String accountNumber, Client client);
    DepositAccount createDepositAccount(String accountNumber, Client client, InterestCalculable interestCalculator);
}
