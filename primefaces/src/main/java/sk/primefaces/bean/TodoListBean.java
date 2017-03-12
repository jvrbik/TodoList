package sk.primefaces.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;

import sk.primefaces.model.ToDo;
import sk.primefaces.service.TodoListService;

@ManagedBean
@ViewScoped
public class TodoListBean {

	private List<ToDo> todoList = new ArrayList<>();

	private int userId;
	private String todoShortDesc;
	private String todoLongDesc;
	private ToDo currentToDo;

	@EJB
	TodoListService todoListService;

	@ManagedProperty(value = "#{userManagerBean}")
	UserManagerBean userManagerBean;

	/**
	 * Reset dialog fields.
	 */
	public void resetFields() {
		todoShortDesc = null;
		todoLongDesc = null;
	}

	/**
	 * Adds new todo into List and persists the entity. At the end closes
	 * dialog.
	 */
	public void addNewToDo() {
		if (StringUtils.isEmpty(todoShortDesc)) {
			addMessage("Please, fill the category first.", FacesMessage.SEVERITY_ERROR);
			return;
		}
		ToDo todo = new ToDo();
		todo.setUserId(userId);
		todo.setTodoShortDesc(todoShortDesc);
		todo.setTodoLongDesc(todoLongDesc);
		todoListService.persistTodo(todo);
		todoList.add(todo);

		RequestContext.getCurrentInstance().execute("PF('newToDoDialog').hide();");
	}

	/**
	 * Preparation before update/delete.
	 * 
	 * @param todo
	 *            {@link ToDo}, currently selected by PrimeFaces
	 */
	public void prepareToDoUpdate(ToDo todo) {
		todoShortDesc = todo.getTodoShortDesc();
		todoLongDesc = todo.getTodoLongDesc();
		currentToDo = todo;
	}

	/**
	 * Updates currentToDo and closes the dialog.
	 */
	public void updateToDo() {
		if (currentToDo != null) {
			if (todoShortDesc != null && !todoShortDesc.equals(currentToDo.getTodoShortDesc())) {
				if (StringUtils.isEmpty(todoShortDesc)) {
					addMessage("Please, fill the category first.", FacesMessage.SEVERITY_ERROR);
					return;
				}
				currentToDo.setTodoShortDesc(todoShortDesc);
				todoListService.mergeTodo(currentToDo);
			}
			if (todoLongDesc != null && !todoLongDesc.equals(currentToDo.getTodoLongDesc())) {
				currentToDo.setTodoLongDesc(todoLongDesc);
				todoListService.mergeTodo(currentToDo);
			}
		} else {
			addMessage("Technical problem (...), unable to update selected ToDo.", FacesMessage.SEVERITY_FATAL);
		}
		RequestContext.getCurrentInstance().execute("PF('updateToDoDialog').hide();");
	}

	/**
	 * Removes curentToDo from list and from datasource.
	 * 
	 * @param todo
	 *            {@link ToDo}, currently selected by PrimeFaces
	 */
	public void removeToDo(ToDo todo) {
		prepareToDoUpdate(todo);
		if (currentToDo != null) {
			todoListService.removeTodo(currentToDo);
			todoList.remove(todo);
		} else {
			addMessage("Technical problem (...), unabl" + "e to remove selected ToDo.", FacesMessage.SEVERITY_FATAL);
		}
	}

	/**
	 * Returns current list of ToDos.
	 * 
	 * @return list of ToDos
	 */
	public List<ToDo> getTodoList() {
		return todoList;
	}

	/**
	 * Set new list of ToDos - called at the beginning of new session.
	 * 
	 * @param todoList
	 *            list of ToDos
	 */
	private void setTodoList(List<ToDo> todoList) {
		this.todoList = todoList;
	}

	/**
	 * Adds new message to UI.
	 * 
	 * @param summary
	 *            description
	 * @param severity
	 *            message severity
	 */
	public void addMessage(String summary, Severity severity) {
		FacesMessage message = new FacesMessage(severity, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	@PostConstruct
	public void populateTodoList() {
		userId = userManagerBean.getCurrentUser().getUserId();
		setTodoList(todoListService.findAllTodos(userId));
	}

	// -----

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTodoShortDesc() {
		return todoShortDesc;
	}

	public void setTodoShortDesc(String todoShortDesc) {
		this.todoShortDesc = todoShortDesc;
	}

	public String getTodoLongDesc() {
		return todoLongDesc;
	}

	public void setTodoLongDesc(String todoLongDesc) {
		this.todoLongDesc = todoLongDesc;
	}

	public ToDo getCurrentToDo() {
		return currentToDo;
	}

	public void setCurrentToDo(ToDo currentToDo) {
		this.currentToDo = currentToDo;
	}

	public UserManagerBean getUserManagerBean() {
		return userManagerBean;
	}

	public void setUserManagerBean(UserManagerBean userManagerBean) {
		this.userManagerBean = userManagerBean;
	}
}
