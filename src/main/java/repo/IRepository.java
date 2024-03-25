package repo;
import java.util.List;

public interface IRepository<T> extends Iterable<T>{
    public T getById(int id);
    int poz(int id);
    void add(T entity) throws RepositoryException;
    void update(int id, T entity) ;
    void delete(int id);
    List<T> getAll();
}
