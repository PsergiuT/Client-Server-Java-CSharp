package app.network.jsonprotocol;

import app.model.implementation.Meci;
import app.model.implementation.Users;
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
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class AppClientJsonWorker implements Runnable, IAppObserver{
    private final Socket connection;
    private final IAppServices services;
    private static Logger logger = LogManager.getLogger(AppClientJsonWorker.class);

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    private static Response okResponse = JsonProtocolUtils.createOkResponse();

    public AppClientJsonWorker(Socket connection, IAppServices services) {
        this.connection = connection;
        this.services = services;
        gsonFormatter = new Gson();
        try{
            input = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
            output = new PrintWriter(connection.getOutputStream());
            output.flush();
            connected = true;
        } catch (Exception e){
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }


    @Override
    public void run() {
        while(connected){
            try {
                String requestLine = input.readLine();
                Request request = gsonFormatter.fromJson(requestLine, Request.class);
                Response response = handleRequest(request);
                if(response != null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
        closeConnection();
    }

    private void closeConnection() {
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }


    private Response handleRequest(Request req){
        Response res = null;
        switch(req.getRequestType()){
            case LOGIN:
                Users user = UtilDTO.getFromDTO(req.getUser());
                user.setPassword(TextUtils.simpleDecode(user.getPassword()));
                try{
                    services.login(user, this);
                    return okResponse;
                } catch (AppException e){
                    return JsonProtocolUtils.createErrorResponse(e.getMessage());
                }

            case LOGOUT:
                return null;

            default:
                return res;
        }
    }


    private void sendResponse(Response res){
        String resLine = gsonFormatter.toJson(res);
        try{
            output.println(resLine);
            output.flush();
        } catch (Exception e){
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    @Override
    public void soldTicket(List<Meci> matches) throws AppException {
        // these will get called from the service
    }

    @Override
    public void updateTicket(List<Meci> matches) throws AppException {
        // these will get called from the service
    }
}
