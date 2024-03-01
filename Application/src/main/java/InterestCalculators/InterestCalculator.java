package InterestCalculators;

import interfaces.InterestCalculable;

public class InterestCalculator implements InterestCalculable {
    private final double lowRateMultiplier;
    private final double mediumRateMultiplier;
    private final double highRateMultiplier;
    private final double lowLimit = 0.0;
    private final double mediumLimit = 50000.0;
    private final double highLimit = 100000.0;

    public InterestCalculator(double lowMultiplierPercentage, double mediumMultiplierPercentage, double highMultiplierPercentage) {
        this.lowRateMultiplier = lowMultiplierPercentage / 100;
        this.mediumRateMultiplier = mediumMultiplierPercentage / 100;
        this.highRateMultiplier = highMultiplierPercentage / 100;
    }


    @Override
    public double calculateInterestRate(double balance) {
        if (balance < lowLimit) {
            return 0;
        } else if (balance < mediumLimit) {
            return balance * lowRateMultiplier;
        } else if (balance < highLimit) {
            return balance * mediumRateMultiplier;
        } else {
            return balance * highRateMultiplier;
        }
    }
}
