package app.network.dto;

import app.model.implementation.Meci;
import app.model.implementation.Users;

public class UtilDTO {

    public static UserDTO getDTO(Users user){
        return new UserDTO(user.getUsername(), user.getPassword());
    }

    public static Users getFromDTO(UserDTO userDto){
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPasswd());
        return user;
    }

    public static MeciDTO getDTO(Meci meci){
        return new MeciDTO(meci.getId(), meci.getDescriere(), meci.getPret(), meci.getNr_locuri(), meci.isSold_out());
    }

    public static Meci getFromDTO(MeciDTO meciDto){
        return new Meci(meciDto.getId(), meciDto.getDescriere(), meciDto.getPret(), meciDto.getNr_locuri(), meciDto.isSold_out());
    }
}
