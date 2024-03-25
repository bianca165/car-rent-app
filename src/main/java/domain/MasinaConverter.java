package domain;

public class MasinaConverter implements EntitateConverter<Masina>{
    @Override
    public String toString(Masina o) {
        return o.getId()+" "+o.getMarca()+" "+o.getModel();
    }

    @Override
    public Masina fromString(String line) {
        String[] parts = line.split(" ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid input format");
        }

        int id = Integer.parseInt(parts[0]);
        String marca = parts[1];
        String model = parts[2];

        return new Masina(id, marca, model);
    }
}
