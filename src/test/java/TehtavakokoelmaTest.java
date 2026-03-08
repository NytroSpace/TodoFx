import fi.jyu.ohj2.nico.todo.model.Prioriteetti;
import fi.jyu.ohj2.nico.todo.model.Tehtava;
import fi.jyu.ohj2.nico.todo.model.Tehtavakokoelma;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TehtavakokoelmaTest {

    @Test
    void lisaaTehtava_lisaaTehtavanJaTallentaaSen() {
        MockTehtavaRepository mockRepo = new MockTehtavaRepository();
        Tehtavakokoelma malli = new Tehtavakokoelma(mockRepo);

        malli.lisaaTehtava("Käy kaupassa");

        assertEquals(1, malli.getTehtavat().size(), "Listassa pitäisi olla 1 tehtävä.");
        assertEquals("Käy kaupassa", malli.getTehtavat().get(0).getOtsikko(), "Otsikon pitäisi täsmätä");

        assertEquals(1, mockRepo.getTallennetutTehtavat().size(), "Data olisi pitänyt tallentaa rajapinnan läpi!");
    }

    @Test
    void lisaaTehtava_eiLisaaTyhjaaOtsikkoa() {
        MockTehtavaRepository mockRepo = new MockTehtavaRepository();
        Tehtavakokoelma malli = new Tehtavakokoelma(mockRepo);

        malli.lisaaTehtava("   ");

        assertEquals(0, malli.getTehtavat().size(), "Tyhjiä tehtäviä ei saa lisätä listaan.");
    }


    @Test
    void tehtavakokoelmaTallennusToimiiLisayksestaJaPoistosta() {
        MockTehtavaRepository repo = new MockTehtavaRepository();
        Tehtavakokoelma kokoelma = new Tehtavakokoelma(repo);

        kokoelma.lisaaTehtava("Käy kaupassa");

        assertEquals(1, repo.getTallennetutTehtavat().size(), "Tehtävät tallentuvat, kun uusi tehtävä lisätään");

        Tehtava tehtava = kokoelma.getTehtavat().getFirst();
        kokoelma.poistaTehtava(tehtava);

        assertEquals(0, repo.getTallennetutTehtavat().size(), "Tehtävät tallentuvat, kun tehtävä poistetaan");
    }

    @Test
    void tehtavakokoelmaTallennusToimiiAttribuuttienMuutoksesta() {
        MockTehtavaRepository repo = new MockTehtavaRepository();
        Tehtavakokoelma kokoelma = new Tehtavakokoelma(repo);

        kokoelma.lisaaTehtava("Käy kaupassa");
        Tehtava tehtava = kokoelma.getTehtavat().getFirst();
        tehtava.setTehty(true);

        Tehtava tallennettuTehtava = repo.getTallennetutTehtavat().getFirst();
        assertEquals(tehtava.getTehty(), tallennettuTehtava.getTehty(), "Tehtävän tehty-tila tallentuu, kun se muutetaan");

        tehtava.setOtsikko("Mene nukkumaan");
        tallennettuTehtava = repo.getTallennetutTehtavat().getFirst();
        assertEquals(tehtava.getOtsikko(), tallennettuTehtava.getOtsikko(), "Tehtävän otsikko tallentuu, kun se muutetaan");

        tehtava.setPrioriteetti(Prioriteetti.KORKEA);
        tallennettuTehtava = repo.getTallennetutTehtavat().getFirst();
        assertEquals(tehtava.getPrioriteetti(), tallennettuTehtava.getPrioriteetti(), "Tehtävän prioriteetti tallentuu, kun se muutetaan");

        tehtava.setKuvaus("Nukkuminen on kivaa");
        tallennettuTehtava = repo.getTallennetutTehtavat().getFirst();
        assertEquals(tehtava.getKuvaus(), tallennettuTehtava.getKuvaus(), "Tehtävän kuvaus tallentuu, kun se muutetaan");
    }

}
