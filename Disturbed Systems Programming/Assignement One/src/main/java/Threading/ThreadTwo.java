package Threading;

import Components.Arena;

public class ThreadTwo extends Thread {

    private Arena arena;

    public ThreadTwo(Arena arena) {
        this.arena = arena;
    }

    public void run() {
        int i = 0;
        while (i < 20) {


            if (arena.getFighterArrayList().size() > 1) {
                System.out.println("Starting Duel");
                arena.duel();
                i++;
            } else {
                System.out.println("There isn't enough fighters to duel.");
            }
        }

        System.out.println("The arena held 20 duels today and needs to be cleaned up.");

    }
}
