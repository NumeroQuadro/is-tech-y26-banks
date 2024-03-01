import Accounts.Client.ClientBuilder;
import Banks.BanksInterfaces.BanksManagable;
import Banks.CentralBank;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;

public class Main {
    public static void main(String[] args) {
        var clientBuilder = new ClientBuilder();
        clientBuilder.setName("Dimon");
        clientBuilder.setSurname("Limon");
        clientBuilder.setEmail("dimabelunin7@gmail.com");
        clientBuilder.setPassportNumber("239239");
        clientBuilder.setPhoneNumber("+79213971428");

        BanksManagable centralBank = new CentralBank();

        var bank = centralBank.CreateBank(1, 1, 2, 3, "Tinkoff");
        var account = bank.createDebitAccount(clientBuilder.buildClient());

        bank.depositMoneyToAccount(1000, account.getAccount().getAccountNumber());
        try {
            bank.withdrawMoneyFromAccount(10000, account.getAccount().getAccountNumber());
        }
        catch (TransactionForbiddenException e) {
            System.out.println("Transaction failed" + e);
        }
        System.out.println(account.getAccount().getCurrentBalance());
    }
}
