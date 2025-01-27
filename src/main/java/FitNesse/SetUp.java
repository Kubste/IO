package FitNesse;

import Model.Classes.Application;
import Model.Classes.DBManager;
import Model.Classes.Database;
import Model.Classes.User;
import Model.Enums.AccessLevel;
import Provider.Classes.ApplicationsProvider;
import Model.Classes.*;
import Model.Enums.AccessLevel;
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


        ArrayList<User> citizens = new ArrayList<>();
        ArrayList<User> officials = new ArrayList<>();
        for(int i =0; i < 5; i++){
            officials.add(new User("Test", "Test", AccessLevel.OFFICIAL,"test@wp.pl","password","username",1));
        }
        for(int i =0; i < 5; i++){
            citizens.add(new User("Test", "Test", AccessLevel.CITIZEN,"test@wp.pl","password","username",1));
        }


        ArrayList<Department> departments = new ArrayList<>();
        departments.add(new Department("1","address1",1,citizens,officials));
        departments.add(new Department("1","address1",3,citizens,officials));
        departments.add(new Department("1","address1",3,citizens,officials));

        dbManager.getDatabase().setAllApplications(applications);

        applicationsProvider = new ApplicationsProvider(new User("John", "Smith", AccessLevel.OFFICIAL, "123@gmail.com",
                "password", "username", 3));
        dbManager.getDatabase().setAllUsers(citizens);
        dbManager.getDatabase().setAllUsers(officials);
        dbManager.getDatabase().setAllDepartments(departments);
    }
}
