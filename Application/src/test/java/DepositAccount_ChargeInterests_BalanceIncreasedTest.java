import Accounts.Client.ClientBuilder;
import Banks.BanksInterfaces.BanksManagable;
import Banks.CentralBank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositAccount_ChargeInterests_BalanceIncreasedTest {
    private final ClientBuilder clientBuilder = new ClientBuilder();
    BanksManagable centralBank = new CentralBank();
    @Test
    void chargeInterests_BalanceIncreased() {
        clientBuilder.setName("Dimon");
        clientBuilder.setSurname("Limon");
        clientBuilder.setEmail("dimabelunin7@gmail.com");
        clientBuilder.setPassportNumber("239239");
        clientBuilder.setPhoneNumber("+79213971428");

        var bank = centralBank.CreateBank(2, 1, 2, 3, "Tinkoff", 239.0);
        var account = bank.createDepositAccount(clientBuilder.buildClient());

        bank.depositMoneyToAccount(1000, account.getAccount().getAccountNumber());
        bank.updateDailyChanges();
        bank.updateDailyChanges();
        bank.updateDailyChanges();
        bank.updateDailyChanges();
        bank.chargeInterests();
        assertEquals(1080, account.getAccount().getCurrentBalance());
    }
}
