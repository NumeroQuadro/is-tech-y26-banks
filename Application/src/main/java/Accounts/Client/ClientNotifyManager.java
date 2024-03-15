package Accounts.Client;

import Banks.PercentageRateInterests;

public class ClientNotifyManager implements ClientNotifable {
    private final Client client;

    public ClientNotifyManager(Client client) {
        this.client = client;
    }

    @Override
    public void notifyClient(PercentageRateInterests rateInterests, String accountNumber) {
        System.out.println("Notifying client " + client.getName() + " " + client.getSurname() +
                " about the interest rate change for account " +
                accountNumber + " to " +
                rateInterests.getLowPercentageRate() + "%" +
                rateInterests.getMediumPercentageRate() + "%" +
                rateInterests.getHighPercentageRate() + "%");
    }
}
