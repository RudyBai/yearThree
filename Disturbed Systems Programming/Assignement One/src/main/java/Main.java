import Components.Arena;
import Fighters.Fighter;
import Components.FighterFactory;
import Threading.ThreadOne;
import Threading.ThreadTwo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int option = -1;
        Scanner scanner = new Scanner(System.in);

        Arena arena = new Arena();
        FighterFactory fighterFactory = FighterFactory.getInstance();

        while (option != 0) {
            System.out.print( "1. Generate new Fighter.\n" +
                                "2. Get number of Fighters.\n" +
                                "3. Random fighters duel.\n" +
                                "4. Get fighter details.\n" +
                                "5. Threading test.\n" +
                                "0. Quit.\n" +
                                ">> ");
            try {
                option = scanner.nextInt();
            } catch (Exception e) {
                System.out.print("Oops. that didn't work. Try again.\n>> ");
                scanner.next();
                option = scanner.nextInt();
            }

            switch (option) {
                case 1:
                    Fighter fighter = fighterFactory.makeFighter();
                    arena.writeFighter(fighter);
                    break;
                case 2:
                    arena.numberOfFighters();
                    break;
                case 3:
                    arena.duel();
                    break;
                case 4:
                    for (Fighter f : arena.getFighterArrayList()) {
                        System.out.println(String.format("%4s\t%s\t%s", f.getName(), f.getPower(), f.getClass()));
                    }
                    break;
                case 5:
                    ThreadOne threadOne = new ThreadOne(arena);
                    ThreadTwo threadTwo = new ThreadTwo(arena);
                    threadOne.start();
                    threadTwo.start();
                    break;
            }

        }
    }
}
