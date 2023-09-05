package com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.bd1;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.responses.UsersListResponse;


@Repository
public interface UserRepository extends JpaRepository< User, Integer > {
    
    @Query(value="SELECT u.id, u.name FROM users u ORDER BY u.name DESC; ", nativeQuery=true )
    public List<UsersListResponse> getUsersList( );
    //public List<Object> getUsersList( );

    public User findByEmail( String email );

}
