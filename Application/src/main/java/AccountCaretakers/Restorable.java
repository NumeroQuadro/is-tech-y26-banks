package AccountCaretakers;

import AccountMementos.AccountMemento;
import interfaces.Transactable;

/**
 * Interface for the AccountCaretakers to implement.
 */
public interface Restorable {
    void restoreAccount(String accountNumber, Transactable account);
    void AddMemento(AccountMemento memento);
}
