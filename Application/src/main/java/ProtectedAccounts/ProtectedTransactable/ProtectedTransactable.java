package ProtectedAccounts.ProtectedTransactable;

import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import Transactions.TransactionModel;
import interfaces.Transactable;

/**
 * Interface for protected transactable accounts
 */
public interface ProtectedTransactable {
    void provideProtectedDeposit(double amount, String transactionUUID, double commission);
    void provideProtectedWithdraw(double amount, String transactionUUID, double commission) throws TransactionForbiddenException;
    void provideProtectedTransfer(double amount, Transactable recipientAccount, String transactionUUID, double commission) throws TransactionForbiddenException;
    double provideProtectedDailyCalculateInterests();
    void provideProtectedCancellationTransaction(TransactionModel transactionModel);
    double provideProtectedChargingInterests(String transactionUUID);
    Transactable getAccount();
}
