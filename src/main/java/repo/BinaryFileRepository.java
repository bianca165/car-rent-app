package repo;

import domain.Entitate;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class BinaryFileRepository<T extends Entitate> extends MemoryRepository<T> {

    private String fileName;

    public BinaryFileRepository(String fileName) {
        this.fileName = fileName;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadDataFromFile();
    }

    @Override
    public void add(T entity) throws RepositoryException {
        super.add(entity);
        saveDataToFile();
    }

    @Override
    public void update(int id, T newEntity){
        super.update(id, newEntity);
        saveDataToFile();
    }

    @Override
    public void delete(int id){
        super.delete(id);
        saveDataToFile();
    }

    private void loadDataFromFile() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {

            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                List<?> deserializedList = (List<?>) obj;

                if (!deserializedList.isEmpty() && deserializedList.get(0) instanceof Entitate) {
                    this.data = (List<T>) new ArrayList<>(deserializedList);                } else {
                    System.err.println("Unexpected object type in the file.");
                }
            }
        } catch (EOFException e) {

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }
    private void saveDataToFile() {
        try {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
                outputStream.writeObject(new ArrayList<>(data.stream().toList()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
