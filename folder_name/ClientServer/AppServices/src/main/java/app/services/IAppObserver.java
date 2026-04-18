package app.services;

import app.model.implementation.Meci;

import java.util.List;

public interface IAppObserver {
    void soldTicket(List<Meci> matches) throws AppException;
    void updateTicket(List<Meci> matches) throws AppException;
}
