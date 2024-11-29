package Model.Interfaces;

import Model.Classes.Application;
import Model.Enums.ApplicationStatus;
import Model.Classes.User;

import java.util.ArrayList;

public interface exposeApplications {

	/**
	 * 
	 * @param applicationID
	 */
	int getUserID(int applicationID);

	/**
	 * 
	 * @param applicationID
	 */
	int getDepartmentID(int applicationID);

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
	 * 
	 * @param user
	 */
	ArrayList<Application> getApplications(User user);

}