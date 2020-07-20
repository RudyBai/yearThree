package PartOne;

import java.io.Serializable;
import java.util.Random;

public class CloudCoverageWorker extends Thread implements Serializable {

    private float cloudCoverage;
    private boolean done = false;
    private Random random;

    CloudCoverageWorker() {
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            done = false;
            cloudCoverage = random.nextFloat() * 60;
            Thread.sleep(3000);
            done = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    float getCloudCoverage() {
        done = false;
        return cloudCoverage;
    }

    boolean isDone() {
        return done;
    }
}
