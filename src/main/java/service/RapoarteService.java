package service;

import domain.Inchiriere;
import domain.Masina;
import repo.IRepository;
import repo.MemoryRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RapoarteService {
    private final ServiceInchiriere serviceInchiriere;
    private final ServiceMasina serviceMasina;

    public RapoarteService(ServiceInchiriere serviceInchiriere, ServiceMasina serviceMasina) {
        this.serviceInchiriere = serviceInchiriere;
        this.serviceMasina = serviceMasina;
    }

    public void celeMaiDesInchiriateMasini() {
        Map<Masina, Long> masiniCuNrInchirieri = serviceInchiriere.getAllInchirieri().stream()
                .collect(Collectors.groupingBy(Inchiriere::getMasina, Collectors.counting()));

        List<Masina> masiniSortate = masiniCuNrInchirieri.entrySet().stream()
                .sorted(Map.Entry.<Masina, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("Cele mai des inchiriate masini:");
        for (Masina masina : masiniSortate) {
            System.out.println("Masina: " + masina + ", Numar Inchirieri: " + masiniCuNrInchirieri.get(masina));
        }
    }

    public void numarInchirieriPeLuni() {
        Map<Month, Long> inchirieriPeLuni = serviceInchiriere.getAllInchirieri().stream()
                .collect(Collectors.groupingBy(inchiriere -> inchiriere.getDataInceput().getMonth(), Collectors.counting()));

        System.out.println("Numarul de inchirieri efectuate in fiecare luna a anului:");
        inchirieriPeLuni.entrySet().stream()
                .sorted(Map.Entry.<Month, Long>comparingByValue().reversed())
                .forEach(entry -> System.out.println("Luna: " + entry.getKey() + ", Numar Inchirieri: " + entry.getValue()));
    }

    public void masiniCeleMaiMultTimpInchiriate() {
        Map<Masina, Long> timpInchirierePeMasini = serviceInchiriere.getAllInchirieri().stream()
                .collect(Collectors.groupingBy(Inchiriere::getMasina, Collectors.summarizingLong(inchiriere -> {
                    LocalDate dataInceput = inchiriere.getDataInceput();
                    LocalDate dataSfarsit = inchiriere.getDataSfarsit();

                    // Calculați diferența în zile
                    long durataInZile = ChronoUnit.DAYS.between(dataInceput, dataSfarsit);

                    return durataInZile;
                })))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getSum()));

        timpInchirierePeMasini.entrySet().stream()
                .sorted(Map.Entry.<Masina, Long>comparingByValue().reversed())
                .forEach(entry -> System.out.println("Masina: " + entry.getKey() + ", Timp Inchiriere (zile): " + entry.getValue()));
    }

}
