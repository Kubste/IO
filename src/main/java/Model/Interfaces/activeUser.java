package Model.Interfaces;

import Model.Classes.DBManager;
import Model.Classes.User;

public interface activeUser {

	boolean isActive();

	/**
	 * 
	 * @param username
	 * @param password
	 */
	static User login(String username, String password){
		DBManager.getInstance().getDatabase().getAllUsers().stream()

	}

	boolean logout();

}