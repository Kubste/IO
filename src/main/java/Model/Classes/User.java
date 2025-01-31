package Model.Classes;

import Model.Enums.AccessLevel;
import Model.Facades.activeUser;
import java.util.HashSet;

public class User extends Model implements activeUser {

	private String firstName;
	private String lastName;
	private AccessLevel accessLevel;
	private String email;
	private String password;
	private String username;
	private int departmentID;
	private HashSet<Integer> assignedDepartmentsIDs;
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
		if (this.accessLevel == AccessLevel.ADMIN) assignedDepartmentsIDs = new HashSet<>();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public AccessLevel getAccessLevel() {
		return this.accessLevel;
	}

	public void setAccessLevel(AccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setAssignedDepartmentsIDs(HashSet<Integer> assignedDepartmentsIDs) {
		this.assignedDepartmentsIDs = assignedDepartmentsIDs;
	}

	public HashSet<Integer> getAssignedDepartmentsIDs() {
		return this.assignedDepartmentsIDs;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
		this.save();
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", this.firstName, this.lastName, this.accessLevel.toString());
	}
}
