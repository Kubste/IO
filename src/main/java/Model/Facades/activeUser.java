package Model.Facades;

import Model.Classes.DBManager;
import Model.Classes.User;

public interface activeUser {

	boolean isActive();

	/**
	 * 
	 * @param email
	 * @param password
	 */
	static User login(String email, String password){
		User user = DBManager.getInstance().getDatabase().getAllUsers()
				.stream()
				.filter(u -> u.getEmail().equals(email))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Bledny email"));

		if(password.equals(user.getPassword())) return user;
		else throw new RuntimeException("Bledne haslo");
	}

	static void logout(int userId) {
		System.out.println("\n\nNastapilo wylogowanie");
	}

}