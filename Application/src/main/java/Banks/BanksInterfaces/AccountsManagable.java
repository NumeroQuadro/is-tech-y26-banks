package Banks.BanksInterfaces;

import Accounts.Client.Client;
import Banks.PercentageRateInterests;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;

import java.util.Collection;

/**
 * Interface for managing accounts
 */
public interface AccountsManagable {
    void depositMoneyToAccount(double amount, String accountNumber);
    void withdrawMoneyFromAccount(double amount, String accountNumber) throws TransactionForbiddenException;
    void transferMoneyBetweenAccounts(double amount, String fromAccountNumber, String toAccountNumber) throws TransactionForbiddenException;
    void transferMoneyBetweenBanks(String accountNumberFrom, String recipientBankName, String accountNumberTo, double amount);
    void cancelTransaction(String transactionUUID, String accountNumber);
    void updateDailyChanges();
    void updateAccountSettings(PercentageRateInterests rateInterests, String accountNumber);
    void chargeInterests();
    String getBankName();
    double getCommission();
    Collection<ProtectedTransactable> getAccounts();
    ProtectedTransactable createDebitAccount(Client client);
    ProtectedTransactable createCreditAccount(Client client, double initialBalance);
    ProtectedTransactable createDepositAccount(Client client);
}
