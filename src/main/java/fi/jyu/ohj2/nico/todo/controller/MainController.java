package fi.jyu.ohj2.nico.todo.controller;

import fi.jyu.ohj2.nico.todo.App;
import fi.jyu.ohj2.nico.todo.model.Tehtavakokoelma;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import fi.jyu.ohj2.nico.todo.model.Tehtava;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class MainController implements Initializable {
    @FXML
    private Button lisaaUusiTehtavaPainike;

    @FXML
    private TextField uusiTehtavaNimi;

    @FXML
    private TableView<Tehtava> tehtavaTaulu;

    @FXML
    private Button poistaValittuPainike;

    private Tehtavakokoelma tehtavakokoelma = new Tehtavakokoelma();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SortedList<Tehtava> tehtavatLajiteltu = tehtavakokoelma.getTehtavat().sorted(Comparator.comparing(Tehtava::getTehty));

        tehtavaTaulu.setItems(tehtavatLajiteltu);
        tehtavaTaulu.setEditable(true);

        TableColumn<Tehtava, Boolean> tehtySarake = new TableColumn<>("Tehty");
        tehtySarake.setCellValueFactory(cd -> cd.getValue().tehtyProperty());
        tehtySarake.setCellFactory(CheckBoxTableCell.forTableColumn(tehtySarake));
        tehtavaTaulu.getColumns().add(tehtySarake);

        TableColumn<Tehtava, String> tekstiSarake = new TableColumn<>("Tehtävä");
        tekstiSarake.setCellValueFactory(cd -> cd.getValue().otsikkoProperty());
        tehtavaTaulu.getColumns().add(tekstiSarake);

        tehtavaTaulu.setRowFactory(tv -> {
            TableRow<Tehtava> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) &&
                        event.getClickCount() == 2 && !row.isEmpty()) {
                    Tehtava tehtava = row.getItem();
                    avaaTehtavanMuokkaus(tehtava);
                }
            });

            return row;
        });

        tehtavakokoelma.lataa();
        lisaaUusiTehtavaPainike.setOnAction(event -> lisaaTehtava());
        uusiTehtavaNimi.setOnAction(event -> lisaaTehtava());

        poistaValittuPainike.setOnAction(event -> poistaValittu());
    }


    private List<Tehtava> haeTehtavat(VBox sailio) {
        return sailio.getChildren().stream()
                .map(n -> (CheckBox) n)
                .map(cb -> new Tehtava(cb.getText(), cb.isSelected()))
                .toList();
    }


    private void lisaaTehtava() {
        tehtavakokoelma.lisaaTehtava(uusiTehtavaNimi.getText());
        uusiTehtavaNimi.clear();
        uusiTehtavaNimi.requestFocus();
    }


    private void poistaValittu() {
        Tehtava valittuTehtava = tehtavaTaulu.getSelectionModel().getSelectedItem();
        tehtavakokoelma.poistaTehtava(valittuTehtava);
    }


    private void avaaTehtavanMuokkaus(Tehtava tehtava) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("tehtava-edit.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            TehtavaEditController controller = loader.getController();
            controller.setTehtava(tehtava);

            Stage dialogi = new Stage();
            dialogi.setScene(scene);

            dialogi.setTitle("Tehtävän muokkaus: " + tehtava.getOtsikko());
            dialogi.setMinWidth(400);
            dialogi.setMinHeight(300);
            dialogi.initModality(Modality.APPLICATION_MODAL);

            dialogi.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
