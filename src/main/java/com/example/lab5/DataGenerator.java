package com.example.lab5;

import domain.Inchiriere;
import domain.Masina;
import repo.DuplicateObjectException;
import repo.IRepository;
import repo.RepositoryException;

import java.time.LocalDate;
import java.util.Random;

public class DataGenerator {

    private static final String[] MARCI = {"Audi", "BMW", "Mercedes", "Porsche", "Ford"};
    private static final String[] MODELE = {"Sedan", "SUV", "Coupe", "Hatchback"};

    public static void generateAndSaveData(IRepository<Masina> masinaRepository, IRepository<Inchiriere> inchiriereRepository) throws RepositoryException {
        Random random = new Random();

        for (int i = 1; i <= 100; i++) {
            String marca = MARCI[random.nextInt(MARCI.length)];
            String model = MODELE[random.nextInt(MODELE.length)];
            Masina masina = new Masina(i, marca, model);
            try {
                masinaRepository.add(masina);
            } catch (RepositoryException e) {
                if (e instanceof DuplicateObjectException) {
                    System.out.println("Eroare: Masina cu acest ID exista deja in repository!");
                } else {
                    throw new RuntimeException(e);
                }
            }

            int numarInchirieri = random.nextInt(5) + 1; // minim 1 închiriere, maxim 5 închirieri
            for (int j = 0; j < numarInchirieri; j++) {
                LocalDate dataInceput = LocalDate.now().plusDays(random.nextInt(30));
                LocalDate dataSfarsit = dataInceput.plusDays(random.nextInt(30));

                // Utilizează un nou identificator unic pentru fiecare închiriere
                int idInchiriere = (i - 1) * 5 + j + 1;
                Inchiriere inchiriere = new Inchiriere(idInchiriere, masina, dataInceput, dataSfarsit);
                try {
                    inchiriereRepository.add(inchiriere);
                } catch (RepositoryException e) {
                    if (e instanceof DuplicateObjectException) {
                        System.out.println("Eroare: Inchiriere cu acest ID exista deja in repository!");
                    } else {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
