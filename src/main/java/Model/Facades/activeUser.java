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

		if(password.equals(user.getPassword())) {
			user.setIsActive(true);
			return user;
		}
		else throw new RuntimeException("Bledne haslo");
	}

	static boolean logout(int userId) {
		DBManager.getInstance().getDatabase().getAllUsers()
				.stream()
				.filter(u -> u.getId() == userId)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Bledny ID"))
				.setIsActive(false);
		System.out.println("\n\nNastapilo wylogowanie");
		return true;
	}

}