package app.network.jsonprotocol;

import app.network.dto.MeciDTO;

import java.util.List;

public class Response {
    private ResponseType responseType;
    String errorMessage;
    List<MeciDTO> meciuri;

    public Response(){

    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<MeciDTO> getMeciuri() {
        return meciuri;
    }

    public void setMeciuri(List<MeciDTO> meciuri){
        this.meciuri = meciuri;
    }
}
