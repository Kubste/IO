package Model.Interfaces;

import Model.Classes.Department;
import Model.Classes.User;
import java.util.ArrayList;

public interface getDepartments {

	/**
	 * 
	 * @param user
	 */
	ArrayList<Department> getAssignedDepartments(User user);

}