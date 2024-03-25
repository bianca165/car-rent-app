package service;

import domain.Masina;
import repo.DuplicateObjectException;
import repo.IRepository;
import repo.RepositoryException;

import java.util.List;

public class ServiceMasina {
    private IRepository<Masina> repoMasina;
    public ServiceMasina(IRepository<Masina> repoMasina){
        this.repoMasina = repoMasina;
    }
    public List<Masina> getAllMasini(){
        return repoMasina.getAll();
    }

    public void adaugaMasina(Masina masina) throws RuntimeException {
        try {
            repoMasina.add(masina);
        } catch (RepositoryException e) {
            if (e instanceof DuplicateObjectException) {
                System.out.println("Eroare: Masina cu acest ID exista deja in repository!");
            } else {
                throw new RuntimeException(e);
            }
        }
    }
    public void updateMasina(int id, Masina newMasina){
        repoMasina.update(id, newMasina);
    }

    public void stergeMasina(int id){
        repoMasina.delete(id);
    }

    public Masina getMasinaById(int id) {
        return repoMasina.getById(id);
    }
}
