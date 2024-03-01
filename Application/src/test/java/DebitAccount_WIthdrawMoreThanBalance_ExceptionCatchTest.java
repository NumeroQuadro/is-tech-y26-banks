import Accounts.Client.ClientBuilder;
import Banks.BanksInterfaces.BanksManagable;
import Banks.CentralBank;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DebitAccount_WIthdrawMoreThanBalance_ExceptionCatchTest {
    private final ClientBuilder clientBuilder = new ClientBuilder();
    private final BanksManagable centralBank = new CentralBank();
    @Test
    void testWithdraw() {
        clientBuilder.setName("Dimon");
        clientBuilder.setSurname("Limon");
        clientBuilder.setEmail("dimabelunin7@gmail.com");
        clientBuilder.setPassportNumber("239239");
        clientBuilder.setPhoneNumber("+79213971428");

        var bank = centralBank.CreateBank(1, 1, 2, 3, "Tinkoff", 234);
        var account = bank.createDebitAccount(clientBuilder.buildClient());

        bank.depositMoneyToAccount(1000, account.getAccount().getAccountNumber());

        assertThrows(IllegalArgumentException.class, () -> {
            bank.withdrawMoneyFromAccount(10000, account.getAccount().getAccountNumber());
        });
    }
}
