package Model.Classes;

import com.github.javafaker.App;

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
     * @param model
     */
	public void save(Model model) {
        if(model == null) throw new NullPointerException();
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
            }case null, default -> throw new IllegalArgumentException("Nieprawidlowy typ obiektu: " + model.getClass().getName());
        }
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
        if(model == null) throw new NullPointerException();
        switch(model) {
            case Application application -> {
                for(Application applicationDb : database.getAllApplications()){
                    if(applicationDb.getId() == application.getId()){
                        applicationDb.setArchived(application.isArchived());
                        applicationDb.setStatus(application.getStatus());
                        applicationDb.setConsiderationDate(application.getConsiderationDate());
                        applicationDb.setRejectedDescription(application.getRejectedDescription());
                        return true;
                    }
                }
                return false;
            }case User user -> {
                for(User userDb : database.getAllUsers()){
                    if(userDb.getId() == user.getId()){
                        userDb.setFirstName(user.getFirstName());
                        userDb.setEmail(user.getEmail());
                        userDb.setAccessLevel(user.getAccessLevel());
                        userDb.setIsActive(user.isActive());
                        userDb.setLastName(user.getLastName());
                        userDb.setPassword(user.getPassword());
                        return true;
                    }
                }
                return false;

            }case Copy copy -> {
                for(Copy copyDb : database.getAllCopies()){
                    if(copyDb.getId() == copy.getId()){
                        copyDb.setSize(copy.getSize());
                        copyDb.setDepartmentID(copy.getDepartmentID());
                        return true;
                    }
                }
                return false;

            }case Department department -> {
                for(Department departmentDb : database.getAllDepartments()){
                    if(departmentDb.getId() == department.getId()) {
                        departmentDb.setName(department.getName());
                        departmentDb.setAddress(department.getAddress());
                        departmentDb.setCitizens(department.getCitizens());
                        departmentDb.setOfficials(department.getOfficials());
                        departmentDb.setLocked(department.isLocked());
                        return true;
                    }
                }
                return false;
            }case null, default -> throw new IllegalArgumentException("Nieprawidlowy typ obiektu: " + model.getClass().getName());
        }
    }
}