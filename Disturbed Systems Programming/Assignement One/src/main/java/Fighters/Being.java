package Fighters;

import java.io.Serializable;

public abstract class Being implements Serializable {
    private String name;

    Being(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
