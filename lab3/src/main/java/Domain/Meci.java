package Domain;

public class Meci {
    private Long id;
    private String descriere;
    private Float pret;
    private Integer nr_locuri;

    public Meci() {
    }

    public Meci(Long id, String descriere, Float pret, Integer nr_locuri) {
        this.id = id;
        this.descriere = descriere;
        this.pret = pret;
        this.nr_locuri = nr_locuri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Float getPret() {
        return pret;
    }

    public void setPret(Float pret) {
        this.pret = pret;
    }

    public Integer getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(Integer nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    @Override
    public String toString() {
        return "Meci{" +
                "id=" + id +
                ", descriere='" + descriere + '\'' +
                ", pret=" + pret +
                ", nr_locuri=" + nr_locuri +
                '}';
    }
}
