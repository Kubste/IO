package Provider.Classes;

import Model.Classes.User;
import Model.Classes.Department;
import Model.Facades.manageDepartments;
import View.Classes.CopyView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CopyProvider extends Provider {

	private final ArrayList<Department> assignedDepartments;

	public CopyProvider(User loggedUser) {
		super(loggedUser);
		this.assignedDepartments = new ArrayList<>(Objects.requireNonNull(manageDepartments.getAssignedDepartments(loggedUser.getId())));
		this.supportedViews = new ArrayList<>(List.of(CopyView.class));
	}

	@Override
	public void createView() {
		ViewBuilder.createView(CopyView.class, assignedDepartments.stream().map(Department::getId), this);
	}


}