package com.simple_apiX.tasks.api.v1.local.back_task.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple_apiX.tasks.api.v1.local.Utils.ErrorService;
import com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.UserAdapter;
import com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.bd.User;
import com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.payloads.UserDto;



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
