package Provider.Facades;

public interface manageApplications {

	/**
	 * 
	 * @param applicationID
	 */
	boolean deleteApplication(int applicationID);

	/**
	 * 
	 * @param id
	 */
	void acceptApplication(int id);

	/**
	 * 
	 * @param id
	 * @param rejectDescription
	 */
	void rejectApplication(int id, String rejectDescription);

}