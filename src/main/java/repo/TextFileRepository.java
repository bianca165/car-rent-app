package repo;

import domain.Entitate;
import domain.EntitateConverter;

import java.io.*;


public class TextFileRepository<T extends Entitate> extends MemoryRepository<T> {

    private String fileName;
    private EntitateConverter<T> converter;

    public TextFileRepository(String fileName, EntitateConverter<T> converter) throws IOException {
        this.fileName = fileName;
        this.converter = converter;

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        loadFile();
    }

    @Override
    public void add(T o) throws RepositoryException {
        super.add(o);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RepositoryException("Error saving object", e);
        }
    }

    @Override
    public void update(int id, T o) {
        for (int i = 0; i < data.size(); i++) {
            T entity = data.get(i);
            if (entity.getId() == id) {
                data.set(i, o);
                try {
                    saveFile();
                } catch (IOException e) {
                    throw new RuntimeException("Error saving object", e);
                }
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        super.delete(id);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveFile() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (T object : data) {
                bw.write(converter.toString(object));
                bw.write("\r\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(converter.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}