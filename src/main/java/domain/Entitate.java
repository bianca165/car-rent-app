package domain;

import java.io.Serializable;

public abstract class Entitate implements Serializable {
    protected int id;

    public Entitate(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
