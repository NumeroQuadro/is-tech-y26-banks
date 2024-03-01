package Banks.BanksInterfaces;

import Banks.OrdinaryBank;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;

/**
 * Interface for managing banks
 */
public interface BanksManagable {
    void transferMoneyBetweenBanks(ProtectedTransactable from, String recipientBankName, String accountNumberTo, double amount);
    OrdinaryBank CreateBank(double doubtfulLimit, double highRatePercentage, double middleRatePercentage, double lowRatePercentage, String bankName, double commission);
}
