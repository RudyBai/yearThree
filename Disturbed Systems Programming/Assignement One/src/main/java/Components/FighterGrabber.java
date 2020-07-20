package Components;

import java.util.ArrayList;

public class FighterGrabber implements Subject {

    private ArrayList<Observer> observers;
    private int numberOfFighters;

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer o : observers) {
            o.update(numberOfFighters);
        }
    }

    public void setNumberOfFighters(int numberOfFighters) {
        this.numberOfFighters = numberOfFighters;
        notifyObserver();
    }

}
