package com.example.lab5;

import com.example.lab5.Console;
import domain.*;
import repo.*;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IOException, RepositoryException {

        String repositoryType = Settings.getRepoType();

        IRepository<Masina> masinaRepo = new MemoryRepository<>();
        IRepository<Inchiriere> inchiriereRepo = new MemoryRepository<>();
        EntitateConverter<Masina> cm = new MasinaConverter();
        EntitateConverter<Inchiriere> ic = new InchirirereConverter();

        System.out.println("Repo Type: " + repositoryType);

        if ("memory".equals(repositoryType)) {
            masinaRepo  = new MemoryRepository<>();
            inchiriereRepo = new MemoryRepository<>();
            try {

                masinaRepo.add(new Masina(12, "toyota", "chr"));
                masinaRepo.add(new Masina(13, "bmw", "m4"));
                masinaRepo.add(new Masina(14, "renault", "megane"));

                inchiriereRepo.add(new Inchiriere(10, masinaRepo.getAll().get(0), LocalDate.parse("2010-01-01"), LocalDate.parse("2010-01-05")));
                inchiriereRepo.add(new Inchiriere(11, masinaRepo.getAll().get(1), LocalDate.parse("2011-02-01"), LocalDate.parse("2011-02-10")));
                inchiriereRepo.add(new Inchiriere(12, masinaRepo.getAll().get(2), LocalDate.parse("2012-03-01"), LocalDate.parse("2012-03-15")));

            } catch (RepositoryException e) {

                throw new RuntimeException(e);
            }
        }

        else if ("binary".equals(repositoryType)) {
            masinaRepo = new BinaryFileRepository<>("masini.bin");
            inchiriereRepo = new BinaryFileRepository<>("inchirieri.bin");
        }

        else if ("text".equals(repositoryType)) {
            masinaRepo = new TextFileRepository<>("masini.txt", cm);
            inchiriereRepo = new TextFileRepository<>("inchirieri.txt", ic);
        }

        else if ("db".equals(repositoryType)) {
            masinaRepo = new MasinaDBRepository();
            inchiriereRepo = new InchiriereDBRepository();

            //com.example.lab5.DataGenerator.generateAndSaveData(masinaRepo, inchiriereRepo);

        }

        else {
            throw new IllegalArgumentException("Invalid repository type: " + repositoryType);
        }


        for (Masina masina : masinaRepo) {
            System.out.println(masina);
        }

        for (Inchiriere inchiriere : inchiriereRepo) {
            System.out.println(inchiriere);
        }


        Console app = new Console(masinaRepo, inchiriereRepo);
        app.run();

        if (masinaRepo instanceof MasinaDBRepository) {
            ((MasinaDBRepository) masinaRepo).closeConnection();
        }
        if (inchiriereRepo instanceof InchiriereDBRepository) {
            ((InchiriereDBRepository) inchiriereRepo).closeConnection();
        }
    }


}