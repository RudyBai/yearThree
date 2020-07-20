package PartOne;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    private Float humidity, temperature, cloudCoverage;

    public static void main(String[] args) {
        Client client = new Client();
        HumidityTemperatureInterface humidityTemperatureServer = client.getHumidityTemperatureServer();
        CloudCoverageInterface cloudCoverageServer = client.getCloudCoverageServer();
        try {
            HumidityTemperatureChecker humidityTemperatureChecker = new HumidityTemperatureChecker(client, humidityTemperatureServer);
            CloudCoverageChecker cloudCoverageChecker = new CloudCoverageChecker(client, cloudCoverageServer);

            cloudCoverageChecker.start();

            //noinspection InfiniteLoopStatement
            while (true) {

                humidityTemperatureServer.generateHumidityAndTemperature(humidityTemperatureChecker);
                cloudCoverageServer.generateCloudCoverage();

                Thread.sleep(10000);

                float warningValue = client.getTemperature() * ((100 - client.getHumidity()) / 100) * ((100 - client.getCloudCoverage()) / 100);
                if (warningValue > 20) {
                    System.out.println("Warning status " + warningValue);
                } else {
                    System.out.println("No Warning; warningValue = " + warningValue);
                }
            }
        } catch (RemoteException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Float getHumidity() {
        return humidity;
    }

    void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    private Float getTemperature() {
        return temperature;
    }

    void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    private Float getCloudCoverage() {
        return cloudCoverage;
    }

    void setCloudCoverage(Float cloudCoverage) {
        this.cloudCoverage = cloudCoverage;
    }

    private HumidityTemperatureInterface getHumidityTemperatureServer() {

        HumidityTemperatureInterface humidityTemperatureInterface = null;

        while (humidityTemperatureInterface == null) {
            try {
                humidityTemperatureInterface = (HumidityTemperatureInterface) Naming.lookup("rmi://localhost/hts");
                System.out.println("Connected to Temperature and Humidity Server");
            } catch (ConnectException ex) {
                System.out.println("Looking for server...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (RemoteException | NotBoundException | MalformedURLException e) {
                e.printStackTrace();
                break;
            }
        }
        return humidityTemperatureInterface;
    }

    private CloudCoverageInterface getCloudCoverageServer() {

        CloudCoverageInterface cloudCoverageInterface = null;

        while (cloudCoverageInterface == null) {
            try {
                cloudCoverageInterface = (CloudCoverageInterface) Naming.lookup("rmi://localhost/cs");
                System.out.println("Connected to Cloud Coverage Server");
            } catch (ConnectException ex) {
                System.out.println("Looking for server...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (RemoteException | NotBoundException | MalformedURLException e) {
                e.printStackTrace();
                break;
            }
        }
        return cloudCoverageInterface;
    }

}