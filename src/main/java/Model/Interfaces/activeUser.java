package Model.Interfaces;

public interface activeUser {

	boolean isActive();

	/**
	 * 
	 * @param username
	 * @param password
	 */
	boolean login(String username, String password);

	boolean logout();

}