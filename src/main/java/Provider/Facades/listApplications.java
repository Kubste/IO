package Provider.Facades;

import java.util.ArrayList;

public interface listApplications {

	/**
	 * 
	 * @param userID
	 */
	ArrayList<Integer> getAssignedApplications(int userID);

	/**
	 * 
	 * @param applicationId
	 */
	String getApplicationDetails(int applicationId);

}