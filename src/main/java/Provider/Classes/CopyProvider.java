package Provider.Classes;

import Model.Classes.User;
import Model.Enums.CopyType;
import Model.Classes.Department;
import Provider.Interfaces.listDepartments;
import Provider.Interfaces.makeCopy;
import View.Classes.CopyView;
import java.util.ArrayList;

public class CopyProvider extends Provider implements makeCopy, listDepartments {

	Department selectedDepartment;

	public CopyProvider(User loggedUser) {
		super(loggedUser);
	}

	/**
	 * 
	 * @param departmentIds
	 */
	public CopyView createView(ArrayList<Integer> departmentIds) {
		// TODO - implement Provider.Classes.Provider.Classes.CopyProvider.createView
		throw new UnsupportedOperationException();
	}

	@Override
	public ArrayList<Department> getAssignedDepartments(int userID) {
		return null;
	}

	@Override
	public void startCreateCopy(int userID, int selectedDepartmentId, CopyType selectedCopyType) {

	}
}