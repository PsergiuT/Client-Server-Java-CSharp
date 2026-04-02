package app.network.jsonprotocol;

import app.model.implementation.Meci;
import app.model.implementation.Users;
import app.network.dto.MeciDTO;
import app.network.dto.UtilDTO;

import javax.swing.text.Utilities;
import java.util.ArrayList;
import java.util.List;

public class JsonProtocolUtils {
    // --- REQUESTS --- //
    public static Request createLoginRequest(Users user){
        Request req = new Request();
        req.setRequestType(RequestType.LOGIN);
        req.setUser(UtilDTO.getDTO(user));
        return req;
    }

    public static Request createLogoutRequest(Users user){
        Request req=new Request();
        req.setRequestType(RequestType.LOGOUT);
        req.setUser(UtilDTO.getDTO(user));
        return req;
    }


    // --- RESPONSES --- //
    public static Response createOkResponse(){
        Response res = new Response();
        res.setResponseType(ResponseType.OK);
        return res;
    }

    public static Response createErrorResponse(String errorMessage){
        Response res = new Response();
        res.setResponseType(ResponseType.ERROR);
        res.setErrorMessage(errorMessage);
        return res;
    }


    public static Response createGetAllMatchesResponse(List<Meci> matches) {
        Response res = new Response();
        res.setResponseType(ResponseType.GET_ALL_MATCHES);
        List<MeciDTO> meciuriDTO = new ArrayList<>();
        for(Meci m: matches){
            meciuriDTO.add(UtilDTO.getDTO(m));
        }
        res.setMeciuri(meciuriDTO);
        return res;
    }

    public static Request createGetAllMatchesRequest() {
        Request req = new Request();
        req.setRequestType(RequestType.GET_ALL_MATCHES);
        return req;
    }
}
