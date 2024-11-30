package Model.Interfaces;

import Model.Classes.Application;
import Model.Classes.DBManager;
import Model.Enums.ApplicationStatus;
import Model.Classes.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

public interface exposeApplications {

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
	ApplicationStatus getApplicationStatus(int applicationID);

	/**
	 * 
	 * @param applicationID
	 */
	void acceptApplication(int applicationID);

	/**
	 * 
	 * @param applicationID
	 */
	void rejectApplication(int applicationID, String rejectedDescription);

	/**
	 * @param user
	 */
	static ArrayList<Application> getApplications(User user) {


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