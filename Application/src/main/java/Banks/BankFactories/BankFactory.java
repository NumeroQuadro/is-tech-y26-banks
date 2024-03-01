package Banks.BankFactories;

import Banks.OrdinaryBank;
import Banks.PercentageRateInterests;

/**
 * Factory for creating banks
 */
public class BankFactory implements BankCreatable {
    private final PercentageRateInterests percentageRateInterests;
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
