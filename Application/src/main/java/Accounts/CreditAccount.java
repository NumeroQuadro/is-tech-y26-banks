package Accounts;

import AccountCaretakers.AccountCaretaker;
import AccountCaretakers.Restorable;
import AccountMementos.AccountMemento;
import Accounts.Client.Client;
import Accounts.Client.ClientBuilder;
import AccountStates.AccountState;
import AccountStates.AccountStatesInterfaces.AccountStatable;
import AccountStates.SuspiciousState;
import Transactions.TransactionHistory;
import Transactions.TransactionModel;
import Transactions.TransactionTypes;
import interfaces.Transactable;
import lombok.Getter;

public class CreditAccount implements Transactable {
    @Getter private double currentBalance = 0;
    @Getter private final String accountNumber;
    @Getter private Client client;
    @Getter private TransactionHistory transactionHistory = new TransactionHistory();
    @Getter private final Restorable accountCaretaker = new AccountCaretaker();
    @Getter private final AccountStateManager accountStateManager = new AccountStateManager();
    private final AccountStatable accountState;

    public CreditAccount(String accountNumber, Client client) {
        this.accountNumber = accountNumber;
        this.client = client;
        this.accountState = new SuspiciousState();
        accountStateManager.checkAndMoveAccountState(accountState, client);
    }

    @Override
    public void depositMoney(double amount, String transactionUUID) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        this.currentBalance += amount;

        var transaction = new TransactionModel(this, null, amount, TransactionTypes.DEPOSIT, transactionUUID);
        transactionHistory.addTransaction(transaction);
    }

    @Override
    public void withdrawMoney(double amount, String transactionUUID) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be greater than 0");
        } // no check to negative balance because it's a credit account

        this.currentBalance -= amount;

        var transaction = new TransactionModel(this, null, amount, TransactionTypes.WITHDRAW, transactionUUID);
        transactionHistory.addTransaction(transaction);

        // TODO: implement commission for negative balance
    }

    @Override
    public void transferMoney(double amount, Transactable accountToTransferTo, String transactionUUID) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than 0");
        }

        if(amount > this.currentBalance) {
            throw new IllegalArgumentException("Transfer amount must be less than or equal to current balance");
        }

        this.currentBalance -= amount;
        try {
            accountToTransferTo.depositMoney(amount, transactionUUID);

            var transaction = new TransactionModel(this, accountToTransferTo, amount, TransactionTypes.TRANSFER, transactionUUID);
            transactionHistory.addTransaction(transaction);
        }
        catch (IllegalArgumentException e) {
            this.currentBalance += amount; // return the money to the account
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
    public double dailyCalculateInterests() { return 0; }

    @Override
    public double chargeInterests(String transactionUUID) {
        return 0;
    }

    @Override
    public void restoreMemento(AccountMemento memento) {
        if (memento.getAccountNumber().equals(this.accountNumber)) {
            if (memento.getTransactionType().equals(TransactionTypes.DEPOSIT)) {
                this.currentBalance -= memento.getAmount();
            }
            else if (memento.getTransactionType().equals(TransactionTypes.WITHDRAW)) {
                this.currentBalance += memento.getAmount();
            }
            else if (memento.getTransactionType().equals(TransactionTypes.TRANSFER)) {
                this.currentBalance += memento.getAmount();
            }

            this.client = memento.getClient();
            this.transactionHistory = memento.getTransactionHistory();
        }
        else {
            throw new IllegalArgumentException("CreditAccount number mismatch");
        }
    }

    @Override
    public AccountState getAccountState() {
        return accountState.getCurrentAccountState();
    }

    @Override
    public AccountMemento saveMemento(String transactionUUID, TransactionTypes transactionType) {
        return new AccountMemento(
                this.currentBalance,
                this.accountNumber,
                null,
                client,
                null,
                transactionHistory,
                transactionType,
                transactionUUID
        );
    }
}
