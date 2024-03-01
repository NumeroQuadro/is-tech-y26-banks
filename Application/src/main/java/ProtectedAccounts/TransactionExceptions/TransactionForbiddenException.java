package ProtectedAccounts.TransactionExceptions;

public class TransactionForbiddenException extends Exception {
    public TransactionForbiddenException(String message) {
        super(message);
    }
}
