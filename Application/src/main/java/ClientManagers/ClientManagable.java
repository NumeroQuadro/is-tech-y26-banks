package ClientManagers;

import Accounts.Client.Client;

/**
 * Interface for managing clients
 */
public interface ClientManagable {
    public Client registerNewClient(String name, String surname, String address, String email, String phoneNumber);
}
