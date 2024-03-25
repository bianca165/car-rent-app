package com.example.lab5;

import domain.Inchiriere;
import domain.Masina;
import repo.IRepository;
import service.ServiceInchiriere;
import service.ServiceMasina;

import service.RapoarteService;
import repo.DuplicateObjectException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Console {
    private ServiceMasina serviceMasina;
    private ServiceInchiriere serviceInchiriere;
    private RapoarteService rapoarteService;
    private Scanner scanner;

    public Console(IRepository<Masina> repoMasina, IRepository<Inchiriere> repoInchiriere) {

        serviceMasina = new ServiceMasina(repoMasina);
        serviceInchiriere = new ServiceInchiriere(repoInchiriere, repoMasina);
        rapoarteService = new RapoarteService(serviceInchiriere, serviceMasina);

        scanner = new Scanner(System.in);


        try {
            serviceMasina.adaugaMasina(new Masina(1, "audi", "r8"));
            serviceMasina.adaugaMasina(new Masina(2, "bmw", "x6"));
            serviceMasina.adaugaMasina(new Masina(3, "renault", "clio"));
            serviceMasina.adaugaMasina(new Masina(4, "dacia", "logan"));
            serviceMasina.adaugaMasina(new Masina(5, "toyota", "prius"));

            serviceInchiriere.adaugaInchiriere(new Inchiriere(1, serviceMasina.getAllMasini().get(0), LocalDate.parse("2023-01-01"), LocalDate.parse("2023-01-10")));
            serviceInchiriere.adaugaInchiriere(new Inchiriere(2, serviceMasina.getAllMasini().get(1), LocalDate.parse("2023-02-01"), LocalDate.parse("2023-02-10")));
            serviceInchiriere.adaugaInchiriere(new Inchiriere(3, serviceMasina.getAllMasini().get(1), LocalDate.parse("2023-03-01"), LocalDate.parse("2023-03-10")));
            serviceInchiriere.adaugaInchiriere(new Inchiriere(4, serviceMasina.getAllMasini().get(2), LocalDate.parse("2023-04-01"), LocalDate.parse("2023-04-10")));
            serviceInchiriere.adaugaInchiriere(new Inchiriere(5, serviceMasina.getAllMasini().get(3), LocalDate.parse("2023-05-01"), LocalDate.parse("2023-05-10")));
            serviceInchiriere.adaugaInchiriere(new Inchiriere(6, serviceMasina.getAllMasini().get(4), LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-10")));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            System.out.println("Meniu:");
            System.out.println("1. Afiseaza toate masinile");
            System.out.println("2. Adauga o masina");
            System.out.println("3. Actualizeaza o masina");
            System.out.println("4. Sterge o masina");
            System.out.println("5. Afiseaza toate inchirierile");
            System.out.println("6. Adauga o inchiriere");
            System.out.println("7. Actualizeaza o inchiriere");
            System.out.println("8. Sterge o inchirirere");
            System.out.println("9. Afiseaza Inchiririle pentru o masina");
            System.out.println("10. Iesire");

            System.out.println("11. Afiseaza cele mai des inchiriate masini");
            System.out.println("12. Afiseaza numarul de inchirieri pe luni");
            System.out.println("13. Afiseaza masinile cele mai mult timp inchiriate");


            System.out.println("Alege :) : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    afiseazaMasini();
                    break;
                case 2:
                    adaugaMasina();
                    break;
                case 3:
                    actualizeazaMasina();
                    break;
                case 4:
                    stergeMasina();
                    break;
                case 5:
                    afiseazaInchirieri();
                    break;
                case 6:
                    adaugaInchiriere();
                    break;
                case 7:
                    actualizeazaInchiriere();
                    break;
                case 8:
                    stergeInchiriere();
                    break;
                case 9:
                    afiseazaInchirieriPentruMasina();
                    break;
                case 10:
                    System.out.println("stop");
                    scanner.close();
                    System.exit(0);

                case 11:
                    afiseazaCeleMaiDesInchiriateMasini();
                    break;
                case 12:
                    afiseazaNumarInchirieriPeLuni();
                    break;
                case 13:
                    afiseazaMasiniCeleMaiMultTimpInchiriate();
                    break;
                default:
                    System.out.println("Optiune invalida. Te rog alege o optiune valida.");
            }
        }
    }

    public void afiseazaCeleMaiDesInchiriateMasini() {
        rapoarteService.celeMaiDesInchiriateMasini();
    }

    public void afiseazaNumarInchirieriPeLuni() {
        rapoarteService.numarInchirieriPeLuni();
    }

    public void afiseazaMasiniCeleMaiMultTimpInchiriate() {
        rapoarteService.masiniCeleMaiMultTimpInchiriate();
    }

    public void afiseazaMasini() {
        System.out.println("Masini disponibile:");
        for (Masina masina : serviceMasina.getAllMasini()) {
            System.out.println(masina.getId() + ". " + masina.getMarca() + " " + masina.getModel());
        }
    }

    public void afiseazaInchirieri() {
        List<Inchiriere> inchiriri = serviceInchiriere.getAllInchirieri();
        System.out.println("Toate inchirirerile: ");

        for (Inchiriere inchiriere : inchiriri) {
            Masina masina = inchiriere.getMasina();
            String masinaInfo = (masina != null) ? "id masina: " + masina.getId()  : "Masina indisponibila";

            System.out.println(
                    inchiriere.getId() + ". " +
                            masinaInfo + ": " +
                            inchiriere.getDataInceput() + ": " +
                            inchiriere.getDataSfarsit()
            );
        }
    }

    public void adaugaMasina() {
        System.out.print("Introdu ID-ul masinii: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introdu marca masinii: ");
        String marca = scanner.nextLine();
        System.out.print("Introdu modelul masinii: ");
        String model = scanner.nextLine();

        Masina masina = new Masina(id, marca, model);

        serviceMasina.adaugaMasina(masina);
        System.out.println("Masina adaugata cu succes!");
    }

    public void adaugaInchiriere() {
        System.out.print("Introdu ID-ul inchirierii: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introdu indexul masinii pentru inchiriere: ");
        int indexMasina = scanner.nextInt();
        scanner.nextLine();

        Inchiriere inchiriere = null;

        if (indexMasina >= 0 && indexMasina <= serviceMasina.getAllMasini().size()) {
            Masina masina = serviceMasina.getAllMasini().get(indexMasina - 1);

            System.out.print("Introdu data de inceput (yyyy-mm-dd): ");
            String dataInceput = scanner.nextLine();

            System.out.print("Introdu data de sfarsit (yyyy-mm-dd): ");
            String dataSfarsit = scanner.nextLine();

            try {
                LocalDate dataInceputParsed = LocalDate.parse(dataInceput);
                LocalDate dataSfarsitParsed = LocalDate.parse(dataSfarsit);

                inchiriere = new Inchiriere(id, masina, dataInceputParsed, dataSfarsitParsed);

            } catch (DateTimeParseException e) {
                System.out.println("Eroare la parsarea datei: " + e.getMessage());

            }
        } else {
            System.out.println("Indexul masinii introdus nu este valid!");
        }

        if (inchiriere != null) {  // Check if inchiriere is not null before using it
            try {
                serviceInchiriere.adaugaInchiriere(inchiriere);
                System.out.println("Inchiriere adaugata cu succes!");
            } catch (Exception e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }
    }

    public void actualizeazaMasina() {
        System.out.print("Introdu ID-ul masinii pe care doresti sa o actualizezi: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introdu noua marca a masinii: ");
        String marca = scanner.nextLine();
        System.out.print("Introdu noul model al masinii: ");
        String model = scanner.nextLine();

        Masina newMasina = new Masina(id, marca, model);

        serviceMasina.updateMasina(id, newMasina);
        System.out.println("Masina actualizata cu succes!");
    }
    public void actualizeazaInchiriere() {
        System.out.print("Introdu ID-ul inchirierii pe care dorești să o actualizezi: ");
        int idInchiriere = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introdu indexul mașinii pentru inchiriere: ");
        int indexMasina = scanner.nextInt();
        scanner.nextLine();


        if (indexMasina >= 0 && indexMasina <= serviceMasina.getAllMasini().size()) {
            Masina masina = serviceMasina.getAllMasini().get(indexMasina-1);

            System.out.print("Introdu data de început (yyyy-MM-dd): ");
            String dataInceput = scanner.nextLine();

            System.out.print("Introdu data de sfârșit (yyyy-MM-dd): ");
            String dataSfarsit = scanner.nextLine();

            LocalDate dataInceputParsed = LocalDate.parse(dataInceput);
            LocalDate dataSfarsitParsed = LocalDate.parse(dataSfarsit);

            Inchiriere newInchiriere = new Inchiriere(idInchiriere, masina, dataInceputParsed, dataSfarsitParsed);

            try {
                serviceInchiriere.updateInchiriere(idInchiriere, newInchiriere);
                System.out.println("Inchiriere actualizată cu succes!");
            } catch (Exception e) {
                System.out.println("Eroare la actualizarea închirierii: " + e.getMessage());
            }
        } else {
            System.out.println("Indexul mașinii introdus nu este valid!");
        }
    }

    public void stergeMasina() {
        System.out.print("Introdu ID-ul masinii pe care doresti sa o stergi: ");
        int id = scanner.nextInt();

        serviceMasina.stergeMasina(id);
        System.out.println("Masina stearsa cu succes!");
    }

    public void stergeInchiriere() throws RuntimeException {
        System.out.print("Introdu ID-ul inchirierii pe care doresti sa o stergi: ");
        int id = scanner.nextInt();

        serviceInchiriere.stergeInchirirere(id);
        System.out.println("Inchiriere stearsa cu succes!");
    }

    public void afiseazaInchirieriPentruMasina() {
        System.out.print("Introdu ID-ul masinii pentru a afisa inchirierile: ");
        int idMasina = scanner.nextInt();

        List<Inchiriere> inchirieri = serviceInchiriere.getInchirieriMasina(idMasina);
        if (inchirieri.isEmpty()) {
            System.out.println("Nu exista inchirieri pentru masina cu ID-ul specificat.");
        } else {
            System.out.println("Inchirieri pentru masina cu ID-ul " + idMasina + ":");
            for (Inchiriere inchiriere : inchirieri) {
                System.out.println(inchiriere.getId() + ": Data Inceput - " + inchiriere.getDataInceput() + ", Data Sfarsit - " + inchiriere.getDataSfarsit());
            }
        }
    }
}
