package Banks;

import lombok.Getter;

public record PercentageRateInterests(@Getter double lowPercentageRate, @Getter double mediumPercentageRate, @Getter double highPercentageRate) { }
