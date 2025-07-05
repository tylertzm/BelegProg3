package gui;

import domainlogic.Automat;
import domainlogic.AutomatService;
import domainlogic.EventSystem;
import domainlogic.Kuchen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kuchen.Allergen;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AutomatGUI extends Application {

    private Automat automat;
    private AutomatService automatService;
    private ObservableList<Kuchen> kuchenList = FXCollections.observableArrayList();
    private TableView<Kuchen> kuchenTableView;
    private TextField nameField, sorteField, herstellerField;
    private DatePicker inspektionsDatumPicker;
    private CheckBox glutenfreiCheckbox, lactosefreiCheckbox; // Example Allergens

    @Override
    public void start(Stage primaryStage) {
        // Initialize Automat and EventSystem (without network/observer)
        automat = new Automat(10, new EventSystem()); // Example capacity
        automatService = new AutomatService(automat);
        kuchenList = automatService.getKuchenList();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Center: Kuchen TableView
        kuchenTableView = createKuchenTableView();
        root.setCenter(kuchenTableView);

        // Right: Input Fields and Buttons
        root.setRight(createInputPanel());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Automat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Kuchen> createKuchenTableView() {
        TableView<Kuchen> tableView = new TableView<>();
        tableView.setItems(kuchenList);

        TableColumn<Kuchen, Integer> fachnummerCol = new TableColumn<>("Fachnummer");
        fachnummerCol.setCellValueFactory(new PropertyValueFactory<>("fach"));

        TableColumn<Kuchen, String> herstellerCol = new TableColumn<>("Hersteller");
        herstellerCol.setCellValueFactory(new PropertyValueFactory<>("hersteller"));

        TableColumn<Kuchen, String> sorteCol = new TableColumn<>("Sorte");
        sorteCol.setCellValueFactory(new PropertyValueFactory<>("sorte"));

        TableColumn<Kuchen, String> allergeneCol = new TableColumn<>("allergeneString");
        allergeneCol.setCellValueFactory(new PropertyValueFactory<>("allergeneString"));

        TableColumn<Kuchen, LocalDate> inspektionsdatumCol = new TableColumn<>("Inspektionsdatum");
        inspektionsdatumCol.setCellValueFactory(new PropertyValueFactory<>("inspectionDate"));

        tableView.getColumns().addAll(fachnummerCol, herstellerCol, sorteCol, allergeneCol, inspektionsdatumCol);

        // Add selection listener for update and delete
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate input fields with selected Kuchen data
                nameField.setText(newSelection.getName());
                sorteField.setText(newSelection.getSorte());
                herstellerField.setText(newSelection.getHersteller());
                inspektionsDatumPicker.setValue(newSelection.getInspectionDate());
                // Update allergen checkboxes based on selected Kuchen
            }
        });

        return tableView;
    }

    private GridPane createInputPanel() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        Label sorteLabel = new Label("Sorte:");
        sorteField = new TextField();
        Label herstellerLabel = new Label("Hersteller:");
        herstellerField = new TextField();
        Label inspektionsDatumLabel = new Label("Inspektionsdatum:");
        inspektionsDatumPicker = new DatePicker();
        glutenfreiCheckbox = new CheckBox("Glutenfrei");
        lactosefreiCheckbox = new CheckBox("Lactosefrei");

        Button einfuegenButton = new Button("Einfügen");
        einfuegenButton.setOnAction(e -> einfuegenKuchen());

        Button loeschenButton = new Button("Löschen");
        loeschenButton.setOnAction(e -> loeschenKuchen());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateKuchen());

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(sorteLabel, 0, 1);
        grid.add(sorteField, 1, 1);
        grid.add(herstellerLabel, 0, 2);
        grid.add(herstellerField, 1, 2);
        grid.add(inspektionsDatumLabel, 0, 3);
        grid.add(inspektionsDatumPicker, 1, 3);
        grid.add(glutenfreiCheckbox, 0, 4);
        grid.add(lactosefreiCheckbox, 1, 4);
        grid.add(einfuegenButton, 1, 5);
        grid.add(loeschenButton, 1, 6);
        grid.add(updateButton, 1, 7);

        return grid;
    }

    private void einfuegenKuchen() {
        String name = nameField.getText();
        String sorte = sorteField.getText();
        String hersteller = herstellerField.getText();
        LocalDate inspektionsDatum = inspektionsDatumPicker.getValue();
        Set<Allergen> allergene = getAllergeneFromCheckboxes();

        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() throws Exception {
                // Simulate long running task
                Thread.sleep(1000);
                return automatService.einfuegenKuchen(name, sorte, hersteller, inspektionsDatum, allergene);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                int fach = getValue();
                if (fach != -1) {
                    Platform.runLater(() -> {
                        Kuchen kuchen = new Kuchen(fach, name, sorte, hersteller, inspektionsDatum, allergene);
                        kuchenList.add(kuchen);
                        // Clear input fields
                        nameField.clear();
                        sorteField.clear();
                        herstellerField.clear();
                        inspektionsDatumPicker.setValue(null);
                        glutenfreiCheckbox.setSelected(false);
                        lactosefreiCheckbox.setSelected(false);
                    });
                } else {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Kein freies Fach gefunden!");
                        alert.showAndWait();
                    });
                }
            }
        };

        new Thread(task).start();
    }

    private void loeschenKuchen() {
        Kuchen selectedKuchen = kuchenTableView.getSelectionModel().getSelectedItem();
        if (selectedKuchen != null) {
            automatService.loeschenKuchen(selectedKuchen.getFach());
            kuchenList.remove(selectedKuchen);
        } else {
            // Show an alert if no Kuchen is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "Bitte wählen Sie einen Kuchen zum Löschen aus.");
            alert.showAndWait();
        }
    }

    private void updateKuchen() {
        Kuchen selectedKuchen = kuchenTableView.getSelectionModel().getSelectedItem();
        if (selectedKuchen != null) {
            String name = nameField.getText();
            String sorte = sorteField.getText();
            String hersteller = herstellerField.getText();
            LocalDate inspektionsDatum = inspektionsDatumPicker.getValue();
            Set<Allergen> allergene = getAllergeneFromCheckboxes();

            // Update the Kuchen object
            selectedKuchen.setName(name);
            selectedKuchen.setSorte(sorte);
            selectedKuchen.setHersteller(hersteller);
            selectedKuchen.setInspectionDate(inspektionsDatum);

            // Refresh the TableView
            kuchenTableView.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Bitte wählen Sie einen Kuchen zum Aktualisieren aus.");
            alert.showAndWait();
        }
    }

    private Set<Allergen> getAllergeneFromCheckboxes() {
        Set<Allergen> allergene = new HashSet<>();
        if (glutenfreiCheckbox.isSelected()) {
            allergene.add(Allergen.Gluten);
        }
        if (lactosefreiCheckbox.isSelected()) {
            allergene.add(Allergen.Erdnuss);
        }
        if (lactosefreiCheckbox.isSelected()) {
            allergene.add(Allergen.Haselnuss);
        }
        if (lactosefreiCheckbox.isSelected()) {
            allergene.add(Allergen.Sesamsamen);
        }
        return allergene;
    }
}