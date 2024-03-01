package AccountCaretakers;

import AccountMementos.AccountMemento;
import interfaces.Transactable;

import java.util.ArrayList;

/**
 * The AccountCaretaker class is responsible for keeping track of the general information about accounts.
 */
public class AccountCaretaker implements Restorable {
    private final ArrayList<AccountMemento> mementos = new ArrayList<>();

    public void AddMemento(AccountMemento memento) {
        mementos.add(memento);
    }

    @Override
    public void restoreAccount(String transactionUUID, Transactable account) {
        boolean restored = false;
        for (var memento : mementos) {
            if (memento.transactionUUID().equals(transactionUUID) && memento.accountNumber().equals(account.getAccountNumber())) {
                account.restoreMemento(memento);
                mementos.remove(memento);
                restored = true;
                break;
            }
        }

        if (!restored) {
            throw new IllegalArgumentException("No memento found for this transaction");
        }
    }


}
