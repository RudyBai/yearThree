package Components;

public class FighterObserver implements Observer {

    private int numberOfFighters;
    private static int observerIDTracker = 0;
    private int observerID;

    private Subject fighterGrabber;

    public FighterObserver(Subject fighterGrabber) {
        this.fighterGrabber = fighterGrabber;
        this.observerID = ++observerIDTracker;
        fighterGrabber.register(this);
    }

    @Override
    public void update(int numberOfFighters) {
        this.numberOfFighters = numberOfFighters;
        System.out.println("Observer ID : " + this.observerID);
        System.out.println("Number of fighters : " + this.numberOfFighters);
    }
}
