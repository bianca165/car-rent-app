package domain;

import domain.Entitate;

import java.io.Serializable;
import java.util.Objects;

public class Masina extends Entitate {

    private String marca;
    private String model;

    public Masina(int id, String marca, String model) {
        super(id);
        this.marca=marca;
        this.model=model;
    }

    public String toString() {
        return "Masina(" + "marca: "+marca+", model: "+ model +", id: "+id +")";
    }

    public String getMarca() {
        return marca;
    }

    public String getModel() {
        return model;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Masina(int id){
        super(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Masina masina = (Masina) o;
        return id == masina.id && Objects.equals(marca, masina.marca) && Objects.equals(model, masina.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marca, model);
    }
}
