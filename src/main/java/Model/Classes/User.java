package Model.Classes;

import Model.Enums.AccessLevel;
import Model.Interfaces.activeUser;

public class User extends Model implements activeUser {

	private String firstName;
	private String lastName;
	private AccessLevel accessLevel;
	private String email;
	private String password;
	private String username;
	private int departmentID;
	private boolean isActive;

	public User(String firstName, String lastName, AccessLevel accessLevel, String email, String password, String username, int departmentID) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.accessLevel = accessLevel;
		this.email = email;
		this.password = password;
		this.username = username;
		this.departmentID = departmentID;
		this.isActive = false;
	}


	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public AccessLevel getAccessLevel() {
		return this.accessLevel;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public int getID() {
		return this.id;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public boolean isPrivileged() {
		return this.accessLevel == AccessLevel.ADMIN;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	public void login(String email, String password) {
		User user = activeUser.login(email, password);

		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.accessLevel = user.getAccessLevel();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.username = user.getUsername();
		this.departmentID = user.getDepartmentID();
		this.isActive = true;
	}

	public void logout() {
		this.id = -1;
		this.firstName = null;
		this.lastName = null;
		this.accessLevel = null;
		this.email = null;
		this.password = null;
		this.username = null;
		this.departmentID = -1;
		this.isActive = false;

		activeUser.logout();
	}
}