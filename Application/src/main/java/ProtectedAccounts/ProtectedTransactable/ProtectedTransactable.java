package ProtectedAccounts.ProtectedTransactable;

import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import Transactions.TransactionModel;
import interfaces.Transactable;

public interface ProtectedTransactable {
    void provideProtectedDeposit(double amount, String transactionUUID);
    void provideProtectedWithdraw(double amount, String transactionUUID) throws TransactionForbiddenException;
    void provideProtectedTransfer(double amount, Transactable recipientAccount, String transactionUUID) throws TransactionForbiddenException;
    double provideProtectedDailyCalculateInterests();
    void provideProtectedCancellationTransaction(TransactionModel transactionModel);
    double provideProtectedChargingInterests(String transactionUUID);
    Transactable getAccount();
}
