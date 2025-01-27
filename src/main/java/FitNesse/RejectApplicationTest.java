package FitNesse;

import Model.Classes.Application;
import Model.Classes.DBManager;
import Model.Facades.exposeApplications;
import fit.ColumnFixture;

import java.util.Objects;

public class RejectApplicationTest extends ColumnFixture {

    public int applicationID;
    public String rejectedDescription;

    public boolean rejectApplication() {
        try {
            exposeApplications.rejectApplication(applicationID, rejectedDescription);

            Application application = getApplication();

            if(application == null) return false;


            boolean isArchivedStatusCorrect = application.isArchived();
            boolean isDescriptionCorrect = rejectedDescription.equals(application.getRejectedDescription());
            boolean isConsiderationDateSet = application.getConsiderationDate() != null;

            return isArchivedStatusCorrect && isDescriptionCorrect && isConsiderationDateSet;

        } catch (NullPointerException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isArchivedStatusCorrect() {
        Application application = getApplication();
        if(application != null) return application.isArchived();
        else return false;
    }

    public boolean isDescriptionCorrect() {
        Application application = getApplication();
        if(application != null) return Objects.equals(rejectedDescription, application.getRejectedDescription());
        else return false;
    }

    public boolean isConsiderationDateSet() {
        Application application = getApplication();
        if(application != null) return application.getConsiderationDate() != null;
        else return false;
    }

    private Application getApplication() {
        return  DBManager.getInstance().getDatabase().getAllApplications().stream()
                .filter(a -> a.getId() == applicationID)
                .findFirst()
                .orElse(null);
    }
}