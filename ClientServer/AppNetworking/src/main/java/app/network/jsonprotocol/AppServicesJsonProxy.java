package app.network.jsonprotocol;

import app.model.implementation.Meci;
import app.model.implementation.Users;
import app.network.dto.MeciDTO;
import app.network.dto.UtilDTO;
import app.network.utils.TextUtils;
import app.services.AppException;
import app.services.IAppObserver;
import app.services.IAppServices;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AppServicesJsonProxy implements IAppServices{
    private String host;
    private int port;
    private IAppObserver client;

    private final BlockingQueue<Response> qresponses;             // thread save queue for messages

    private BufferedReader input;
    private PrintWriter output;
    private Socket connection;
    private Gson gsonFormatter;

    private static Logger logger = LogManager.getLogger(AppServicesJsonProxy.class);

    private volatile boolean finished;                              // volatile enforces that every thread reads this value from RAM not from the L1/L2 cache

    public AppServicesJsonProxy(String host, int port){
        this.host = host;
        this.port = port;
        this.qresponses = new LinkedBlockingQueue<>();

        logger.info("Initializing connection socket");
        initializeConnection();
    }


    // --- INITIALIZATION STEP --- //


    private void initializeConnection(){
        try{
            gsonFormatter = new Gson();
            connection = new Socket(host, port);
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            output = new PrintWriter(connection.getOutputStream());
            output.flush();
            finished = false;
            logger.info("Starting reader thread");
            startReaderThread();
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }
    private void startReaderThread(){
        Thread reader = new Thread(new ReaderThread());
        reader.start();
    }


    private boolean isUpdate(Response response){
        return response.getResponseType() == ResponseType.BOUGHT_TICKET || response.getResponseType() == ResponseType.UPDATED_TICKET;
    }
    private class ReaderThread implements Runnable{

        @Override
        public void run() {
            while(!finished){
                Response response = null;
                try{
                    String responseLine = input.readLine();
                    response = gsonFormatter.fromJson(responseLine, Response.class);
                    if(isUpdate(response)){
                        handleUpdate(response);
                    }
                    else{
                        qresponses.put(response);
                    }
                } catch (IOException | InterruptedException e) {
                    logger.error(e);
                    logger.error(e.getStackTrace());
                }
            }
        }
    }

    private void handleUpdate(Response response){
        switch(response.getResponseType()){
            case BOUGHT_TICKET:
                List<MeciDTO> meciuriDTO = response.getMeciuri();
                List<Meci> meciuri = new ArrayList<>();
                for(MeciDTO meciDTO : meciuriDTO){
                    meciuri.add(UtilDTO.getFromDTO(meciDTO));
                }
                client.soldTicket(meciuri);
                break;

            case UPDATED_TICKET:
                meciuriDTO = response.getMeciuri();
                meciuri = new ArrayList<>();
                for(MeciDTO meciDTO : meciuriDTO){
                    meciuri.add(UtilDTO.getFromDTO(meciDTO));
                }
                client.updateTicket(meciuri);
                break;
        }

    }

    private void closeConnection(){
        finished = true;
        try{
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            logger.error(e);
        }
    }

    // --- SEND AND READ REQUESTS / RESPONSES --- //


    private void sendRequest(Request req) throws AppException{
        String reqLine = gsonFormatter.toJson(req);
        try {
            output.println(reqLine);
            output.flush();
        } catch (Exception e) {
            logger.error(e);
            throw new AppException(e.getMessage());
        }
    }


    private Response readResponse() throws AppException{
        Response res = null;
        try{
            res = qresponses.take();
        } catch (InterruptedException e) {
            logger.error(e);
            throw new AppException(e.getMessage());
        }
        return res;
    }


    // --- SERVICE METHODS --- //


    @Override
    public void login(Users user, IAppObserver client) throws AppException {
        user.setPassword(TextUtils.simpleEncode(user.getPassword()));
        Request req = JsonProtocolUtils.createLoginRequest(user);
        sendRequest(req);
        Response res = readResponse();
        switch (res.getResponseType()){
            case OK:
                this.client = client;
                return;

            case ERROR:
                closeConnection();
                throw new AppException(res.getErrorMessage());


        }
    }

    @Override
    public void logout(Users user, IAppObserver client) throws AppException {
        Request req = JsonProtocolUtils.createLogoutRequest(user);
        sendRequest(req);
        Response res = readResponse();
        switch(res.getResponseType()){
            case OK:
                closeConnection();
                return;

            case ERROR:
                closeConnection();
                throw new AppException(res.getErrorMessage());
        }
    }

    @Override
    public List<Meci> findAll() throws AppException {
        return List.of();
    }

    @Override
    public void vanzareBilet(Meci m, String numeClient, String adresaClient, String nr_locuri_string) throws AppException {

    }

    @Override
    public List<Meci> cautaMeciuri(String adresaClient, String numeClient) throws AppException {
        return List.of();
    }

    @Override
    public void modificaLocuri(String id_bilet_string, String numar_locuri_string) throws AppException {

    }
}
