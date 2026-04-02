package app.network.dto;

public class BiletDTO {
    String id_bilet;
    String numeClient;
    String adresaClient;
    String nr_locuri;

    public BiletDTO(){

    }

    public String getId_bilet() {
        return id_bilet;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public String getAdresaClient() {
        return adresaClient;
    }

    public String getNr_locuri() {
        return nr_locuri;
    }

    public void setId_bilet(String id_bilet) {
        this.id_bilet = id_bilet;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public void setAdresaClient(String adresaClient) {
        this.adresaClient = adresaClient;
    }

    public void setNr_locuri(String nr_locuri) {
        this.nr_locuri = nr_locuri;
    }
}
