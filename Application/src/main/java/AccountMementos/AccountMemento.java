package AccountMementos;

import Accounts.Client.Client;
import lombok.Getter;
import InterestManagers.InterestManager;
import Transactions.TransactionHistory;
import Transactions.TransactionTypes;
import interfaces.InterestCalculable;


/**
 * Memento model class which stores general information for the Account class.
 * @param amount
 * @param actualCommission
 * @param accountNumber
 * @param interestManager
 * @param client
 * @param interestRateCalculator
 * @param transactionHistory
 * @param transactionType
 * @param transactionUUID
 */
public record AccountMemento(
        @Getter double amount,
        @Getter double actualCommission,
        @Getter String accountNumber,
        @Getter InterestManager interestManager,
        @Getter Client client,
        @Getter InterestCalculable interestRateCalculator,
        @Getter TransactionHistory transactionHistory,
        @Getter TransactionTypes transactionType,
        @Getter String transactionUUID) {
}
