import Accounts.Client.ClientBuilder;
import Banks.BanksInterfaces.BanksManagable;
import Banks.CentralBank;
import Banks.OrdinaryBank;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepositAccount_BalanceIncreased_ExceptionDoubtfulLimitExceededTest {
    private final BanksManagable centralBank = new CentralBank();
    private final ClientBuilder clientBuilder = new ClientBuilder();
    @Test
    void DepositAccountTest() {
        clientBuilder.setName("Dimon");
        clientBuilder.setSurname("Limon");
        clientBuilder.setPhoneNumber("+79213971428");

        var bank = centralBank.CreateBank(1, 1, 2, 3, "Tinkoff");
        var account = bank.createDepositAccount(clientBuilder.buildClient());

        bank.depositMoneyToAccount(1000, account.getAccount().getAccountNumber());

        assertThrows(TransactionForbiddenException.class, () -> {
            bank.withdrawMoneyFromAccount(100, account.getAccount().getAccountNumber());
        });
    }
}
