package Model.Classes;

import java.util.ArrayList;

public class DBManager {

	private static DBManager instance = null;
	private String db_host;
	private String db_name;
	private String db_user;
	private String db_password;
    private Database database;

	private DBManager() {
	}

    private DBManager(Database db) {
        this.database = db;
    }

	public static DBManager getInstance() {
		if(DBManager.instance == null){
            DBManager.instance = new DBManager();
        }

		return DBManager.instance;
	}

    public static DBManager getInstance(ArrayList<User> users, ArrayList<Department> departments, ArrayList<Application> applications) {

        Database db = new Database();
        db.setAllApplications(applications);
        db.setAllDepartments(departments);
        db.setAllUsers(users);

        if(DBManager.instance == null){
            DBManager.instance = new DBManager(db);
        }

        return DBManager.instance;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
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

        switch(model) {
            case Application application -> {
                database.getAllApplications()
                        .stream()
                        .filter(app -> app.getId() == model.getId())
                        .findFirst()
                        .ifPresent(app -> {
                            app = (Application) model;
                        });
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
}