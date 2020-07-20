package Threading;

import Components.Arena;
import Components.FighterFactory;

public class ThreadOne extends Thread {

    private Arena arena;

    // PASS ARENA INTO CONSTRUCTOR
    public ThreadOne(Arena arena) {
        this.arena = arena;
    }

    public void run() {

        FighterFactory fighterFactory = FighterFactory.getInstance();
        int i = 0;
        while (i < 20) {
            if (arena.getFighterArrayList().size() < 20) {
                System.out.println("Creating new Fighters");
                i++;
                arena.writeFighter(fighterFactory.makeFighter());
            } else {
                System.out.println("Arena at max capacity");
            }
        }

        System.out.println("The arena has created 20 Fighters and needs to restock.");

    }
}