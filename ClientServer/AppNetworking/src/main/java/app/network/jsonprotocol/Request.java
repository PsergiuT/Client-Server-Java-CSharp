package app.network.jsonprotocol;

import app.network.dto.UserDTO;

public class Request {
    private RequestType requestType;
    private UserDTO user;

    public Request(){

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
