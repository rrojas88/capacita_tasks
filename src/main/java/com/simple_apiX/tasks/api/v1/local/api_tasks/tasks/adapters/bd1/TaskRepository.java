package com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.bd1;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface TaskRepository extends JpaRepository< Task, Integer > {


	@Transactional
	@Query(value="SELECT t.* FROM tasks t WHERE t.user_id = :user_id ", nativeQuery=true )
	public List<Task> findByUserId( Integer user_id );


	@Transactional
    List<?> getPendingTask(String sql );


}
