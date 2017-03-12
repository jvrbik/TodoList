package sk.primefaces.bean;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import sk.primefaces.model.User;

@ManagedBean
@SessionScoped
public class UserManagerBean {

	private static final String TODOLIST_REDIRECT = "/pages/todolist/todo.xhtml?faces-redirect=true";
	private static final String LOGOUT_REDIRECT = "/index.html?faces-redirect=true";

	private Map<Map.Entry<String, String>, User> mockUsersMap;

	private String username;
	private String userpassw;
	private User currentUser;

	public boolean isLoggedIn() {
		return currentUser != null;
	}

	public String login() {
		currentUser = find(username, userpassw);

		if (currentUser != null) {
			return TODOLIST_REDIRECT;
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Login failed", "Invalid or unknown credentials."));
		}
		return null;
	}

	private User find(String username, String userpasswd) {
		User result = null;

		Map.Entry<String, String> key = new SimpleEntry<>(username, userpasswd);
		if (mockUsersMap.containsKey(key)) {
			result = mockUsersMap.get(key);
		}

		return result;
	}

	public String getFullName() {
		if (isLoggedIn()) {
			return currentUser.getFirstname() + " " + currentUser.getLastname();
		}
		return null;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return LOGOUT_REDIRECT;
	}

	@PostConstruct
	public void populateMockUsersMap() {
		mockUsersMap = new HashMap<>();
		mockUsersMap.put(new SimpleEntry<>("saso", "saso1234"), new User(1, "saso", "Jozef", "Mrkvicka"));
		mockUsersMap.put(new SimpleEntry<>("franta", "franta1234"), new User(2, "franta", "Frantisek", "Siska"));
		username = null;
		userpassw = null;
		currentUser = null;
	}

	// ----

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassw() {
		return userpassw;
	}

	public void setUserpassw(String userpassw) {
		this.userpassw = userpassw;
	}

	public User getCurrentUser() {
		return currentUser;
	}

}
