package AccountCaretakers;

import AccountMementos.AccountMemento;
import interfaces.Transactable;

public interface Restorable {
    void restoreAccount(String accountNumber, Transactable account);
    void AddMemento(AccountMemento memento);
}
