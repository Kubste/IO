package Model.Classes;

import java.util.ArrayList;
import java.util.Objects;

public class DBManager {

	Model dbManager;
	private static DBManager instance;
	private String db_host;
	private String db_name;
	private String db_user;
	private String db_password;
	private Database database;

	private DBManager() {
		// TODO - implement Model.Classes.Model.Classes.DBManager.Model.Classes.Model.Classes.DBManager
		throw new UnsupportedOperationException();
	}

	public static DBManager getInstance() {
		if(instance == null) instance = new DBManager();
		return instance;
	}

	/**
	 * 
	 * @param model
	 */
	public boolean save(Model model) {
        switch(model) {
            case Application application -> {
                ArrayList<Application> applications = database.getAllApplications();
                applications.add(application);
                database.setAllApplications(applications);
            }case User user -> {
                ArrayList<User> users = database.getAllUsers();
                users.add(user);
                database.setAllUsers(users);
            }case Copy copy -> {
                ArrayList<Copy> copies = database.getAllCopies();
                copies.add(copy);
                database.setAllCopies(copies);
            }case Department department -> {
                ArrayList<Department> departments = database.getAllDepartments();
                departments.add(department);
                database.setAllDepartments(departments);
            }case null, default -> {
                return false;
            }
        }
		return true;
	}

	/**
	 * 
	 * @param model
	 */
	public boolean delete(Model model) {
        switch(model) {
            case Application application -> {
                ArrayList<Application> applications = database.getAllApplications();
                applications.remove(application);
                database.setAllApplications(applications);
            }case User user -> {
                ArrayList<User> users = database.getAllUsers();
                users.remove(user);
                database.setAllUsers(users);
            }case Copy copy -> {
                ArrayList<Copy> copies = database.getAllCopies();
                copies.remove(copy);
                database.setAllCopies(copies);
            }case Department department -> {
                ArrayList<Department> departments = database.getAllDepartments();
                departments.remove(department);
                database.setAllDepartments(departments);
            }case null, default -> {
                return false;
            }
        }
		return true;
	}

	/**
	 * 
	 * @param model
	 */
	public boolean update(Model model) {
		// TODO - implement Model.Classes.Model.Classes.DBManager.update
		throw new UnsupportedOperationException();
	}

	public String getCorrectPassword(String username) {
		for(User user : database.getAllUsers()) {
			if(Objects.equals(user.getUsername(), username)) return user.getPassword();
		}
		return null;
	}

}