import Accounts.Client.ClientBuilder;
import Banks.BanksInterfaces.BanksManagable;
import Banks.CentralBank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositAccount_BalanceIncreasedTest {

    @Test
    void testDeposit() {
        var clientBuilder = new ClientBuilder();
        clientBuilder.setName("Dimon");
        clientBuilder.setSurname("Limon");
        clientBuilder.setEmail("dimabelunin7@gmail.com");
        clientBuilder.setPassportNumber("239239");
        clientBuilder.setPhoneNumber("+79213971428");

        BanksManagable centralBank = new CentralBank();
        var bank = centralBank.CreateBank(1000, 1, 2, 3, "Tinkoff", 234);
        var account = bank.createDepositAccount(clientBuilder.buildClient());

        bank.depositMoneyToAccount(1000, account.getAccount().getAccountNumber());

        assertEquals(1000, account.getAccount().getCurrentBalance());
    }
}
