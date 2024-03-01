import Accounts.Client.ClientBuilder;
import Banks.BanksInterfaces.BanksManagable;
import Banks.CentralBank;
import ProtectedAccounts.TransactionExceptions.TransactionForbiddenException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditAccount_DepositToNegativeBalanceAccount_CommissionWasTakenTest {
    private final ClientBuilder clientBuilder = new ClientBuilder();
    private final BanksManagable centralBank = new CentralBank();

    @Test
    void testDepositToNegativeBalanceAccount() {
        clientBuilder.setName("Dimon");
        clientBuilder.setSurname("Limon");
        clientBuilder.setEmail("dimabelunin7@gmail.com");
        clientBuilder.setPassportNumber("239239");
        clientBuilder.setPhoneNumber("+79213971428");

        var bank = centralBank.CreateBank(2, 1, 2, 3, "Tinkoff", 239.0);
        var account = bank.createCreditAccount(clientBuilder.buildClient(), 0);

        bank.depositMoneyToAccount(1000, account.getAccount().getAccountNumber());
        try {
            bank.withdrawMoneyFromAccount(10000, account.getAccount().getAccountNumber());
        }
        catch (TransactionForbiddenException e) {
            System.out.println("Transaction failed" + e);
        }
        bank.depositMoneyToAccount(1000, account.getAccount().getAccountNumber());

        assertEquals(-8239.0, account.getAccount().getCurrentBalance());
    }
}
