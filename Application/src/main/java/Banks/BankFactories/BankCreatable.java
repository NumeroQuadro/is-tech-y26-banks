package Banks.BankFactories;

import Banks.OrdinaryBank;

/**
 * Interface for creating banks
 */
public interface BankCreatable {
     OrdinaryBank createBank(String bankName);
}
