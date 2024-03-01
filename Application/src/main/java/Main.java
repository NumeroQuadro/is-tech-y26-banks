import Accounts.Client.ClientBuilder;
import Banks.BanksInterfaces.BanksManagable;
import Banks.CentralBank;
import InterestCalculators.InterestCalculator;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import interfaces.InterestCalculable;

public class Main {
    public static void main(String[] args) {
        var clientBuilder = new ClientBuilder();
        clientBuilder.setName("Dimon");
        clientBuilder.setSurname("Limon");
        clientBuilder.setEmail("dimabelunin7@gmail.com");
        clientBuilder.setPassportNumber("239239");
        clientBuilder.setPhoneNumber("+79213971428");

        BanksManagable centralBank = new CentralBank();

        var bank = centralBank.CreateBank(2, 1, 2, 3, "Tinkoff", 239.0);
        var account = bank.createDepositAccount(clientBuilder.buildClient());

        bank.depositMoneyToAccount(1000, account.getAccount().getAccountNumber());
        bank.updateDailyChanges();
        bank.updateDailyChanges();
        bank.updateDailyChanges();
        bank.updateDailyChanges();
        bank.chargeInterests();
        System.out.println(account.getAccount().getCurrentBalance());
    }
}
