package PartOne;

import java.rmi.RemoteException;

public class CloudCoverageChecker extends Thread {

    private Client client;
    private CloudCoverageInterface server;

    CloudCoverageChecker(Client client, CloudCoverageInterface server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Thread.sleep(100);
                if (server.isDone()) {
                    client.setCloudCoverage(server.getCloudCoverage());
                }
            } catch (RemoteException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
