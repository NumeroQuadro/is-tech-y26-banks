package ProtectedAccounts;

import AccountStates.AccountState;
import Accounts.AccountFactories.AccountCreatable;
import Accounts.Client.Client;
import Accounts.CreditAccount;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import Transactions.TransactionModel;
import interfaces.Transactable;

public class ProtectedCreditAccount implements ProtectedTransactable {
    private CreditAccount creditAccount;
    private final double doubtfulLimit; // if account sus - limit of money that can be transferred

    public ProtectedCreditAccount(AccountCreatable factory, double doubtfulLimit, String accountNumber, Client client) {
        this.creditAccount = factory.createCreditAccount(accountNumber, client);
        this.doubtfulLimit = doubtfulLimit;
    }

    @Override
    public void provideProtectedDeposit(double amount, String transactionUUID) {
        this.creditAccount.depositMoney(amount, transactionUUID);
    }

    @Override
    public void provideProtectedWithdraw(double amount, String transactionUUID) throws TransactionForbiddenException {

        if (creditAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for credit account");
            }
        }

        this.creditAccount.withdrawMoney(amount, transactionUUID);
    }

    @Override
    public void provideProtectedTransfer(double amount, Transactable recipientAccount, String transactionUUID) throws TransactionForbiddenException {
        if (creditAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for credit account");
            }
        }

        this.creditAccount.transferMoney(amount, recipientAccount, transactionUUID);
    }

    @Override
    public double provideProtectedDailyCalculateInterests() {
        return this.creditAccount.dailyCalculateInterests();
    }

    @Override
    public void provideProtectedCancellationTransaction(TransactionModel transactionModel) {
        this.creditAccount.cancelTransaction(transactionModel.getTransactionUUID());
    }

    @Override
    public double provideProtectedChargingInterests(String transactionUUID) {
        return this.creditAccount.chargeInterests(transactionUUID);
    }

    @Override
    public Transactable getAccount() {
        return this.creditAccount;
    }
}
