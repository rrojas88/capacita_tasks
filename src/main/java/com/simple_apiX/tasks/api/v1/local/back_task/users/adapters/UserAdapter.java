package com.simple_apiX.tasks.api.v1.local.back_task.users.adapters;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.simple_apiX.tasks.api.v1.local.Utils.ErrorService;
import com.simple_apiX.tasks.api.v1.local.Utils.UtilsLocal;
import com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.bd.User;



@Service
public class UserAdapter {
    
    private String myClassName = UserAdapter.class.getName();



    // Inic "users" sin BD
    private List<User> listBd = new ArrayList<>();


    public Object findAll()
    {
        try {
            return listBd;
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


    public Object save( User user )
    {
        try {            
            listBd.add( user );
            return listBd;
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
