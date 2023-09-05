package com.simple_apiX.tasks.api.v1.local.back_task.users.adapters;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simple_apiX.tasks.api.v1.local.Utils.ErrorService;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.bd1.User;
import com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.bd1.UserRepository;



@Service
public class UserAdapter {
    
    private String myClassName = UserAdapter.class.getName();

    @Autowired
    UserRepository userRepository;


    public Object findAll()
    {
        try {
            Object resp = userRepository.getUsersList();
            return resp;
        }
        catch ( Exception e ){
            UtilsLocal.log( e.getMessage() );
            return new ErrorService( 
                "Ha ocurrido un error obteniendo la lista de usuarios", 
                e.getMessage(), 
                myClassName
            );
        }
    }


    public Object getByEmail( String email )
    {
        try {
            Object resp = userRepository.findByEmail( email );
            return resp;
        }
        catch ( Exception e ){
            UtilsLocal.log( e.getMessage() );
            return new ErrorService( 
                "Ha ocurrido un error consultando usuarios por correo", 
                e.getMessage(), 
                myClassName
            );
        }
    }


    public Object save( User user )
    {
        try {
            Object resp = userRepository.save( user );
            return resp;
        }
        catch ( Exception e ){
            return new ErrorService( 
                "Ha ocurrido un error guardando el usuario", 
                e.getMessage(), 
                myClassName
            );
        }
    }

}
