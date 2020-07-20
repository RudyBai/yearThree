package PartOne;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CloudCoverageInterface extends Remote {

    void generateCloudCoverage() throws RemoteException;

    boolean isDone() throws RemoteException;

    float getCloudCoverage() throws RemoteException;
}
