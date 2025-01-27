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
	boolean rejectApplication(int id, String rejectDescription) throws IllegalAccessException;

}