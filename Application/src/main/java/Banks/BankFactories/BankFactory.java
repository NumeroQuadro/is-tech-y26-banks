package Banks.BankFactories;

import Banks.BanksInterfaces.AccountsManagable;
import Banks.OrdinaryBank;
import Banks.PercentageRateInterests;

public class BankFactory implements BankCreatable {
    private PercentageRateInterests percentageRateInterests;
    private final double commission;
    private final double doubtfulLimit;
    public BankFactory(double lowLimitPercentage, double mediumLimitPercentage, double highLimitPercentage, double doubtfulLimit, double commission) {
        this.commission = commission;
        this.percentageRateInterests =  new PercentageRateInterests(lowLimitPercentage, mediumLimitPercentage, highLimitPercentage);
        this.doubtfulLimit = doubtfulLimit;
    }


    @Override
    public OrdinaryBank createBank(String bankName) {
        return new OrdinaryBank(bankName, doubtfulLimit, commission, percentageRateInterests);
    }
}
