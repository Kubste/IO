package Model.Interfaces;

import Model.Classes.DBManager;
import Model.Classes.Department;
import Model.Classes.User;
import java.util.ArrayList;
import java.util.stream.Collectors;

public interface getDepartments {

	/**
	 * 
	 * @param user
	 */
	public static ArrayList<Department> getAssignedDepartments(User user) {
		if(user.isPrivileged()) {
			return DBManager.getInstance()
					.getDatabase()
					.getAllDepartments()
					.stream()
					.filter(dep -> user.getAssignedDepartmentsIDs().contains(dep.getId()))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return null;
	}
}