package ProtectedAccounts;

import AccountStates.AccountState;
import Accounts.AccountFactories.AccountCreatable;
import Accounts.Client.Client;
import Accounts.DebitAccount;
import Banks.PercentageRateInterests;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import Transactions.TransactionModel;
import Transactions.TransactionTypes;
import interfaces.Transactable;

/**
 * Class for protected debit account. Protected means there are check for availability to provide transaction
 */
public class ProtectedDebitAccount implements ProtectedTransactable {
    private final DebitAccount debitAccount;
    private final double doubtfulLimit; // if account sus - limit of money that can be transferred
    public ProtectedDebitAccount(AccountCreatable factory, double doubtfulLimit, String accountNumber, Client client) {
        this.debitAccount = factory.createDebitAccount(accountNumber, client);
        this.doubtfulLimit = doubtfulLimit;
    }

    @Override
    public void provideProtectedDeposit(double amount, String transactionUUID, double commission) {
        debitAccount.depositMoney(amount, transactionUUID, commission);
        this.debitAccount.saveMemento(transactionUUID, TransactionTypes.DEPOSIT, commission);
    }

    @Override
    public void provideProtectedWithdraw(double amount, String transactionUUID, double commission) throws TransactionForbiddenException, IllegalArgumentException {
        if (debitAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for debit account");
            }
        }

        this.debitAccount.withdrawMoney(amount, transactionUUID, commission);
        this.debitAccount.saveMemento(transactionUUID, TransactionTypes.WITHDRAW, commission);
    }

    @Override
    public void provideProtectedTransfer(double amount, Transactable recipientAccount, String transactionUUID, double commission) throws TransactionForbiddenException, IllegalArgumentException {
        if (debitAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for debit account");
            }
        }

        this.debitAccount.transferMoney(amount, recipientAccount, transactionUUID, commission);
        this.debitAccount.saveMemento(transactionUUID, TransactionTypes.TRANSFER, commission);
    }

    @Override
    public double provideProtectedDailyCalculateInterests() {
        return this.debitAccount.dailyCalculateInterests();
    }

    @Override
    public void provideProtectedCancellationTransaction(TransactionModel transactionModel) throws IllegalArgumentException {
        this.debitAccount.cancelTransaction(transactionModel.getTransactionUUID());
    }

    @Override
    public double provideProtectedChargingInterests(String transactionUUID) {
        return this.debitAccount.chargeInterests(transactionUUID);
    }

    @Override
    public void provideProtectedUpdateAccountSettings(PercentageRateInterests rateInterests) {
        this.debitAccount.updateAccountSettings(rateInterests);
    }

    @Override
    public Transactable getAccount() {
        return this.debitAccount;
    }
}
