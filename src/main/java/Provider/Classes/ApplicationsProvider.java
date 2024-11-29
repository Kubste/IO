package Provider.Classes;

import Model.Classes.Application;
import Provider.Interfaces.listApplications;
import Provider.Interfaces.manageApplications;
import View.Classes.ApplicationsView;
import java.util.ArrayList;

public class ApplicationsProvider extends Provider implements manageApplications, listApplications {

	/**
	 * 
	 * @param assignedApplicationIds
	 * @param assignedApplications
	 */
	public ApplicationsView createView(ArrayList<Integer> assignedApplicationIds, ArrayList<Application> assignedApplications) {
		// TODO - implement Provider.Classes.Provider.Classes.ApplicationsProvider.createView
		throw new UnsupportedOperationException();
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