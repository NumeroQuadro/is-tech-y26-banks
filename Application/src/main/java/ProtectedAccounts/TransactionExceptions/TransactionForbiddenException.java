package ProtectedAccounts.TransactionExceptions;

/**
 * Exception for forbidden transactions
 */
public class TransactionForbiddenException extends Exception {
    public TransactionForbiddenException(String message) {
        super(message);
    }
}
