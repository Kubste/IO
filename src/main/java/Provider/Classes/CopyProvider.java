package Provider.Classes;

import Model.Classes.User;
import Model.Enums.CopyType;
import Model.Classes.Department;
import Model.Interfaces.getDepartments;
import Model.Interfaces.manageCopies;
import Provider.Interfaces.listDepartments;
import Provider.Interfaces.makeCopy;
import View.Classes.CopyView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class CopyProvider extends Provider implements makeCopy, listDepartments {

	private final ArrayList<Department> assignedDepartments;

	public CopyProvider(User loggedUser) {
		super(loggedUser);
		this.assignedDepartments = new ArrayList<>(Objects.requireNonNull(getDepartments.getAssignedDepartments(loggedUser)));
		this.supportedViews = new ArrayList<>(List.of(CopyView.class));
	}

	@Override
	public void createView() {
		CopyView copyView = new CopyView(assignedDepartments.stream().map(Department::getId).collect(Collectors.toCollection(ArrayList::new)), this);
		this.checkView(copyView);
		copyView.render();
	}

	@Override
	public ArrayList<Department> getAssignedDepartments(int userID) {
		return this.assignedDepartments;
	}

	@Override
	public void startCreateCopy(int userID, int selectedDepartmentId, CopyType selectedCopyType) {
		File file = new File(UUID.randomUUID().toString() + ".txt");
		manageCopies.addCopy(file, selectedCopyType, selectedDepartmentId);
	}
}