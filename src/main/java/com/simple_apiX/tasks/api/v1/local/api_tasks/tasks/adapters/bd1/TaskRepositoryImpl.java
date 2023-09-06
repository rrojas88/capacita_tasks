package com.simple_apiX.tasks.api.v1.local.api_tasks.tasks.adapters.bd1;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class TaskRepositoryImpl {
	

	@PersistenceContext(unitName = "Bd1PostgresConfig")
	private EntityManager em;

	public List<?> getPendingTask( String sql ){
		try {
			List<?> result = em.createNativeQuery(sql, PendingTask.class)
				.getResultList();
			return result;
		}
		catch (Exception e) {
			System.out.println("Error en TaskRepositoryImpl: " + e );
            return null;
		}
	}



}
