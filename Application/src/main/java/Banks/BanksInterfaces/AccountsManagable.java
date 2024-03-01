package Banks.BanksInterfaces;

import Accounts.Client.Client;
import Accounts.Client.ClientBuilder;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import interfaces.InterestCalculable;

import java.util.Collection;

public interface AccountsManagable {
    void depositMoneyToAccount(double amount, String accountNumber);
    void withdrawMoneyFromAccount(double amount, String accountNumber) throws TransactionForbiddenException;
    void transferMoneyBetweenAccounts(double amount, String fromAccountNumber, String toAccountNumber) throws TransactionForbiddenException;
    void transferMoneyBetweenBanks(String accountNumberFrom, String recipientBankName, String accountNumberTo, double amount);
    void cancelTransaction(String transactionUUID, String accountNumber);
    void updateDailyChanges();
    void chargeInterests();
    String getBankName();
    double getCommission();
    Collection<ProtectedTransactable> getAccounts();
    ProtectedTransactable createDebitAccount(Client client);
    ProtectedTransactable createCreditAccount(Client client);
    ProtectedTransactable createDepositAccount(Client client);
}
