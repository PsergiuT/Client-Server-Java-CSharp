package app.network.utils;

import app.network.jsonprotocol.AppClientJsonWorker;
import app.services.IAppServices;

import java.net.Socket;

public class JsonConcurrentServerImpl extends AbstractConcurrentServer{
    private final IAppServices services;

    public JsonConcurrentServerImpl(int port, IAppServices services) {
        super(port);
        this.services = services;
    }

    @Override
    protected Thread createWorker(Socket client) {
        AppClientJsonWorker worker = new AppClientJsonWorker(client, services);
        return new Thread(worker);
    }
}
