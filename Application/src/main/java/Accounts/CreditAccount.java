package Accounts;

import AccountCaretakers.AccountCaretaker;
import AccountCaretakers.Restorable;
import AccountMementos.AccountMemento;
import Accounts.Client.Client;
import AccountStates.AccountState;
import AccountStates.AccountStatesInterfaces.AccountStatable;
import AccountStates.SuspiciousState;
import Accounts.Client.ClientNotifyManager;
import Banks.PercentageRateInterests;
import Transactions.TransactionHistory;
import Transactions.TransactionModel;
import Transactions.TransactionTypes;
import interfaces.Transactable;
import lombok.Getter;

/**
 * Class for managing certain credit account
 */
public class CreditAccount implements Transactable {
    @Getter private double currentBalance = 0;
    @Getter private final String accountNumber;
    @Getter private Client client;
    @Getter private TransactionHistory transactionHistory = new TransactionHistory();
    @Getter private final Restorable accountCaretaker = new AccountCaretaker();
    @Getter private final AccountStateManager accountStateManager = new AccountStateManager();
    private final ClientNotifyManager clientNotifyManager;
    private final AccountStatable accountState;

    public CreditAccount(String accountNumber, Client client, double initialBalance) {
        this.currentBalance = initialBalance;
        this.accountNumber = accountNumber;
        this.client = client;
        this.clientNotifyManager = new ClientNotifyManager(client);
        this.accountState = new SuspiciousState();
        accountStateManager.checkAndMoveAccountState(accountState, client);
    }

    @Override
    public void depositMoney(double amount, String transactionUUID, double commission) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        if (this.currentBalance < 0) {
            this.currentBalance -= commission;
        }

        this.currentBalance += amount;

        var transaction = new TransactionModel(this, null, amount, TransactionTypes.DEPOSIT, transactionUUID, commission);
        transactionHistory.addTransaction(transaction);
    }

    @Override
    public void withdrawMoney(double amount, String transactionUUID, double commission) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be greater than 0");
        } // no check to negative balance because it's a credit account

        if (this.currentBalance < 0) {
            this.currentBalance -= commission;
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

        if(amount > this.currentBalance) {
            throw new IllegalArgumentException("Transfer amount must be less than or equal to current balance");
        }

        if (this.currentBalance < 0) {
            this.currentBalance -= commission;
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
    public void updateAccountSettings(PercentageRateInterests rateInterests) {
        this.clientNotifyManager.notifyClient(rateInterests, accountNumber);
    }

    @Override
    public void cancelTransaction(String transactionUUID) throws IllegalArgumentException {
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
    public void restoreMemento(AccountMemento memento) throws IllegalArgumentException {
        if (memento.accountNumber().equals(this.accountNumber)) {
            if (memento.transactionType().equals(TransactionTypes.DEPOSIT)) {
                this.currentBalance -= memento.amount();
                this.currentBalance += memento.actualCommission();
            }
            else if (memento.transactionType().equals(TransactionTypes.WITHDRAW)) {
                this.currentBalance += memento.amount();
                this.currentBalance += memento.actualCommission();

            }
            else if (memento.transactionType().equals(TransactionTypes.TRANSFER)) {
                this.currentBalance += memento.amount();
                this.currentBalance += memento.actualCommission();
            }

            this.client = memento.client();
            this.transactionHistory = memento.transactionHistory();
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
    public AccountMemento saveMemento(String transactionUUID, TransactionTypes transactionType, double actualCommission) {
        var memento = new AccountMemento(
                this.currentBalance,
                actualCommission,
                this.accountNumber,
                null,
                client,
                null,
                transactionHistory,
                transactionType,
                transactionUUID
        );
        accountCaretaker.AddMemento(memento);

        return memento;
    }
}
