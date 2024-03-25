package repo;

import domain.Entitate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractRepository<T extends Entitate> implements IRepository<T> {

    protected List<T> data = new ArrayList<>();

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<T>(data).iterator();
    }
}