package Accounts;

import Accounts.Client.Client;
import lombok.Getter;
import AccountCaretakers.AccountCaretaker;
import AccountCaretakers.Restorable;
import AccountMementos.AccountMemento;
import AccountStates.AccountState;
import AccountStates.AccountStatesInterfaces.AccountStatable;
import AccountStates.SuspiciousState;
import InterestManagers.InterestManager;
import Transactions.TransactionHistory;
import Transactions.TransactionModel;
import Transactions.TransactionTypes;
import interfaces.InterestCalculable;
import interfaces.Transactable;

/**
 * Class for managing certain deposit account
 */
public class DepositAccount implements Transactable {
    @Getter private InterestCalculable interestRateCalculator;
    @Getter private double currentBalance = 0;
    @Getter private final String accountNumber;
    @Getter private final InterestManager interestManager;
    @Getter private Client client;
    @Getter private TransactionHistory transactionHistory = new TransactionHistory();
    @Getter private final Restorable accountCaretaker = new AccountCaretaker();
    @Getter private final AccountStateManager accountStateManager = new AccountStateManager();
    private final AccountStatable accountState;

    public DepositAccount(String accountNumber, InterestCalculable interestRateCalculator, Client client) {
        this.accountNumber = accountNumber;
        this.interestManager = new InterestManager(interestRateCalculator);
        this.interestRateCalculator = interestRateCalculator;
        this.client = client;
        this.accountState = new SuspiciousState();
        this.accountStateManager.checkAndMoveAccountState(accountState, client);
    }

    @Override
    public void depositMoney(double amount, String transactionUUID, double commission) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        this.currentBalance += amount;

        var transaction = new TransactionModel(this, null, amount, TransactionTypes.DEPOSIT, transactionUUID, commission);

        transactionHistory.addTransaction(transaction);
    }

    @Override
    public void withdrawMoney(double amount, String transactionUUID, double commission) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be greater than 0");
        }

        if (amount > this.currentBalance) {
            throw new IllegalArgumentException("Withdraw amount must be less than or equal to current balance");
        }

        this.currentBalance -= amount;

        var transaction = new TransactionModel(this, null, amount, TransactionTypes.WITHDRAW, transactionUUID, commission);

        transactionHistory.addTransaction(transaction);
    }

    @Override
    public void transferMoney(double amount, Transactable accountToTransferTo, String transactionUUID, double commission) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than 0");
        }

        if (amount > this.currentBalance) {
            throw new IllegalArgumentException("Transfer amount must be less than or equal to current balance");
        }

        this.currentBalance -= amount;
        try {
            accountToTransferTo.depositMoney(amount, transactionUUID, commission);

            var transaction = new TransactionModel(this, accountToTransferTo, amount, TransactionTypes.TRANSFER, transactionUUID, commission);
            transactionHistory.addTransaction(transaction);
        }
        catch (IllegalArgumentException e) {
            this.currentBalance += amount; // return the money to the account
            throw e;
        }
    }

    @Override
    public void cancelTransaction(String transactionUUID) {
        try {
            accountCaretaker.restoreAccount(transactionUUID, this);
            transactionHistory.removeTransactionUUID(transactionUUID);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Transaction not found");
        }
    }

    @Override
    public double dailyCalculateInterests() {
        return interestManager.manageInterest(this.currentBalance);
    }

    @Override
    public double chargeInterests(String transactionUUID) {
        double accumulatedInterests = interestManager.getAccumulatedInterests();
        this.currentBalance += accumulatedInterests;

        var transaction = new TransactionModel(this, null, accumulatedInterests, TransactionTypes.CHARGE, transactionUUID, 0);

        transactionHistory.addTransaction(transaction);

        return accumulatedInterests;
    }

    @Override
    public void restoreMemento(AccountMemento memento) {
        if (memento.accountNumber().equals(this.accountNumber)) {
            if (memento.transactionType().equals(TransactionTypes.DEPOSIT)) {
                this.currentBalance -= memento.amount();
            }
            else if (memento.transactionType().equals(TransactionTypes.WITHDRAW)) {
                this.currentBalance += memento.amount();
            }
            else if (memento.transactionType().equals(TransactionTypes.TRANSFER)) {
                this.currentBalance += memento.amount();
            }

            this.client = memento.client();
            this.transactionHistory = memento.transactionHistory();
        }
        else {
            throw new IllegalArgumentException("DepositAccount number mismatch");
        }
    }

    @Override
    public AccountState getAccountState() {
        return accountState.getCurrentAccountState();
    }

    @Override
    public AccountMemento saveMemento(String transactionUUID, TransactionTypes transactionType, double actualCommission) {
        var memento =  new AccountMemento(
                this.currentBalance,
                actualCommission,
                this.accountNumber,
                this.interestManager,
                this.client,
                this.interestRateCalculator,
                transactionHistory,
                transactionType,
                transactionUUID);
        accountCaretaker.AddMemento(memento);

        return memento;
    }
}
