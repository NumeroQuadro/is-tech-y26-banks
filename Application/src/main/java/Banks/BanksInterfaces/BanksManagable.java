package Banks.BanksInterfaces;

import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;

public interface BanksManagable {
    void transferMoneyBetweenBanks(ProtectedTransactable from, String recipientBankName, String accountNumberTo, double amount);
    AccountsManagable CreateBank(double doubtfulLimit, double highRatePercentage, double middleRatePercentage, double lowRatePercentage, String bankName);
}
