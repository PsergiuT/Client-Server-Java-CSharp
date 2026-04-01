package app.model.implementation;

import app.model.Identifiable;

public class Meci implements Identifiable<Long> {
    private Long id;
    private String descriere;
    private Float pret;
    private Integer nr_locuri;
    private boolean sold_out;


    public Meci(Long id, String descriere, Float pret, Integer nr_locuri, Boolean sold_out) {
        this.id = id;
        this.descriere = descriere;
        this.pret = pret;
        this.nr_locuri = nr_locuri;
        this.sold_out = sold_out;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public void setSold_out(boolean sold_out) {
        this.sold_out = sold_out;
    }

    public boolean isSold_out() {
        return sold_out;
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
