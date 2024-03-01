package interfaces;

import AccountMementos.AccountMemento;
import AccountStates.AccountState;
import Transactions.TransactionHistory;
import Transactions.TransactionTypes;

public interface Transactable {
    void depositMoney(double amount, String transactionUUID, double commission);
    void withdrawMoney(double amount, String transactionUUID, double commission);
    void transferMoney(double amount, Transactable accountToTransferTo, String transactionUUID, double commission);
    void cancelTransaction(String transactionUUID);
    double dailyCalculateInterests();
    double chargeInterests(String transactionUUID);
    void restoreMemento(AccountMemento memento);
    String getAccountNumber();
    double getCurrentBalance();
    AccountState getAccountState();
    TransactionHistory getTransactionHistory();
    AccountMemento saveMemento(String transactionUUID, TransactionTypes transactionType, double actualCommission);
}
