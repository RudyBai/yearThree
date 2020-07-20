package PartOne;

import java.rmi.RemoteException;
import java.util.Random;

public class HumidityTemperatureWorker extends Thread {
    private Random random;
    private Notify notify;
    private float humidity;
    private float temperature;

    HumidityTemperatureWorker(Notify notify)    {
        this.notify = notify;
        this.random = new Random();
    }

    @Override
    public void run() {
        this.humidity = random.nextFloat() * 60;
        this.temperature = (random.nextFloat() * 20) + 20;
        try {
            Thread.sleep(1500);
            notify.doneIt();
        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
    }

    float getHumidity() {
        return this.humidity;
    }

    float getTemperature() {
        return this.temperature;
    }

}
