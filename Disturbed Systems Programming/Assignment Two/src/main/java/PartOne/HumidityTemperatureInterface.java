package PartOne;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HumidityTemperatureInterface extends Remote {
    void generateHumidityAndTemperature(Notify notify) throws RemoteException;
    float getHumidity() throws RemoteException;
    float getTemperature() throws RemoteException;
}