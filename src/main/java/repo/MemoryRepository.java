package repo;

import domain.Entitate;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository<T extends Entitate> extends AbstractRepository<T>{

    @Override
    public void add(T o) throws RepositoryException {
        if (o == null) {
            throw new IllegalArgumentException();
        }

        if (getById(o.getId()) != null) {
            throw new DuplicateObjectException("Cannot duplicate repository objects!");
        }

        this.data.add(o);
    }
    @Override
    public T getById(int id) {
        for (T o : data) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    public int poz(int id) {
        for (int i = 0; i < data.size(); i++)
            if (data.get(i).getId() == id)
                return i;
        return -1;
    }

    @Override
    public void update(int id, T newEntity) {
        for (int i = 0; i < data.size(); i++) {
            T entity = data.get(i);
            if (entity.getId() == id) {
                data.set(i, newEntity);
                return;
            }
        }
    }

    @Override
    public void delete(int id){
        int entityToDelete = poz(id);

        try{
            if (entityToDelete == -1) {
                throw new RepositoryException("Nu putem da delete, pentru ca nu este inca adaugata");
            } else {
                data.remove(entityToDelete);
            }
        }catch (RepositoryException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(data);
    }
}
