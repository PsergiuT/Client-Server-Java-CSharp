package app.network.jsonprotocol;

import app.model.implementation.Meci;
import app.services.AppException;
import app.services.IAppObserver;
import app.services.IAppServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;
import java.util.List;

public class AppClientJsonWorker implements Runnable, IAppObserver{
    private final Socket client;
    private final IAppServices services;
    private static Logger logger = LogManager.getLogger(AppClientJsonWorker.class);

    public AppClientJsonWorker(Socket client, IAppServices services) {
        this.client = client;
        this.services = services;
    }
    @Override
    public void run() {

    }

    @Override
    public void soldTicket(List<Meci> matches) throws AppException {

    }

    @Override
    public void updateTicket(List<Meci> matches) throws AppException {

    }
}
