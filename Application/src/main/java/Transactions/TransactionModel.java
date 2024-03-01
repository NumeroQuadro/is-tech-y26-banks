package Transactions;

import lombok.Getter;
import interfaces.Transactable;

import java.util.UUID;

/**
 * Class for transaction model which stores general information about a certain transaction
 * @param from
 * @param to
 * @param amount
 * @param type
 * @param transactionUUID
 * @param commission
 */
public record TransactionModel(@Getter Transactable from, @Getter Transactable to, @Getter double amount, @Getter TransactionTypes type, @Getter String transactionUUID, @Getter double commission) {
    public static String GenerateTransactionUUIDString(TransactionTypes type) {
        UUID uuid = UUID.randomUUID();
        return "transaction-" + uuid.toString() + "-" + type.toString();
    }


}
