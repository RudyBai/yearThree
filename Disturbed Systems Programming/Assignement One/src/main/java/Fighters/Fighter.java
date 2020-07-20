package Fighters;

import java.io.Serializable;

public class Fighter extends Being implements Serializable {

    private int power;

    Fighter(String name, int power) {
        super(name);
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    private void setPower(int power) {
        this.power = power;
    }

    public void grow(int increment) {
        this.setPower(this.getPower() + increment);
    }
}
