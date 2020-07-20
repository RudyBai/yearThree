package Components;

import Fighters.AirFighter;
import Fighters.Fighter;
import Fighters.FireFighter;
import Fighters.WaterFighter;

import java.util.Random;

public class FighterFactory {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static FighterFactory fighterFactory;

    private FighterFactory() {
    }

    // LAZY INSTANTIATION
    public static FighterFactory getInstance() {
        if (fighterFactory == null) {
            synchronized (FighterFactory.class) {
                if (fighterFactory == null) {
                    fighterFactory = new FighterFactory();
                }
            }
        }

        return fighterFactory;

    }

    // GENERATE RANDOM FIGHTER
    public Fighter makeFighter() {
        Random random = new Random();

        // TAKE RANDOM INDEX FROM ALPHABET STRING TO GENERATE RANDOM LETTER
        String name = String.valueOf(ALPHABET.charAt(random.nextInt(ALPHABET.length()))) + random.nextInt(1000);
        // GENERATE RANDOM TYPE
        int type = random.nextInt(3);
        if (type == 0) {
            return new FireFighter(name, random.nextInt(100) + 1);
        } else if (type == 1) {
            return new WaterFighter(name, random.nextInt(100) + 1);
        } else if (type == 2) {
            return new AirFighter(name, random.nextInt(100) + 1);
        } else {
            return null;
        }
    }
}
