package Model.Facades;

import Model.Classes.DBManager;
import Model.Classes.Department;
import Model.Classes.User;
import Model.Enums.AccessLevel;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class manageDepartments {


	public static ArrayList<Department> getAssignedDepartments(int userId) {

		User user = DBManager.getInstance().getDatabase().getAllUsers().stream().filter(u -> u.getId() == userId).findFirst().get();
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

	public static ArrayList<User> getAllCitizens(int departmentId){
		return DBManager.getInstance().getDatabase().getAllUsers().stream().filter(u -> u.getDepartmentID() == departmentId && u.getAccessLevel() == AccessLevel.CITIZEN).collect(Collectors.toCollection(ArrayList::new));
	}

	public static ArrayList<User> getAllOfficials(int departmentId) {
		return DBManager.getInstance().getDatabase().getAllUsers().stream().filter(u -> u.getDepartmentID() == departmentId && u.getAccessLevel() == AccessLevel.OFFICIAL).collect(Collectors.toCollection(ArrayList::new));
	}

		public static void lockDepartment(int departmentId){

		Department department = DBManager.getInstance().getDatabase().getAllDepartments().stream().filter(d -> d.getId() == departmentId).findFirst().get();
		department.setLocked(true);
		DBManager.getInstance().update(department);
	}

	public static void unlockDepartment(int departmentId){
		Department department = DBManager.getInstance().getDatabase().getAllDepartments().stream().filter(d -> d.getId() == departmentId).findFirst().get();
		department.setLocked(false);
		DBManager.getInstance().update(department);
	}
}