package Banks;

import Banks.BanksInterfaces.AccountsManagable;
import Banks.BankFactories.BankFactory;
import Banks.BanksInterfaces.BanksManagable;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;

import java.util.List;
import java.util.UUID;

public class CentralBank implements BanksManagable {
    private List<AccountsManagable> banks;

    @Override
    public void transferMoneyBetweenBanks(ProtectedTransactable from, String recipientBankName, String accountNumberTo, double amount) {
        UUID uuid = UUID.randomUUID();
        var uuidString = uuid.toString() + "transfer";

        for (AccountsManagable recipientBank : banks) {
            if (recipientBank.getBankName().equals(recipientBankName)) {
                try {
                    from.provideProtectedWithdraw(amount, uuidString, recipientBank.getCommission());

                    for (ProtectedTransactable accountTo : recipientBank.getAccounts()) {
                        if (accountTo.getAccount().getAccountNumber().equals(accountNumberTo)) {
                            accountTo.provideProtectedDeposit(amount, uuidString, recipientBank.getCommission());
                            return;
                        }
                    }
                }
                catch (TransactionForbiddenException e) {
                    System.out.println("Transaction failed");
                }
            }
        }
    }

    @Override
    public OrdinaryBank CreateBank(double doubtfulLimit, double highRatePercentage, double middleRatePercentage, double lowRatePercentage, String bankName, double commission) {
        BankFactory bankFactory = new BankFactory(doubtfulLimit, lowRatePercentage, middleRatePercentage, highRatePercentage, commission);

        return bankFactory.createBank(bankName);
    }
}
