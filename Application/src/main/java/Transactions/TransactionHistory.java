package Transactions;

import lombok.Getter;

import java.util.Stack;

@Getter
public class TransactionHistory {
    private final Stack<TransactionModel> transactions = new Stack<>();

    public void addTransaction(TransactionModel transaction) {
        this.transactions.push(transaction);
    }
    public void removeTransaction(TransactionModel transaction) {
        transactions.remove(transaction);
    }
    public void removeTransactionUUID(String transactionUUID) {
        this.transactions.removeIf(transaction -> transaction.getTransactionUUID().equals(transactionUUID));
    }


}
