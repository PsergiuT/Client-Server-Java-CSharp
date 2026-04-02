package app.network.jsonprotocol;

import app.network.dto.BiletDTO;
import app.network.dto.MeciDTO;
import app.network.dto.UserDTO;

import java.util.List;

public class Request {
    private RequestType requestType;
    private UserDTO user;
    private BiletDTO bilet;
    private MeciDTO meci;

    public void setBilet(BiletDTO bilet) {
        this.bilet = bilet;
    }

    public void setMeci(MeciDTO meci) {
        this.meci = meci;
    }

    public MeciDTO getMeci() {
        return meci;
    }

    public BiletDTO getBilet() {
        return bilet;
    }

    private List<MeciDTO> meciuri;

    public Request(){

    }

    public List<MeciDTO> getMeciuri(){
        return meciuri;
    }

    public void setMeciuri(List<MeciDTO> meciuri){
        this.meciuri = meciuri;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
