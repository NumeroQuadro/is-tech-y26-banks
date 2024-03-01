package Transactions;

import lombok.Getter;
import interfaces.Transactable;

import java.util.UUID;

public record TransactionModel(@Getter Transactable from, @Getter Transactable to, @Getter double amount, @Getter TransactionTypes type, @Getter String transactionUUID, @Getter double commission) {
    public static String GenerateTransactionUUIDString(TransactionTypes type) {
        UUID uuid = UUID.randomUUID();
        return "transaction-" + uuid.toString() + "-" + type.toString();
    }


}
