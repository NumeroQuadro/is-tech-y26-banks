package AccountCaretakers;

import AccountMementos.AccountMemento;
import interfaces.Transactable;

import java.util.ArrayList;

public class AccountCaretaker implements Restorable {
    private ArrayList<AccountMemento> mementos = new ArrayList<>() {
    };

    @Override
    public void restoreAccount(String transactionUUID, Transactable account) {
        boolean restored = false;
        for (var memento : mementos) {
            if (memento.getTransactionUUID().equals(transactionUUID) && memento.getAccountNumber().equals(account.getAccountNumber())) {
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
