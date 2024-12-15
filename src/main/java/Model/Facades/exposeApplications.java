package Model.Facades;

import Model.Classes.Application;
import Model.Classes.DBManager;
import Model.Enums.ApplicationStatus;
import Model.Classes.User;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class exposeApplications {

	/**
	 * 
	 * @param applicationID
	 */
	static int getUserID(int applicationID){
		return -1;
	};

	/**
	 * 
	 * @param applicationID
	 */
	static int getDepartmentID(int applicationID){
		return 1;
	}

	/**
	 * 
	 * @param applicationID
	 */
	static ApplicationStatus getApplicationStatus(int applicationID){
		return Objects.requireNonNull(DBManager.getInstance().getDatabase().getAllApplications().stream().
                filter(application -> application.getId() == applicationID).findFirst().orElse(null)).getStatus();
	}

	/**
	 * 
	 * @param applicationID
	 */
	public static void acceptApplication(int applicationID){
		Application application = Objects.requireNonNull(DBManager.getInstance().getDatabase().getAllApplications().stream().
                filter(app -> app.getId() == applicationID).findFirst().orElse(null));
		application.setAcceptParams();
		DBManager.getInstance().update(application);
	}

	/**
	 * 
	 * @param applicationID
	 */
	public static void rejectApplication(int applicationID, String rejectedDescription){
		Application application = Objects.requireNonNull(DBManager.getInstance().getDatabase().getAllApplications().stream().
                filter(app -> app.getId() == applicationID).findFirst().orElse(null));
		application.setRejectParams(rejectedDescription);
		DBManager.getInstance().update(application);
	}


	public static ArrayList<Application> getApplicationsFromDepartment(int departmentId){
		return DBManager.getInstance().getDatabase().getAllApplications().stream().filter(a -> a.getDepartmentID() == departmentId).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * @param user
	 */
	public static ArrayList<Application> getApplications(User user) {

		switch (user.getAccessLevel()){
			case CITIZEN -> {
				return DBManager.getInstance()
						.getDatabase()
						.getAllApplications()
						.stream()
						.filter(app -> app.getUserID() == user.getId() && !app.isArchived())
						.collect(Collectors.toCollection(ArrayList::new));
			}
			case OFFICIAL -> {
				return DBManager.getInstance().getDatabase().getAllApplications().stream().filter(app -> app.getDepartmentID() == user.getDepartmentID()).collect(Collectors.toCollection(ArrayList::new));
			}
			default -> {
				return new ArrayList<>();
			}
		}
	}
}