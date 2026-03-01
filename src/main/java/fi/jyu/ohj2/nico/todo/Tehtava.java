package fi.jyu.ohj2.nico.todo;

public class Tehtava {
    @SuppressWarnings("FieldMayBeFinal")
    private String teksti;
    @SuppressWarnings("FieldMayBeFinal")
    private boolean tehty;

    @SuppressWarnings("unused")
    public Tehtava() {}

    public Tehtava(String teksti, boolean tehty) {
        this.teksti = teksti;
        this.tehty = tehty;
    }

    public boolean getTehty() { return tehty; }

    public String getTeksti() { return teksti; }
}
