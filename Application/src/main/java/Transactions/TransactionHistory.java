package Transactions;

import java.util.Stack;

/**
 * Class for transaction history
 */
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

    public Stack<TransactionModel> getTransactions() {
        var newStack = new Stack<TransactionModel>();
        newStack.addAll(transactions);
        return newStack;
    }


}
