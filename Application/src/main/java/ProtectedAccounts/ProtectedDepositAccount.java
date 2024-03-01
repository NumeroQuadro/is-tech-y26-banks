package ProtectedAccounts;

import AccountStates.AccountState;
import Accounts.AccountFactories.AccountCreatable;
import Accounts.Client.Client;
import Accounts.Client.ClientBuilder;
import Accounts.DepositAccount;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import Transactions.TransactionModel;
import interfaces.InterestCalculable;
import interfaces.Transactable;

public class ProtectedDepositAccount implements ProtectedTransactable {
    private final DepositAccount depositAccount;
    private double doubtfulLimit; // if account sus - limit of money that can be transferred


    public ProtectedDepositAccount(AccountCreatable factory, double doubtfulLimit, String accountNumber, Client client, InterestCalculable interestCalculator) {
        this.depositAccount = factory.createDepositAccount(accountNumber, client, interestCalculator);
        this.doubtfulLimit = doubtfulLimit;
    }

    @Override
    public void provideProtectedDeposit(double amount, String transactionUUID) {
        this.depositAccount.depositMoney(amount, transactionUUID);
    }

    @Override
    public void provideProtectedWithdraw(double amount, String transactionUUID) throws TransactionForbiddenException {
        if (depositAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for deposit account");
            }
        }

        this.depositAccount.withdrawMoney(amount, transactionUUID);
    }

    @Override
    public void provideProtectedTransfer(double amount, Transactable recipientAccount, String transactionUUID) throws TransactionForbiddenException {
        if (this.depositAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for deposit account");
            }
        }

        this.depositAccount.transferMoney(amount, recipientAccount, transactionUUID);
    }

    @Override
    public double provideProtectedDailyCalculateInterests() {
        return this.depositAccount.dailyCalculateInterests();
    }

    @Override
    public void provideProtectedCancellationTransaction(TransactionModel transactionModel) {
        this.depositAccount.cancelTransaction(transactionModel.getTransactionUUID());
    }

    @Override
    public double provideProtectedChargingInterests(String transactionUUID) {
        return this.depositAccount.chargeInterests(transactionUUID);
    }

    @Override
    public Transactable getAccount() {
        return this.depositAccount;
    }
}
