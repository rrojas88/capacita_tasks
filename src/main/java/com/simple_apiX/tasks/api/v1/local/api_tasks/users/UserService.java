package com.simple_apiX.tasks.api.v1.local.api_tasks.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple_apiX.tasks.api.v1.local.Utils.ErrorService;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsService;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.UserAdapter;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.bd1.User;
import com.simple_apiX.tasks.api.v1.local.api_tasks.users.adapters.payloads.UserDto;



@Service
public class UserService {
    
    private String myClassName = UserService.class.getName();

    @Autowired
    UserAdapter userAdapter;


    public Object getAll(  )
    {
        try {
            Object resp = userAdapter.findAll();
            return resp;
        }
        catch (Exception e) {
            return new ErrorService( 
                "Ha ocurrido un error obteniendo la lista de usuarios", 
                e.getMessage(), 
                myClassName
            );
        }
    }


    public Object register( UserDto userDto )
    {
        try {
            // Log. Neg.: validar si existe el correo:
            Object respValCorreo = userAdapter.getByEmail( userDto.getEmail() );
            if( UtilsService.isErrorService( respValCorreo ) ) return respValCorreo;

            User userExist = ( User ) respValCorreo;
            if( userExist != null ){
                return new ErrorService( 
                    "El usuario ya se encuentra registrado", 
                    "El correo ya se encuentra registrado", 
                    myClassName,
                    400
                );
            }

            User user = new User();
            user.setName( userDto.getName() );
            user.setEmail( userDto.getEmail() );
            user.setPassword( userDto.getPassword() );

            Object resp = userAdapter.save( user );
            return resp;
        }
        catch (Exception e) {
            return new ErrorService( 
                "Ha ocurrido un error registrando el usuario", 
                e.getMessage(), 
                myClassName
            );
        }
    }

}
