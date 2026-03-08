package fi.jyu.ohj2.nico.todo.persistance;

import fi.jyu.ohj2.nico.todo.model.Tehtava;
import java.util.List;

public interface TehtavaRepository {
    List<Tehtava> lataa() throws RepositoryException;
    void tallenna(List<Tehtava> tehtavat) throws RepositoryException;
}