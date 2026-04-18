package app.model.implementation;

import app.model.Identifiable;

public class Bilet implements Identifiable<Long> {
    private Long id;
    private String numeClient;
    private String adresaClient;
    private Long id_meci;
    private Integer nr_locuri;

    public Bilet() {
    }

    public Bilet(Long id, String numeClient, String adresaClient, Long id_meci, Integer nr_locuri) {
        this.id = id;
        this.numeClient = numeClient;
        this.adresaClient = adresaClient;
        this.id_meci = id_meci;
        this.nr_locuri = nr_locuri;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public String getAdresaClient() {
        return adresaClient;
    }

    public void setAdresaClient(String adresaClient) {
        this.adresaClient = adresaClient;
    }

    public Long getId_meci() {
        return id_meci;
    }

    public void setId_meci(Long id_meci) {
        this.id_meci = id_meci;
    }

    public Integer getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(Integer nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "id=" + id +
                ", numeClient='" + numeClient + '\'' +
                ", adresaClient='" + adresaClient + '\'' +
                ", id_meci=" + id_meci +
                ", nr_locuri=" + nr_locuri +
                '}';
    }
}
