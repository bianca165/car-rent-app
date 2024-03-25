package service;

import domain.Inchiriere;
import repo.DuplicateObjectException;
import domain.Masina;
import repo.IRepository;
import repo.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class ServiceInchiriere {
    private IRepository<Inchiriere> repoInchiriere;
    private IRepository<Masina> repoMasina;

    public ServiceInchiriere(IRepository<Inchiriere> repoInchiriere, IRepository<Masina> repoMasina){
        this.repoInchiriere= repoInchiriere;
        this.repoMasina=repoMasina;
    }

    public List<Inchiriere> getAllInchirieri(){
        return repoInchiriere.getAll();
    }

    public void adaugaInchiriere(Inchiriere inchiriere) throws RuntimeException {
        try {
            if (!existaSuprapuneri(inchiriere)) {
                repoInchiriere.add(inchiriere);
            } else {
                throw new DuplicateObjectException("Suprapunere de timp pentru închiriere sau mașina indisponibilă!");
            }
        } catch (RepositoryException e) {
            if (e instanceof DuplicateObjectException) {
                System.out.println("Eroare: Inchiriere cu acest ID exista deja in repository!");
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean existaSuprapuneri(Inchiriere inchiriere) {
        for (Inchiriere inc : repoInchiriere.getAll()) {
            if (inc.getMasina().equals(inchiriere.getMasina()) && inc.seSuprapuneCu(inchiriere)) {
                return true;
            }
        }
        return false;
    }

    public void updateInchiriere(int id, Inchiriere newInchiriere){
        repoInchiriere.update(id, newInchiriere);
    }
    public void stergeInchirirere(int id){
        repoInchiriere.delete(id);
    }

    public List<Inchiriere> getInchirieriMasina(int id) {
        Masina masinaExistenta = repoMasina.getById(id);
        if (masinaExistenta == null) {
            return new ArrayList<>();
        }

        List<Inchiriere> inchirieriMasina = new ArrayList<>();
        for (Inchiriere existenta : repoInchiriere.getAll()) {
            if (existenta.getMasina().equals(masinaExistenta)) {
                inchirieriMasina.add(existenta);
            }
        }

        return inchirieriMasina;
    }

}