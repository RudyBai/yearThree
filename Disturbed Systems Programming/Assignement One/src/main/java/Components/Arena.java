package Components;

import Fighters.AirFighter;
import Fighters.Fighter;
import Fighters.FireFighter;
import Fighters.WaterFighter;

import java.io.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Random;

public class Arena {

    // DECLARE FILE LOCATION
    private final String fileLocation = "listOfFighters.txt";
    // DECLARE ARRAY LIST
    private ArrayList<Fighter> fighterArrayList;

    // CONSTRUCTOR
    public Arena() {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileLocation);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.fighterArrayList = (ArrayList<Fighter>) objectInputStream.readObject();
        } catch (FileNotFoundException ex) {
            // IF THE FILE IS NOT FOUND REPLACE WITH NEW ARRAY LIST
            System.out.println("File not found, creating empty fighter list.");
            this.fighterArrayList = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Getter
    public ArrayList<Fighter> getFighterArrayList() {
        return this.fighterArrayList;
    }


    public void numberOfFighters() {
        System.out.println("There are " + this.getFighterArrayList().size() + " fighters in the arena.");
    }

    // SERIALIZE FIGHTER TO ARRAY LIST FILE
    public synchronized void writeFighter(Fighter fighter) {
        this.getFighterArrayList().add(fighter);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new ArrayList<>(this.fighterArrayList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TAKE 2 RANDOM FIGHTERS FROM THE ARRAY LIST AND COMPARE THEIR TYPE AND POWER
    public synchronized void duel() {

        // CHECK AGAINST NUMBER OF FIGHTERS, NEED 2 FOR DUEL
        if (this.getFighterArrayList().size() < 2) {
            System.out.println("Not enough fighters.");
            return;
        }

        // INIT VARIABLES
        Random random = new Random();
        Fighter one, two;
        int res;

        // FIRST RANDOM FIGHTER
        int firstChoice = random.nextInt(getFighterArrayList().size());
        one = this.getFighterArrayList().get(firstChoice);

        // MAKE SURE SAME FIGHTER ISN'T CHOSEN TWICE
        int secondChoice = random.nextInt(this.getFighterArrayList().size());
        while (secondChoice == firstChoice) {
            secondChoice = random.nextInt(this.getFighterArrayList().size());
        }
        two = this.getFighterArrayList().get(secondChoice);

        // PRINT OUT SOME DETAILS
        System.out.println(String.format("%20s vs %20s", "Name : " + one.getName(), two.getName()));
        System.out.println(String.format("%20s    %20s", one.getClass().getName(), two.getClass().getName()));
        System.out.println(String.format("%20s    %20d", String.format("Power : %d", one.getPower()), two.getPower()));

        // AIR COUNTERS FIRE
        // FIRE COUNTERS WATER
        // WATER COUNTERS AIR

        if (one instanceof AirFighter && two instanceof AirFighter ||
                one instanceof FireFighter && two instanceof FireFighter ||
                one instanceof WaterFighter && two instanceof WaterFighter) {

            res = Integer.compare(one.getPower(), two.getPower());

        } else if (one instanceof AirFighter && two instanceof FireFighter ||
                one instanceof FireFighter && two instanceof WaterFighter ||
                one instanceof WaterFighter && two instanceof AirFighter) {

            res = Integer.compare(one.getPower() * 2, two.getPower());

        } else if (one instanceof AirFighter && two instanceof WaterFighter ||
                one instanceof WaterFighter && two instanceof FireFighter ||
                one instanceof FireFighter && two instanceof AirFighter) {

            res = Integer.compare(one.getPower(), two.getPower() * 2);

        } else {
            throw new UnknownError("Unprepared comparison case");
        }


        if (res < 0) {
            // PRINT DUEL RESULT
            System.out.println(two.getName() + " has won!");
            // REMOVE LOSER FROM LIST
            this.kill(one);
            // WINNER'S POWER INCREASES BY ONE TWELFTH OF LOSERS POWER
            two.grow(one.getPower() / 12);
        } else if (res > 0) {
            System.out.println(one.getName() + " has won!");
            this.kill(two);
            one.grow(two.getPower() / 12);
        } else {
            System.out.println("Draw!");
        }

    }


    private void kill(Fighter fighter) {

        // REMOVE FIGHTER FROM LOCAL MEMORY
        this.getFighterArrayList().remove(fighter);

        // REMOVE PLAYER FROM FILE
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new ArrayList<>(this.getFighterArrayList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void subscribe(Observer o) {

    }
}
