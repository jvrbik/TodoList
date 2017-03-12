package sk.primefaces.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TODO")
public class ToDo {

	@Id
	@Column(name = "UNIQUE_ID")
	@GeneratedValue
	private long uniqueId;

	@Column(name = "USER_ID")
	private int userId;

	@Column(name = "TODO_SHORT_DESC")
	private String todoShortDesc;

	@Column(name = "TODO_LONG_DESC")
	private String todoLongDesc;

	public ToDo() {
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getUniqueId() {
		return uniqueId;
	}
}
