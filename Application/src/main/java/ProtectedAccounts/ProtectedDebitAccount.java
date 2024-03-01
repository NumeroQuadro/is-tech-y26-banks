package ProtectedAccounts;

import AccountStates.AccountState;
import Accounts.AccountFactories.AccountCreatable;
import Accounts.Client.Client;
import Accounts.DebitAccount;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import Transactions.TransactionModel;
import interfaces.Transactable;

public class ProtectedDebitAccount implements ProtectedTransactable {
    private final DebitAccount debitAccount;
    private final double doubtfulLimit; // if account sus - limit of money that can be transferred
    public ProtectedDebitAccount(AccountCreatable factory, double doubtfulLimit, String accountNumber, Client client) {
        this.debitAccount = factory.createDebitAccount(accountNumber, client);
        this.doubtfulLimit = doubtfulLimit;
    }

    @Override
    public void provideProtectedDeposit(double amount, String transactionUUID) {
        debitAccount.depositMoney(amount, transactionUUID);
    }

    @Override
    public void provideProtectedWithdraw(double amount, String transactionUUID) throws TransactionForbiddenException {
        if (debitAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for debit account");
            }
        }

        this.debitAccount.withdrawMoney(amount, transactionUUID);
    }

    @Override
    public void provideProtectedTransfer(double amount, Transactable recipientAccount, String transactionUUID) throws TransactionForbiddenException {
        if (debitAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for debit account");
            }
        }

        this.debitAccount.transferMoney(amount, recipientAccount, transactionUUID);
    }

    @Override
    public double provideProtectedDailyCalculateInterests() {
        return this.debitAccount.dailyCalculateInterests();
    }

    @Override
    public void provideProtectedCancellationTransaction(TransactionModel transactionModel) {
        this.debitAccount.cancelTransaction(transactionModel.getTransactionUUID());
    }

    @Override
    public double provideProtectedChargingInterests(String transactionUUID) {
        return this.debitAccount.chargeInterests(transactionUUID);
    }

    @Override
    public Transactable getAccount() {
        return this.debitAccount;
    }
}
