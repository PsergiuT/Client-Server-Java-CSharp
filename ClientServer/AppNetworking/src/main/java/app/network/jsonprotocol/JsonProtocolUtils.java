package app.network.jsonprotocol;

import app.model.implementation.Meci;
import app.model.implementation.Users;
import app.network.dto.BiletDTO;
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
        res.setResponseType(ResponseType.GET_MATCHES);
        res.setMeciuri(getDtoFromMeciuri(matches));
        return res;
    }

    public static Request createGetAllMatchesRequest() {
        Request req = new Request();
        req.setRequestType(RequestType.GET_MATCHES);
        return req;
    }

    public static List<Meci> getMeciuriFromDto(Response res){
        List<MeciDTO> meciuriDTO = res.getMeciuri();
        List<Meci> meciuri = new ArrayList<>();
        for(MeciDTO meci: meciuriDTO){
            meciuri.add(UtilDTO.getFromDTO(meci));
        }
        return meciuri;
    }

    public static List<MeciDTO> getDtoFromMeciuri(List<Meci> matches){
        List<MeciDTO> meciuri = new ArrayList<>();
        for(Meci meci: matches){
            meciuri.add(UtilDTO.getDTO(meci));
        }
        return meciuri;
    }

    public static Request createBuyTicketRequest(Meci m, String numeClient, String adresaClient, String nr_locuri_string){
        Request req = new Request();
        req.setRequestType(RequestType.BUY_TICKET);

        BiletDTO bilet = new BiletDTO();
        bilet.setNumeClient(numeClient);
        bilet.setAdresaClient(adresaClient);
        bilet.setNr_locuri(nr_locuri_string);
        req.setBilet(bilet);

        MeciDTO meciDto = UtilDTO.getDTO(m);
        req.setMeci(meciDto);
        return req;
    }

    public static Request createSearchMatchesRequest(String adresaClient, String numeClient) {
        BiletDTO bilet = new BiletDTO();
        bilet.setAdresaClient(adresaClient);
        bilet.setNumeClient(numeClient);

        Request req = new Request();
        req.setRequestType(RequestType.SEARCH_MATCHES);
        req.setBilet(bilet);
        return req;
    }

    public static Request createModifySeatsRequest(String idBiletString, String numarLocuriString) {
        BiletDTO bilet = new BiletDTO();
        bilet.setId_bilet(idBiletString);
        bilet.setNr_locuri(numarLocuriString);

        Request req = new Request();
        req.setRequestType(RequestType.UPDATE_TICKET);
        req.setBilet(bilet);
        return req;
    }

    public static Response createBuyTicketResponse(List<Meci> matches) {
        Response res = new Response();
        res.setResponseType(ResponseType.BUY_TICKET);
        res.setMeciuri(getDtoFromMeciuri(matches));
        return res;
    }

    public static Response createModifySeatsResponse(List<Meci> matches) {
        Response res = new Response();
        res.setResponseType(ResponseType.UPDATE_TICKET);
        res.setMeciuri(getDtoFromMeciuri(matches));
        return res;
    }

}
