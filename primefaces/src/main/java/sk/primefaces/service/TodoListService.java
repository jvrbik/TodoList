package sk.primefaces.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import sk.primefaces.model.ToDo;

/**
 * Simple stateless session EJB.
 */

@Stateless
public class TodoListService {
	EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void persistTodo(ToDo todo) {
		entityManager.persist(todo);
	}

	public ToDo mergeTodo(ToDo todo) {
		return entityManager.merge(todo);
	}

	public void removeTodo(ToDo todo) {
		ToDo persistedToDo = entityManager.find(ToDo.class, todo.getUniqueId());
		entityManager.remove(persistedToDo);
	}

	public List<ToDo> findAllTodos(int userId) {
		TypedQuery<ToDo> query = entityManager.createQuery("SELECT t FROM ToDo t WHERE t.userId = :userid", ToDo.class)
				.setParameter("userid", userId);
		return query.getResultList();
	}
}
