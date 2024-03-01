package Banks;

import Accounts.Client.Client;
import lombok.Getter;
import lombok.Setter;
import Accounts.Client.ClientBuilder;
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

/**
 * Ordinary bank class
 */
public class OrdinaryBank implements AccountsManagable {
    ProtectedAccountCreatable accountFactory = new ProtectedAccountFactory();
    private @Getter final String BankName;
    private BanksManagable centralBank;
    private @Setter double doubtfulLimit;
    private @Getter final double commission;
    private final TransactionHistory transactionHistory = new TransactionHistory();
    private final ArrayList<ProtectedTransactable> accounts = new ArrayList<>();
    private final PercentageRateInterests percentageRateInterests;

    public OrdinaryBank(String bankName, double doubtfulLimit, double commission, PercentageRateInterests percentageRateInterests) {
        BankName = bankName;
        this.doubtfulLimit = doubtfulLimit;
        this.commission = commission;
        this.percentageRateInterests = percentageRateInterests;
    }

    @Override
    public void updateDailyChanges() {
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

            var transaction = new TransactionModel(account.getAccount(), null, amount, TransactionTypes.CHARGE, uuidString, this.commission);
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
                account.provideProtectedDeposit(amount, uuidString, this.commission);

                var transaction = new TransactionModel(account.getAccount(), null, amount, TransactionTypes.DEPOSIT, uuidString, this.commission);
                transactionHistory.addTransaction(transaction);
                return;
            }
        }
    }

    @Override
    public void withdrawMoneyFromAccount(double amount, String accountNumber) throws TransactionForbiddenException {
        UUID uuid = UUID.randomUUID();
        var uuidString = uuid.toString() + "withdraw";

        for (ProtectedTransactable account : accounts) {
            if (account.getAccount().getAccountNumber().equals(accountNumber)) {
                try {
                    account.provideProtectedWithdraw(amount, uuidString, this.commission);
                }
                catch (TransactionForbiddenException e) {
                    throw new TransactionForbiddenException("Transaction failed" + e);
                }

                var transaction = new TransactionModel(account.getAccount(), null, amount, TransactionTypes.WITHDRAW, uuidString, this.commission);
                transactionHistory.addTransaction(transaction);
                return;
            }
        }

    }

    @Override
    public void transferMoneyBetweenAccounts(double amount, String fromAccountNumber, String toAccountNumber) throws TransactionForbiddenException {
        UUID uuid = UUID.randomUUID();
        var uuidString = uuid.toString() + "transfer";

        for (ProtectedTransactable fromAccount : accounts) {
            if (fromAccount.getAccount().getAccountNumber().equals(fromAccountNumber)) {
                for (ProtectedTransactable toAccount : accounts) {
                    if (toAccount.getAccount().getAccountNumber().equals(toAccountNumber)) {
                        fromAccount.provideProtectedTransfer(amount, toAccount.getAccount(), uuidString, this.commission);

                        var transaction = new TransactionModel(fromAccount.getAccount(), toAccount.getAccount(), amount, TransactionTypes.TRANSFER, uuidString, this.commission);
                        transactionHistory.addTransaction(transaction);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void transferMoneyBetweenBanks(String accountNumberFrom, String recipientBankName, String accountNumberTo, double amount) {
        for (ProtectedTransactable account : accounts) {
            if (account.getAccount().getAccountNumber().equals(accountNumberFrom)) {
                centralBank.transferMoneyBetweenBanks(account, recipientBankName, accountNumberTo, amount);
                return;
            }
        }
    }

    @Override
    public void cancelTransaction(String transactionUUID, String accountNumber) {
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
    public ProtectedTransactable createCreditAccount(Client client, double initialBalance) {
        var account = accountFactory.createProtectedCreditAccount(doubtfulLimit, client, initialBalance);
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
