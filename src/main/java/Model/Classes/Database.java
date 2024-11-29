package Model.Classes;

import java.util.ArrayList;

public class Database {

	private ArrayList<Application> allApplications;
	private ArrayList<User> allUsers;
	private ArrayList<Copy> allCopies;
	private ArrayList<Department> allDepartments;

	public Database() {
		this.allApplications = new ArrayList<>();
		this.allUsers = new ArrayList<>();
		this.allCopies = new ArrayList<>();
		this.allDepartments = new ArrayList<>();
	}

	public ArrayList<Application> getAllApplications() {
		return allApplications;
	}

	public void setAllApplications(ArrayList<Application> allApplications) {
		this.allApplications = allApplications;
	}

	public ArrayList<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(ArrayList<User> allUsers) {
		this.allUsers = allUsers;
	}

	public ArrayList<Copy> getAllCopies() {
		return allCopies;
	}

	public void setAllCopies(ArrayList<Copy> allCopies) {
		this.allCopies = allCopies;
	}

	public ArrayList<Department> getAllDepartments() {
		return allDepartments;
	}

	public void setAllDepartments(ArrayList<Department> allDepartments) {
		this.allDepartments = allDepartments;
	}
}