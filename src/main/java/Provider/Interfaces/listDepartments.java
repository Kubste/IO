package Provider.Interfaces;

import Model.Classes.Department;
import java.util.ArrayList;

public interface listDepartments {

	/**
	 * 
	 * @param userID
	 */
	ArrayList<Department> getAssignedDepartments(int userID);

}