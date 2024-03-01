package ClientManagers;

import Accounts.Client.Client;
import Banks.CentralBank;

/**
 * Class for managing clients which implements ClientManagable interface
 */
public class ClientManager implements ClientManagable {
    CentralBank centralBank = new CentralBank();

    @Override
    public Client registerNewClient(String name, String surname, String address, String email, String phoneNumber) {
        return null;
    }
}
