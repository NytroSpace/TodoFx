package fi.jyu.ohj2.nico.todo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class MainController implements Initializable {
    @FXML
    private Button lisaaUusiTehtavaPainike;

    @FXML
    private TextField uusiTehtavaNimi;

    @FXML
    private Label tekemattomat;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        uusiTehtavaNimi.requestFocus();

        lisaaUusiTehtavaPainike.setOnAction(event -> {
            String teksti = uusiTehtavaNimi.getText();
            IO.println("Tekstikentän sisältö: " + teksti);
            tekemattomat.setText(tekemattomat.getText() + teksti + "\n");
        });
    }
}
