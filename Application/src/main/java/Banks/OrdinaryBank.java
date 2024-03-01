package Banks;

import lombok.Getter;
import lombok.Setter;
import Accounts.Client.Client;
import Banks.BanksInterfaces.AccountsManagable;
import Banks.BanksInterfaces.BanksManagable;
import ProtectedAccounts.ProtectedAccountFactories.ProtectedAccountFactoriesInterfaces.ProtectedAccountCreatable;
import ProtectedAccounts.ProtectedTransactable.ProtectedTransactable;
import ProtectedAccounts.ProtectedAccountFactories.ProtectedAccountFactory;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import Transactions.TransactionHistory;
import Transactions.TransactionModel;
import Transactions.TransactionTypes;
import interfaces.InterestCalculable;

import java.util.*;

public class OrdinaryBank implements AccountsManagable {
    ProtectedAccountCreatable accountFactory = new ProtectedAccountFactory();
    private @Getter final String BankName;
    private BanksManagable centralBank;
    private @Setter double doubtfulLimit;
    private final TransactionHistory transactionHistory = new TransactionHistory();
    private final ArrayList<ProtectedTransactable> accounts = new ArrayList<>();
    private final PercentageRateInterests percentageRateInterests;

    public OrdinaryBank(String bankName, double doubtfulLimit, PercentageRateInterests percentageRateInterests) {
        BankName = bankName;
        this.doubtfulLimit = doubtfulLimit;
        this.percentageRateInterests = percentageRateInterests;
    }

    @Override
    public void updateDailyChanges(InterestCalculable concreteInterestCalculator) {
        for (ProtectedTransactable account : accounts) {
            account.provideProtectedDailyCalculateInterests();
        }
    }

    @Override
    public void chargeInterests() {
        for (ProtectedTransactable account : accounts) {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString() + "interest";
            double amount = account.provideProtectedChargingInterests(uuidString);

            var transaction = new TransactionModel(account.getAccount(), null, amount, TransactionTypes.CHARGE, uuidString);
            this.transactionHistory.addTransaction(transaction);
        }
    }

    @Override
    public Collection<ProtectedTransactable> getAccounts() {
        return Collections.unmodifiableCollection(accounts);
    }

    @Override
    public void depositMoneyToAccount(double amount, String accountNumber) {
        UUID uuid = UUID.randomUUID();
        var uuidString = uuid.toString() + "deposit";

        for (ProtectedTransactable account : accounts) {
            if (account.getAccount().getAccountNumber().equals(accountNumber)) {
                account.provideProtectedDeposit(amount, uuidString);

                var transaction = new TransactionModel(account.getAccount(), null, amount, TransactionTypes.DEPOSIT, uuid.toString());
                transactionHistory.addTransaction(transaction);
                return;
            }
        }
    }

    @Override
    public void withdrawMoneyFromAccount(double amount, String accountNumber) {
        UUID uuid = UUID.randomUUID();
        var uuidString = uuid.toString() + "withdraw";

        try {
            for (ProtectedTransactable account : accounts) {
                if (account.getAccount().getAccountNumber().equals(accountNumber)) {
                    account.provideProtectedWithdraw(amount, uuidString);

                    var transaction = new TransactionModel(account.getAccount(), null, amount, TransactionTypes.WITHDRAW, uuid.toString());
                    transactionHistory.addTransaction(transaction);
                    return;
                }
            }
        }
        catch(TransactionForbiddenException e) {
            System.out.println(e.getMessage()); // TODO: log this to client manager
        }
    }

    @Override
    public void transferMoneyBetweenAccounts(double amount, String fromAccountNumber, String toAccountNumber) {
        UUID uuid = UUID.randomUUID();
        var uuidString = uuid.toString() + "transfer";

        try {
            for (ProtectedTransactable fromAccount : accounts) {
                if (fromAccount.getAccount().getAccountNumber().equals(fromAccountNumber)) {
                    for (ProtectedTransactable toAccount : accounts) {
                        if (toAccount.getAccount().getAccountNumber().equals(toAccountNumber)) {
                            fromAccount.provideProtectedTransfer(amount, toAccount.getAccount(), uuidString);

                            var transaction = new TransactionModel(fromAccount.getAccount(), toAccount.getAccount(), amount, TransactionTypes.TRANSFER, uuid.toString());
                            transactionHistory.addTransaction(transaction);
                            return;
                        }
                    }
                }
            }
        }
        catch (TransactionForbiddenException e) {
            System.out.println(e.getMessage()); // TODO: log this to client manager
        }
    }

    @Override
    public void transferMoneyBetweenBanks(String accountNumberFrom, String recipientBankName, String accountNumberTo, double amount) {
        // 1. find is there are accountNumberFrom in this bank
        // 2. call central bank and give it all info to transfer money
    }

    @Override
    public void CancelTransaction(String transactionUUID, String accountNumber) {
        // TODO: OrdinaryBank should save memento to caretaker after each transaction
        Stack<TransactionModel> transactions = transactionHistory.getTransactions();

        for (TransactionModel transaction : transactions) {
            if (transaction.getTransactionUUID().equals(transactionUUID)) {
                transactionHistory.removeTransaction(transaction);

                for (ProtectedTransactable account : accounts) {
                    if (account.getAccount().getAccountNumber().equals(accountNumber)) {
                        processTransactionCancellation(transaction, account);
                        return;
                    }
                }
                return;
            }
        }
    }

    @Override
    public ProtectedTransactable createDebitAccount(Client client) {
        var account = accountFactory.createProtectedDebitAccount(doubtfulLimit, client);
        accounts.add(account);

        return account;
    }

    @Override
    public ProtectedTransactable createCreditAccount(Client client) {
        var account = accountFactory.createProtectedCreditAccount(doubtfulLimit, client);
        accounts.add(account);

        return account;
    }

    @Override
    public ProtectedTransactable createDepositAccount(Client client) {
        var account = accountFactory.createProtectedDepositAccount(percentageRateInterests, client, doubtfulLimit);
        accounts.add(account);

        return account;
    }

    private void processTransactionCancellation(TransactionModel transaction, ProtectedTransactable accountToCancelTransaction) throws IllegalArgumentException {
        var transactionType = transaction.getType();

        if (transactionType.equals(TransactionTypes.TRANSFER)) {
            if (transaction.getTo() == null) {
                throw new IllegalArgumentException("Transaction not found! For transaction type: " + transactionType + "recipient not found!");
            }

            for (ProtectedTransactable recipientAccount : accounts) {
                if (recipientAccount.getAccount().getAccountNumber().equals(transaction.getTo().getAccountNumber())) {
                    recipientAccount.provideProtectedCancellationTransaction(transaction);
                    break;
                }
            }
        }

        accountToCancelTransaction.provideProtectedCancellationTransaction(transaction);
    }
}
