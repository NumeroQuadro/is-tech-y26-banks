package interfaces;

import AccountMementos.AccountMemento;
import AccountStates.AccountState;
import Transactions.TransactionTypes;

public interface Transactable {
    void depositMoney(double amount, String transactionUUID);
    void withdrawMoney(double amount, String transactionUUID);
    void transferMoney(double amount, Transactable accountToTransferTo, String transactionUUID);
    void cancelTransaction(String transactionUUID);
    double dailyCalculateInterests();
    double chargeInterests(String transactionUUID);
    void restoreMemento(AccountMemento memento);
    String getAccountNumber();
    double getCurrentBalance();
    AccountState getAccountState();
    AccountMemento saveMemento(String transactionUUID, TransactionTypes transactionType);
}
