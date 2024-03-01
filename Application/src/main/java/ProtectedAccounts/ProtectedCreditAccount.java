package ProtectedAccounts;

import AccountStates.AccountState;
import Accounts.AccountFactories.AccountCreatable;
import Accounts.Client.Client;
import Accounts.CreditAccount;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import Transactions.TransactionModel;
import Transactions.TransactionTypes;
import interfaces.Transactable;

public class ProtectedCreditAccount implements ProtectedTransactable {
    private CreditAccount creditAccount;
    private final double doubtfulLimit; // if account sus - limit of money that can be transferred

    public ProtectedCreditAccount(AccountCreatable factory, double doubtfulLimit, String accountNumber, Client client) {
        this.creditAccount = factory.createCreditAccount(accountNumber, client);
        this.doubtfulLimit = doubtfulLimit;
    }

    @Override
    public void provideProtectedDeposit(double amount, String transactionUUID, double commission) {
        this.creditAccount.depositMoney(amount, transactionUUID, commission);
        this.creditAccount.saveMemento(transactionUUID, TransactionTypes.DEPOSIT, commission);
    }

    @Override
    public void provideProtectedWithdraw(double amount, String transactionUUID, double commission) throws TransactionForbiddenException, IllegalArgumentException {

        if (creditAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for credit account");
            }
        }

        this.creditAccount.withdrawMoney(amount, transactionUUID, commission);
        this.creditAccount.saveMemento(transactionUUID, TransactionTypes.WITHDRAW, commission);
    }

    @Override
    public void provideProtectedTransfer(double amount, Transactable recipientAccount, String transactionUUID, double commission) throws TransactionForbiddenException, IllegalArgumentException {
        if (creditAccount.getAccountState().equals(AccountState.SUSPICIOUS)) {
            if (amount > doubtfulLimit) {
                throw new TransactionForbiddenException("Amount is greater than doubtful limit for credit account");
            }
        }

        this.creditAccount.transferMoney(amount, recipientAccount, transactionUUID, commission);
        this.creditAccount.saveMemento(transactionUUID, TransactionTypes.TRANSFER, commission);
    }

    @Override
    public double provideProtectedDailyCalculateInterests() {
        return this.creditAccount.dailyCalculateInterests();
    }

    @Override
    public void provideProtectedCancellationTransaction(TransactionModel transactionModel) throws IllegalArgumentException {
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
