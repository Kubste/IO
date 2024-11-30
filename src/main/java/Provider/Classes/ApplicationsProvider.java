package Provider.Classes;

import Model.Classes.Application;
import Model.Classes.Department;
import Model.Classes.User;
import Provider.Interfaces.listApplications;
import Provider.Interfaces.manageApplications;
import View.Classes.ApplicationsView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import Model.Interfaces.exposeApplications;

public class ApplicationsProvider extends Provider implements manageApplications, listApplications {


	private ArrayList<Application> assignedApplications;
	public ApplicationsProvider(User loggedUser) {
		super(loggedUser);
		this.assignedApplications = exposeApplications.getApplications(loggedUser);
		this.supportedViews = new ArrayList<>(List.of(ApplicationsView.class));
	}

	public void createView() {
		ApplicationsView applicationsView = new ApplicationsView(assignedApplications.stream().map(Application::getDepartmentID).collect(Collectors.toCollection(ArrayList::new)), this);
		this.checkView(applicationsView);
		applicationsView.render();

	}

	@Override
	public ArrayList<Integer> getAssignedApplications(int userID) {
		return null;
	}

	@Override
	public String getApplicationDetails(int applicationId) {
		return null;
	}

	@Override
	public boolean deleteApplication(int applicationID) {
		return false;
	}

	@Override
	public void acceptApplication(int id) {

	}

	@Override
	public void rejectApplication(int id, String rejectDescription) {

	}
}