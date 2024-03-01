package ProtectedAccounts.ProtectedAccountFactories;

import Accounts.AccountFactories.AccountFabric;
import Accounts.Client.Client;
import Banks.PercentageRateInterests;
import InterestCalculators.InterestCalculator;
import ProtectedAccounts.ProtectedCreditAccount;
import ProtectedAccounts.ProtectedDebitAccount;
import ProtectedAccounts.ProtectedDepositAccount;
import ProtectedAccounts.ProtectedAccountFactories.ProtectedAccountFactoriesInterfaces.ProtectedAccountCreatable;
import interfaces.InterestCalculable;

import java.util.UUID;

public class ProtectedAccountFactory implements ProtectedAccountCreatable {

    @Override
    public ProtectedDebitAccount createProtectedDebitAccount(double doubtfulLimit, Client client) {
        var factory = new AccountFabric();
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString() + "account";

        return new ProtectedDebitAccount(factory, doubtfulLimit, uuidString, client);
    }

    @Override
    public ProtectedDepositAccount createProtectedDepositAccount(PercentageRateInterests rateInterests, Client client, double doubtfulLimit) {
        var factory = new AccountFabric();
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString() + "account";

        InterestCalculable calculator = new InterestCalculator(rateInterests.lowPercentageRate(), rateInterests.getMediumPercentageRate(), rateInterests.getHighPercentageRate());

        return new ProtectedDepositAccount(factory, doubtfulLimit, uuidString, client, calculator);
    }

    @Override
    public ProtectedCreditAccount createProtectedCreditAccount(double doubtfulLimit, Client client) {
        var factory = new AccountFabric();
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString() + "account";

        return new ProtectedCreditAccount(factory, doubtfulLimit, uuidString, client);
    }
}
