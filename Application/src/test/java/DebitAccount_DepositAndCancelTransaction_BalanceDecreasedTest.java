import Accounts.Client.ClientBuilder;
import Banks.BanksInterfaces.BanksManagable;
import Banks.CentralBank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitAccount_DepositAndCancelTransaction_BalanceDecreasedTest {
    private final ClientBuilder clientBuilder = new ClientBuilder();
    private final BanksManagable centralBank = new CentralBank();

    @Test
    void testDepositAndCancelTransaction() {
        clientBuilder.setName("Dimon");
        clientBuilder.setSurname("Limon");
        clientBuilder.setEmail("dimabelunin7@gmail.com");
        clientBuilder.setPassportNumber("239239");
        clientBuilder.setPhoneNumber("+79213971428");

        var bank = centralBank.CreateBank(1, 1, 2, 3, "Tinkoff",234);
        var account = bank.createDebitAccount(clientBuilder.buildClient());

        bank.depositMoneyToAccount(1000, account.getAccount().getAccountNumber());
        var transactions = account.getAccount().getTransactionHistory().getTransactions();
        bank.cancelTransaction(transactions.peek().getTransactionUUID(), account.getAccount().getAccountNumber());
        assertEquals(0, account.getAccount().getCurrentBalance());
    }
}
