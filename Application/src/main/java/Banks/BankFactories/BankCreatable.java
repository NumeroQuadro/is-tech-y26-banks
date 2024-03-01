package Banks.BankFactories;

import Banks.BanksInterfaces.AccountsManagable;

public interface BankCreatable {
     AccountsManagable createBank(String bankName);
}
