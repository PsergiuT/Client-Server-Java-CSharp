package app.services;

import app.model.implementation.Meci;
import app.model.implementation.Users;

import java.util.List;

public interface IAppServices {
    void login(Users user, IAppObserver client) throws AppException;
    void logout(Users user, IAppObserver client) throws AppException;
    void vanzareBilet(Meci m, String numeClient, String adresaClient, String nr_locuri_string) throws AppException;
    List<Meci> cautaMeciuri(String adresaClient, String numeClient) throws AppException;
    void modificaLocuri(String id_bilet_string, String numar_locuri_string) throws AppException;
}
