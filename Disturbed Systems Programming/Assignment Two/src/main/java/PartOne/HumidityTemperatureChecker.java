package PartOne;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HumidityTemperatureChecker extends UnicastRemoteObject implements Notify {

    private Client client;
    private HumidityTemperatureInterface humidityTemperatureInterface;

    HumidityTemperatureChecker(Client client, HumidityTemperatureInterface server) throws RemoteException {
        this.client = client;
        this.humidityTemperatureInterface = server;
    }

    @Override
    public void doneIt() {
        try {
            client.setHumidity(humidityTemperatureInterface.getHumidity());
            client.setTemperature(humidityTemperatureInterface.getTemperature());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
