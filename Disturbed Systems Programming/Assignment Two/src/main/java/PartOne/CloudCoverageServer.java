package PartOne;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CloudCoverageServer extends UnicastRemoteObject implements CloudCoverageInterface, Serializable {

    private CloudCoverageWorker cloudCoverageWorker = new CloudCoverageWorker();

    private CloudCoverageServer() throws RemoteException {
    }

    public static void main(String[] args) {
        try {
            CloudCoverageServer cloudCoverageServer = new CloudCoverageServer();
            System.out.println("Running...");

            Naming.rebind("//localhost/cs", cloudCoverageServer);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateCloudCoverage() {
        cloudCoverageWorker = new CloudCoverageWorker();
        cloudCoverageWorker.start();
    }

    @Override
    public boolean isDone() {
        return cloudCoverageWorker.isDone();
    }

    @Override
    public float getCloudCoverage() {
        return cloudCoverageWorker.getCloudCoverage();
    }
}