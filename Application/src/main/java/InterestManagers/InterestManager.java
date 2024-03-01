package InterestManagers;

import InterestManagers.InterstManagersInterfaces.InterestManagable;
import lombok.Getter;
import interfaces.InterestCalculable;

@Getter
public class InterestManager implements InterestManagable {
    private double accumulatedInterests;
    private final InterestCalculable concreteInterestCalculator;

    public InterestManager(InterestCalculable concreteInterestCalculator) {
        this.concreteInterestCalculator = concreteInterestCalculator;
    }

    @Override
    public double manageInterest(double balance) {
        double interests = concreteInterestCalculator.calculateInterestRate(balance);
        accumulatedInterests += interests;

        return interests;
    }
}
