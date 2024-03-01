package Banks.BanksInterfaces;

import Accounts.Client.Client;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import interfaces.InterestCalculable;

import java.util.Collection;

public interface AccountsManagable {
    void depositMoneyToAccount(double amount, String accountNumber);
    void withdrawMoneyFromAccount(double amount, String accountNumber);
    void transferMoneyBetweenAccounts(double amount, String fromAccountNumber, String toAccountNumber);
    void transferMoneyBetweenBanks(String accountNumberFrom, String recipientBankName, String accountNumberTo, double amount);
    void CancelTransaction(String transactionUUID, String accountNumber);
    void updateDailyChanges(InterestCalculable concreteInterestCalculator);
    void chargeInterests();
    String getBankName();
    Collection<ProtectedTransactable> getAccounts();
    ProtectedTransactable createDebitAccount(Client client);
    ProtectedTransactable createCreditAccount(Client client);
    ProtectedTransactable createDepositAccount(Client client);
}
