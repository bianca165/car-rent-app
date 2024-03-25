package domain;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InchirirereConverter implements EntitateConverter<Inchiriere> {
    @Override
    public String toString(Inchiriere o){
        return o.getId() + "," + o.getMasina().getMarca() + "," + o.getMasina().getModel() + ","
                + o.getMasina().getId() + "," + o.getDataInceput() + "," + o.getDataSfarsit();
    }

    @Override
    public Inchiriere fromString(String line) {
        String[] parts = line.split(",");

        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid input format. Expected 6 parts, but found " + parts.length + " parts. Line: " + line);
        }

        try {
            int id = Integer.parseInt(parts[0]);
            String marca = parts[1];
            String model = parts[2];
            int masinaId = Integer.parseInt(parts[3]);
            LocalDate dataInceput = LocalDate.parse(parts[4]);
            LocalDate dataSfarsit = LocalDate.parse(parts[5]);

            Masina masina = new Masina(masinaId, marca, model);

            return new Inchiriere(id, masina, dataInceput, dataSfarsit);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing input. Line: " + line, e);
        }
    }
}
