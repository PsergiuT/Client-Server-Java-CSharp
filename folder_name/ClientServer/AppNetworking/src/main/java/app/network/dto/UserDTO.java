package app.network.dto;

public class UserDTO {
    private String username;
    private String passwd;


    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }


    public UserDTO(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }
}
