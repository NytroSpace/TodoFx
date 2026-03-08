package fi.jyu.ohj2.nico.todo.controller;


import fi.jyu.ohj2.nico.todo.model.Prioriteetti;
import fi.jyu.ohj2.nico.todo.model.Tehtava;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class TehtavaEditController implements Initializable {
    @FXML
    private TextField otsikkoKentta;

    @FXML
    private ComboBox<Prioriteetti> prioriteettiCombo;

    @FXML
    private TextArea kuvausKentta;

    @FXML
    private Button tallennaPainike;

    @FXML
    private Button peruutaPainike;

    private Tehtava muokattavaTehtava;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prioriteettiCombo.setItems(FXCollections.observableArrayList(Prioriteetti.values()));

        tallennaPainike.setOnAction(event -> {
            if (!validoi()) {
                return;
            }
            muokattavaTehtava.setOtsikko(otsikkoKentta.getText());
            muokattavaTehtava.setPrioriteetti(prioriteettiCombo.getValue());
            muokattavaTehtava.setKuvaus(kuvausKentta.getText());
            sulje();
        });
        peruutaPainike.setOnAction(event -> sulje());
    }


    public void setTehtava(Tehtava tehtava) {
        this.muokattavaTehtava = tehtava;
        otsikkoKentta.setText(tehtava.getOtsikko());
        prioriteettiCombo.setValue(tehtava.getPrioriteetti());
        kuvausKentta.setText(tehtava.getKuvaus());
    }


    private void sulje() {
        Scene scene = otsikkoKentta.getScene();
        Stage ikkuna = (Stage) scene.getWindow();
        ikkuna.close();
    }


    private boolean validoi() {
        otsikkoKentta.setStyle("");
        otsikkoKentta.setPromptText("");

        String otsikko = otsikkoKentta.getText();
        if (otsikko == null || otsikko.isBlank()) {
            otsikkoKentta.setStyle(
                    "-fx-border-color: red; " +
                            "-fx-background-color: #fdf2f2;");
            otsikkoKentta.clear();
            otsikkoKentta.setPromptText("Otsikko puuttuu!");
            return false;
        }

        return true;
    }

}