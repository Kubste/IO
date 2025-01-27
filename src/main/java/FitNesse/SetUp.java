package FitNesse;

import Model.Classes.Application;
import Model.Classes.DBManager;
import Model.Classes.Database;
import Model.Classes.User;
import Model.Enums.AccessLevel;
import Provider.Classes.ApplicationsProvider;
import fit.Fixture;
import java.util.ArrayList;

public class SetUp extends Fixture {

    static ApplicationsProvider applicationsProvider;

    public SetUp() {
        DBManager dbManager = DBManager.getInstance();
        dbManager.setDatabase(new Database());

        ArrayList<Application> applications = new ArrayList<>();
        applications.add(new Application(0, 0, "description"));
        applications.add(new Application(1, 1, "description1"));
        applications.add(new Application(2, 2, "description2"));

        dbManager.getDatabase().setAllApplications(applications);

        applicationsProvider = new ApplicationsProvider(new User("John", "Smith", AccessLevel.OFFICIAL, "123@gmail.com",
                "password", "username", 3));
    }
}
