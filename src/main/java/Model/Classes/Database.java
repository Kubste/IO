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

	public void addCopy(Copy copy) {
		this.allCopies.add(copy);
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

	public void updateApplication(Application application) {
		for (int i = 0; i < allApplications.size(); i++) {
			if (allApplications.get(i).getId() == application.getId()) {
				allApplications.set(i, application);
				return;
			}
		}
	}


	public void updateDB(Model model) {
		switch(model) {
			case Application application -> {
				for(int i = 0; i < allApplications.size(); i++) {
					if (allApplications.get(i).getId() == application.getId()) {
						allApplications.set(i, application);
						return;
					}
				}
			}
			case User user -> {
				for(int i = 0; i < allUsers.size(); i++) {
					if (allUsers.get(i).getId() == user.getId()) {
						allUsers.set(i, user);
						return;
					}
				}
			}
			case Department department -> {
				for(int i = 0; i < allDepartments.size(); i++) {
					if (allDepartments.get(i).getId() == department.getId()) {
						allDepartments.set(i, department);
						return;
					}
				}
			}
			case Copy copy -> {
				for(int i = 0; i < allCopies.size(); i++) {
					if (allCopies.get(i).getId() == copy.getId()) {
						allCopies.set(i, copy);
						return;
					}
				}
			}
			default -> throw new IllegalStateException();
		}
	}
}