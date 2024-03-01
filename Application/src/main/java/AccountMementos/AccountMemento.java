package AccountMementos;

import lombok.Getter;
import Accounts.Client.Client;
import InterestManagers.InterestManager;
import Transactions.TransactionHistory;
import Transactions.TransactionTypes;
import interfaces.InterestCalculable;

@Getter
public class AccountMemento {
    private final double amount;
    private final String accountNumber; // TODO: before restore memento, check if account numbers are equal
    private final InterestManager interestManager; // TODO: if account (credit/debit) has no interests - field must be null
    private final Client client;
    private final InterestCalculable interestRateCalculator;
    private final TransactionHistory transactionHistory;
    private final TransactionTypes transactionType;
    private final String transactionUUID;

    public AccountMemento(
            double amount,
            String accountNumber,
            InterestManager interestManager,
            Client client,
            InterestCalculable interestRateCalculator,
            TransactionHistory transactionHistory,
            TransactionTypes transactionType,
            String transactionUUID) {
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.interestManager = interestManager;
        this.client = client;
        this.interestRateCalculator = interestRateCalculator;
        this.transactionHistory = transactionHistory;
        this.transactionType = transactionType;
        this.transactionUUID = transactionUUID;
    }
}
