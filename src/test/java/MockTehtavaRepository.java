import fi.jyu.ohj2.nico.todo.model.Tehtava;
import fi.jyu.ohj2.nico.todo.persistance.TehtavaRepository;

import java.util.ArrayList;
import java.util.List;

public class MockTehtavaRepository implements TehtavaRepository {
    private List<Tehtava> tallennetutTehtavat = new ArrayList<>();

    @Override
    public List<Tehtava> lataa() {
        return tallennetutTehtavat;
    }

    @Override
    public void tallenna(List<Tehtava> tehtavat) {
        tallennetutTehtavat.clear();

        for (Tehtava tehtava : tehtavat) {
            Tehtava kopio = new Tehtava();
            kopio.setOtsikko(tehtava.getOtsikko());
            kopio.setPrioriteetti(tehtava.getPrioriteetti());
            kopio.setKuvaus(tehtava.getKuvaus());
            kopio.setTehty(tehtava.getTehty());
            tallennetutTehtavat.add(kopio);
        }
    }

    public List<Tehtava> getTallennetutTehtavat() {
        return this.tallennetutTehtavat;
    }
}
