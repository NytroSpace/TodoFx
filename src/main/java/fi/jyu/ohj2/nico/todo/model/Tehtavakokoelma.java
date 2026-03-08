package fi.jyu.ohj2.nico.todo.model;

import fi.jyu.ohj2.nico.todo.persistance.RepositoryException;
import fi.jyu.ohj2.nico.todo.persistance.TehtavaRepository;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.*;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Tehtavakokoelma {
    private final ObservableList<Tehtava> tehtavat = FXCollections.observableArrayList(
            tehtava -> new Observable[]{tehtava.tehtyProperty()}
    );


    private final ObjectMapper mapper = new ObjectMapper();
    private final TehtavaRepository repository;


    public Tehtavakokoelma(TehtavaRepository repository) {
        this.repository = repository;

        this.tehtavat.addListener((ListChangeListener<Tehtava>) change -> {
            tallenna();
        });
    }


    public ObservableList<Tehtava> getTehtavat() {
        return tehtavat;
    }

    public void tallenna() {
        try {
            repository.tallenna(tehtavat);
        } catch (RepositoryException e) {
            IO.println(e.getMessage());
        }
    }

    public void lataa() {
        try {
            List<Tehtava> kaikkiTehtavat = repository.lataa();
            tehtavat.addAll(kaikkiTehtavat);
        } catch (RepositoryException e) {
            IO.println(e.getMessage());
        }
    }

    public void lisaaTehtava(String teksti) {
        if (teksti == null || teksti.isBlank()) {
            return;
        }
        teksti = teksti.trim();
        tehtavat.add(new Tehtava(teksti, false));
    }

    public void poistaTehtava(Tehtava tehtava) {
        if (tehtava == null) {
            return;
        }
        tehtavat.remove(tehtava);
    }
}
