package Model.Classes;

import Model.Enums.AccessLevel;
import Model.Interfaces.activeUser;
import java.util.Objects;

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

	public String getLastName() {
		return this.lastName;
	}

	public AccessLevel getAccessLevel() {
		return this.accessLevel;
	}

	public boolean isPrivileged() {
        return this.accessLevel == AccessLevel.ADMIN;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public int getID() {
		return this.id;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}
	@Override
	public boolean login(String username, String password) {
		if(Objects.equals(password, dbManager.getCorrectPassword(username))) {

		}
		return true;
	}

	@Override
	public boolean logout() {
		this.isActive = false;
		return true;
	}
}