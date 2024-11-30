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
import java.util.Optional;
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
		ApplicationsView applicationsView = new ApplicationsView(assignedApplications.stream().map(Application::getId).collect(Collectors.toCollection(ArrayList::new)), this);
		this.checkView(applicationsView);
		applicationsView.render();

	}

	@Override
	public ArrayList<Integer> getAssignedApplications(int userID) {
		return null;
	}

	@Override
	public String getApplicationDetails(int applicationId) {
		Optional<Application> application = this.assignedApplications.stream()
				.filter(app -> app.getId() == applicationId)
				.findFirst();

		if(application.isPresent()) return application.get().getDescription();
		else return null;

	}

	@Override
	public boolean deleteApplication(int applicationID) {
		return false;
	}

	@Override
	public void acceptApplication(int id) {
		Optional<Application> application = this.assignedApplications.stream()
				.filter(app -> app.getId() == id)
				.findFirst();

		if(application.isPresent()) application.get().acceptApplication(id);
	}

	@Override
	public void rejectApplication(int id, String rejectDescription) {
		Optional<Application> application = this.assignedApplications.stream()
				.filter(app -> app.getId() == id)
				.findFirst();

		if(application.isPresent()) application.get().rejectApplication(id, rejectDescription);
	}

	public boolean checkApplicationArchivedStatus(int id) {
		Optional<Application> application = this.assignedApplications.stream()
				.filter(app -> app.getId() == id)
				.findFirst();

		if(application.isPresent()) return !application.get().isArchived();
		return false;
	}
}