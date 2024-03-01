package AccountCaretakers;

import interfaces.Transactable;

public interface Restorable {
    void restoreAccount(String accountNumber, Transactable account);
}
