package Provider.Classes;

import Model.Classes.Application;
import Model.Classes.User;
import Model.Enums.AccessLevel;
import Provider.Facades.listApplications;
import Provider.Facades.manageApplications;
import View.Classes.ApplicationsView;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import Model.Facades.exposeApplications;

public class ApplicationsProvider extends Provider implements manageApplications, listApplications {

	private static final Logger logger = Logger.getLogger(ApplicationsProvider.class.getName());
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
	public boolean rejectApplication(int id, String rejectDescription) throws IllegalAccessException {
		Optional<Application> application = this.assignedApplications.stream()
				.filter(app -> app.getId() == id)
				.findFirst();

		try {
			if(loggedUser.getAccessLevel() != AccessLevel.OFFICIAL) throw new IllegalAccessException();
			exposeApplications.rejectApplication(id, rejectDescription);
			this.sendMail(this.getApplicant(id), "Wniosek odrzucony, uzasadnienie odrzucenia: " + rejectDescription);
		} catch(Exception ex) {
			logger.info("\nOdrzucenie wniosku zakonczone niepowodzeniem\nNumer ID wniosku: " + id + " Numer ID urzednika: " + this.loggedUser.getId() + " Data: " + LocalDateTime.now());
			throw ex;
		}

		logger.info("\nOdrzucenie wniosku zakonczone powodzeniem\nNumer ID wniosku: " + id + " Numer ID urzednika: " + this.loggedUser.getId() + " Data: " + LocalDateTime.now());
		return true;
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

	public AccessLevel getLoggedUserAccessLevel() {
		return loggedUser.getAccessLevel();
	}

	public void setLoggedUserAccess(AccessLevel accessLevel) {
		this.loggedUser.setAccessLevel(accessLevel);
	}
}