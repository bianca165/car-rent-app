package com.example.lab5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import domain.Inchiriere;
import domain.Masina;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import repo.IRepository;
import repo.MemoryRepository;
import service.ServiceInchiriere;
import service.ServiceMasina;

import java.io.IOException;
import java.time.LocalDate;

public class HelloApplication extends Application {
    private ServiceInchiriere serviceInchiriere;
    private ServiceMasina serviceMasina;

    @Override
    public void start(Stage stage) throws IOException {


        IRepository<Inchiriere> repoInchiriere = new MemoryRepository<>();
        IRepository<Masina> repoMasina = new MemoryRepository<>();
        this.serviceInchiriere = new ServiceInchiriere(repoInchiriere, repoMasina);
        this.serviceMasina = new ServiceMasina(repoMasina);

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.4f, 0.6f);

        VBox masinaBox = createMasinaBox();
        VBox inchiriereBox = createInchiriereBox();

        splitPane.getItems().addAll(masinaBox, inchiriereBox);

        Scene scene = new Scene(splitPane, 800, 400);
        stage.setTitle("Car Rental Application");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createMasinaBox() {

        VBox masinaBox = new VBox();
        masinaBox.setPadding(new Insets(10));

        ListView<Masina> masiniListView = new ListView<>();
        ObservableList<Masina> masini = FXCollections.observableArrayList(serviceMasina.getAllMasini());
        masiniListView.setItems(masini);
        VBox.setVgrow(masiniListView, Priority.ALWAYS);
        masinaBox.getChildren().add(masiniListView);

        GridPane masiniGridPane = new GridPane();
        masiniGridPane.setPadding(new Insets(10, 0, 0, 0));

        Label masinaIdLabel = new Label("Id Masina");
        masinaIdLabel.setPadding(new Insets(0, 10, 0, 0));
        TextField masinaIdTextField = new TextField();

        Label masinaMarcaLabel = new Label("Marca Masina");
        masinaMarcaLabel.setPadding(new Insets(0, 10, 0, 0));
        TextField masinaMarcaTextField = new TextField();

        Label masinaModelLabel = new Label("Model Masina");
        masinaModelLabel.setPadding(new Insets(0, 10, 0, 0));
        TextField masinaModelTextField = new TextField();

        masiniGridPane.add(masinaIdLabel, 0, 0);
        masiniGridPane.add(masinaIdTextField, 1, 0);
        masiniGridPane.add(masinaMarcaLabel, 0, 1);
        masiniGridPane.add(masinaMarcaTextField, 1, 1);
        masiniGridPane.add(masinaModelLabel, 0, 2);
        masiniGridPane.add(masinaModelTextField, 1, 2);

        masinaBox.getChildren().add(masiniGridPane);

        HBox masiniActionsHorizontalBox = new HBox();
        masiniActionsHorizontalBox.setPadding(new Insets(10, 0, 0, 0));
        Button addMasinaButton = new Button("Add Masina");
        Button updateMasinaButton = new Button("Update Masina");
        Button deleteMasinaButton = new Button("Delete Masina");
        masiniActionsHorizontalBox.getChildren().addAll(addMasinaButton, updateMasinaButton, deleteMasinaButton);
        masinaBox.getChildren().add(masiniActionsHorizontalBox);


        addMasinaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(masinaIdTextField.getText());
                    String marca = masinaMarcaTextField.getText();
                    String model = masinaModelTextField.getText();
                    serviceMasina.adaugaMasina(new Masina(id, marca, model));
                    masini.setAll(serviceMasina.getAllMasini());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });


        updateMasinaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(masinaIdTextField.getText());
                    String marca = masinaMarcaTextField.getText();
                    String model = masinaModelTextField.getText();
                    serviceMasina.updateMasina(id, new Masina(id, marca, model));
                    masini.setAll(serviceMasina.getAllMasini());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });


        deleteMasinaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(masinaIdTextField.getText());
                    serviceMasina.stergeMasina(id);
                    masini.setAll(serviceMasina.getAllMasini());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });

        return masinaBox;
    }

    private VBox createInchiriereBox() {
        VBox inchiriereBox = new VBox();
        inchiriereBox.setPadding(new Insets(10));

        ListView<Inchiriere> inchirieriListView = new ListView<>();
        ObservableList<Inchiriere> inchirieri = FXCollections.observableArrayList(serviceInchiriere.getAllInchirieri());
        inchirieriListView.setItems(inchirieri);
        VBox.setVgrow(inchirieriListView, Priority.ALWAYS);
        inchiriereBox.getChildren().add(inchirieriListView);

        GridPane inchirieriGridPane = new GridPane();
        inchirieriGridPane.setPadding(new Insets(10, 0, 0, 0));

        Label inchiriereIdLabel = new Label("Id Inchiriere");
        inchiriereIdLabel.setPadding(new Insets(0, 10, 0, 0));
        TextField inchiriereIdTextField = new TextField();

        Label masinaIdLabel = new Label("Id Masina");
        masinaIdLabel.setPadding(new Insets(0, 10, 0, 0));
        TextField masinaIdTextField = new TextField();

        Label dataInceputLabel = new Label("Data Inceput");
        dataInceputLabel.setPadding(new Insets(0, 10, 0, 0));
        TextField dataInceputTextField = new TextField();

        Label dataSfarsitLabel = new Label("Data Sfarsit");
        dataSfarsitLabel.setPadding(new Insets(0, 10, 0, 0));
        TextField dataSfarsitTextField = new TextField();

        inchirieriGridPane.add(inchiriereIdLabel, 0, 0);
        inchirieriGridPane.add(inchiriereIdTextField, 1, 0);
        inchirieriGridPane.add(masinaIdLabel, 0, 1);
        inchirieriGridPane.add(masinaIdTextField, 1, 1);
        inchirieriGridPane.add(dataInceputLabel, 0, 2);
        inchirieriGridPane.add(dataInceputTextField, 1, 2);
        inchirieriGridPane.add(dataSfarsitLabel, 0, 3);
        inchirieriGridPane.add(dataSfarsitTextField, 1, 3);

        inchiriereBox.getChildren().add(inchirieriGridPane);


        HBox inchirieriActionsHorizontalBox = new HBox();
        inchirieriActionsHorizontalBox.setPadding(new Insets(10, 0, 0, 0));
        Button addInchiriereButton = new Button("Add Inchiriere");
        Button updateInchiriereButton = new Button("Update Inchiriere");
        Button deleteInchiriereButton = new Button("Delete Inchiriere");
        inchirieriActionsHorizontalBox.getChildren().addAll(addInchiriereButton, updateInchiriereButton, deleteInchiriereButton);
        inchiriereBox.getChildren().add(inchirieriActionsHorizontalBox);


        addInchiriereButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int idInchiriere = Integer.parseInt(inchiriereIdTextField.getText());
                    int idMasina = Integer.parseInt(masinaIdTextField.getText());

                    Masina masina = serviceMasina.getMasinaById(idMasina);

                    if (masina != null) {
                        LocalDate dataInceput = LocalDate.parse(dataInceputTextField.getText());
                        LocalDate dataSfarsit = LocalDate.parse(dataSfarsitTextField.getText());

                        Inchiriere inchiriere = new Inchiriere(idInchiriere, masina, dataInceput, dataSfarsit);
                        serviceInchiriere.adaugaInchiriere(inchiriere);
                        inchirieri.setAll(serviceInchiriere.getAllInchirieri());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Masina with ID " + idMasina + " not found.");
                        alert.show();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });


        updateInchiriereButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int idInchiriere = Integer.parseInt(inchiriereIdTextField.getText());
                    int idMasina = Integer.parseInt(masinaIdTextField.getText());
                    LocalDate dataInceput = LocalDate.parse(dataInceputTextField.getText());
                    LocalDate dataSfarsit = LocalDate.parse(dataSfarsitTextField.getText());

                    Masina masina = serviceMasina.getMasinaById(idMasina);

                    if (masina != null) {
                        Inchiriere updatedInchiriere = new Inchiriere(idInchiriere, masina, dataInceput, dataSfarsit);

                        serviceInchiriere.updateInchiriere(idInchiriere, updatedInchiriere);
                        inchirieri.setAll(serviceInchiriere.getAllInchirieri());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Masina with ID " + idMasina + " not found.");
                        alert.show();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });


        deleteInchiriereButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int idInchiriere = Integer.parseInt(inchiriereIdTextField.getText());

                    serviceInchiriere.stergeInchirirere(idInchiriere);
                    inchirieri.setAll(serviceInchiriere.getAllInchirieri());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });

        return inchiriereBox;
    }


    public static void main(String[] args) {
        launch();
    }
}