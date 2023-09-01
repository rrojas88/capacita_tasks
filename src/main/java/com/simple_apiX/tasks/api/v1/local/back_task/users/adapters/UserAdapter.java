package com.simple_apiX.tasks.api.v1.local.back_task.users.adapters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.bd.User;



@Service
public class UserAdapter {
    
    private String myClassName = UserAdapter.class.getName();



    // Inic "users" sin BD
    private List<User> listBd = new ArrayList<>();


    public Object findAll()
    {
        return listBd;
    }


    public Object save( User user )
    {
        listBd.add( user );
        return listBd;
    }

}
