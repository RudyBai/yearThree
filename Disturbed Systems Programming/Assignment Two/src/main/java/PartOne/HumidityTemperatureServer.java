package PartOne;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HumidityTemperatureServer extends UnicastRemoteObject implements HumidityTemperatureInterface, Serializable {

    private HumidityTemperatureWorker humidityTemperatureWorker;

    private HumidityTemperatureServer() throws RemoteException {
    }

    public static void main(String[] args) {
        try {
            HumidityTemperatureServer humidityTemperatureServer = new HumidityTemperatureServer();
            System.out.println("Running...");
            Naming.rebind("//localhost/hts", humidityTemperatureServer);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void generateHumidityAndTemperature(Notify notify) {
        humidityTemperatureWorker = new HumidityTemperatureWorker(notify);
        humidityTemperatureWorker.start();
    }

    public float getHumidity() {
        return humidityTemperatureWorker.getHumidity();
    }

    public float getTemperature() {
        return humidityTemperatureWorker.getTemperature();
    }
}