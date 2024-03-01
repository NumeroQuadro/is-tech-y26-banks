package Banks;

import lombok.Getter;

/**
 * Class for keeping percentage rate interests
 * @param lowPercentageRate
 * @param mediumPercentageRate
 * @param highPercentageRate
 */
public record PercentageRateInterests(@Getter double lowPercentageRate, @Getter double mediumPercentageRate, @Getter double highPercentageRate) { }
