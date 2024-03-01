package Banks.BankFactories;

import Banks.BanksInterfaces.AccountsManagable;
import Banks.OrdinaryBank;

public interface BankCreatable {
     OrdinaryBank createBank(String bankName);
}
