package Provider.Classes;

import Model.Classes.Application;
import Model.Classes.User;
import Provider.Facades.listApplications;
import Provider.Facades.manageApplications;
import View.Classes.ApplicationsView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import Model.Facades.exposeApplications;

public class ApplicationsProvider extends Provider implements manageApplications, listApplications {


	private final ArrayList<Application> assignedApplications;

	public ApplicationsProvider(User loggedUser) {
		super(loggedUser);
		this.assignedApplications = exposeApplications.getApplications(loggedUser);
		this.supportedViews = new ArrayList<>(List.of(ApplicationsView.class));
	}

	@Override
	public void createView() throws Exception {
		ViewBuilder.createView(ApplicationsView.class, assignedApplications.stream().map(Application::getId), this);
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

        return application.map(Application::getDescription).orElse(null);

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

		exposeApplications.acceptApplication(id);
	}

	@Override
	public void rejectApplication(int id, String rejectDescription) {
		Optional<Application> application = this.assignedApplications.stream()
				.filter(app -> app.getId() == id)
				.findFirst();

		exposeApplications.rejectApplication(id, rejectDescription);
	}

	public int getApplicant(int applicationId) {
		Optional<Application> application = this.assignedApplications.stream()
				.filter(app -> app.getId() == applicationId)
				.findFirst();

        return application.map(Application::getUserID).orElse(-1);
	}

	public void sendMail(int receiver, String message) {
		System.out.println("Wiadomosc: \"" + message + "\" zostala wyslana do uzytkownika o numerze ID: " + receiver);
	}

	public boolean checkApplicationArchivedStatus(int id) {
		Optional<Application> application = this.assignedApplications.stream()
				.filter(app -> app.getId() == id)
				.findFirst();

        return application.filter(value -> !value.isArchived()).isPresent();
    }
}