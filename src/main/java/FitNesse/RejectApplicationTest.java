package FitNesse;

import Model.Classes.Application;
import Model.Classes.DBManager;
import Model.Facades.exposeApplications;
import fit.ColumnFixture;

public class RejectApplicationTest extends ColumnFixture {

    public int applicationID;
    public String rejectedDescription;

    public boolean rejectApplication() {
        try {
            exposeApplications.rejectApplication(applicationID, rejectedDescription);

            Application application = DBManager.getInstance().getDatabase().getAllApplications().stream()
                    .filter(a -> a.getId() == applicationID)
                    .findFirst()
                    .orElse(null);

            if(application == null) return false;

            boolean isArchivedStatusCorrect = application.isArchived();
            boolean isDescriptionCorrect = rejectedDescription.equals(application.getRejectedDescription());
            boolean isConsiderationDateSet = application.getConsiderationDate() != null;

            return isArchivedStatusCorrect&& isDescriptionCorrect && isConsiderationDateSet;

        } catch (NullPointerException | IllegalArgumentException e) {
            return false;
        }
    }
}
