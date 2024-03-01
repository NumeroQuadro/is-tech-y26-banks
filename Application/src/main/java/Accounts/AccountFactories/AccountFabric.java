package Accounts.AccountFactories;

import Accounts.Client.Client;
import Accounts.Client.ClientBuilder;
import Accounts.CreditAccount;
import Accounts.DebitAccount;
import Accounts.DepositAccount;
import interfaces.InterestCalculable;

/**
 * Factory for creating accounts. Implements AccountCreatable interface
 */
public class AccountFabric implements AccountCreatable {

    @Override
    public CreditAccount createCreditAccount(String accountNumber, Client client) {
        return new CreditAccount(accountNumber, client);
    }

    @Override
    public DebitAccount createDebitAccount(String accountNumber, Client client) {
        return new DebitAccount(accountNumber, client);
    }

    @Override
    public DepositAccount createDepositAccount(String accountNumber, Client client, InterestCalculable interestCalculator) {
        return new DepositAccount(accountNumber, interestCalculator, client);
    }
}
