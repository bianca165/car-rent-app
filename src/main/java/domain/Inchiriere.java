package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Inchiriere extends Entitate {

    private Masina masina;

    private LocalDate dataInceput;
    private LocalDate dataSfarsit;


    public Inchiriere(int id, Masina masina, LocalDate dataInceput, LocalDate dataSfarsit){
        super(id);
        this.masina=masina;
        this.dataInceput=dataInceput;
        this.dataSfarsit=dataSfarsit;
    }


    @Override
    public String toString() {
        return "Inchiriere{" +
                "masina=" + (masina != null ? "marca: " + masina.getMarca() + ", model: " + masina.getModel() : "") +
                ", dataInceput=" + dataInceput +
                ", dataSfarsit=" + dataSfarsit +
                ", id =" + id +
                '}';
    }


    public Masina getMasina() {
        return masina;
    }

    public LocalDate getDataInceput() {
        return dataInceput;
    }

    public LocalDate getDataSfarsit() {
        return dataSfarsit;
    }

    public void setMasina(Masina masina) {
        this.masina = masina;
    }

    public void setDataInceput(LocalDate dataInceput) {
        this.dataInceput = dataInceput;
    }

    public void setDataSfarsit(LocalDate dataSfarsit) {
        this.dataSfarsit = dataSfarsit;
    }

    public boolean seSuprapuneCu(Inchiriere altaInchiriere) {
        return (dataInceput.isBefore(altaInchiriere.dataSfarsit) && dataSfarsit.isAfter(altaInchiriere.dataInceput));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inchiriere that = (Inchiriere) o;
        return id == that.id && Objects.equals(masina, that.masina) && Objects.equals(dataInceput, that.dataInceput) && Objects.equals(dataSfarsit, that.dataSfarsit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, masina, dataInceput, dataSfarsit);
    }
}
