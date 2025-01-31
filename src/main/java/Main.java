import FitNesse.RejectApplicationProviderTest;
import FitNesse.SetUp;
import Model.Classes.*;
import Model.Enums.AccessLevel;
import Model.Facades.activeUser;
import Provider.Classes.ApplicationsProvider;
import Provider.Classes.CopyProvider;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;
import FitNesse.SetUp;
import FitNesse.RejectApplicationProviderTest;

public class Main {

    public static void main(String[] args) throws Exception {

        /*SetUp setUp = new SetUp();
        RejectApplicationProviderTest rejectApplicationProviderTest = new RejectApplicationProviderTest();
        rejectApplicationProviderTest.rejectApplicationProvider();*/

        Faker faker = new Faker();
        Random random = new Random();

        ArrayList<User> mockedUsers = new ArrayList<>();
        ArrayList<Department> mockedDepartments = new ArrayList<>();
        ArrayList<Application> mockedApplications = new ArrayList<>();

        ArrayList<AccessLevel> accessLevels = new ArrayList<>();
        accessLevels.add(AccessLevel.ADMIN);
        accessLevels.add(AccessLevel.OFFICIAL);
        accessLevels.add(AccessLevel.CITIZEN);

        for(int i = 0; i < 100; i++){
            mockedUsers.add(new User(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    accessLevels.get(random.nextInt(0,accessLevels.size())),
                    i + "test@wp.pl",
                    i + "password",
                    faker.funnyName().toString(),
                    -1
            ));
        }

        ArrayList<User> mockedOfficials = mockedUsers.stream()
                .filter(user -> user.getAccessLevel() == AccessLevel.OFFICIAL).distinct().collect(Collectors.toCollection(ArrayList::new));

        ArrayList<User> mockedCitizens = mockedUsers.stream()
                .filter(user -> user.getAccessLevel() == AccessLevel.CITIZEN).distinct().collect(Collectors.toCollection(ArrayList::new));

        ArrayList<User> mockedAdmins = mockedUsers.stream()
                .filter(user -> user.getAccessLevel() == AccessLevel.ADMIN).distinct().collect(Collectors.toCollection(ArrayList::new));

        for(int i = 1; i < 11; i++){
            Collections.shuffle(mockedUsers);
            Collections.shuffle(mockedOfficials);
            Collections.shuffle(mockedCitizens);

            mockedDepartments.add(new Department(
                    "Department"+i,
                faker.address().fullAddress(),
                mockedUsers.stream().filter(user -> user.getAccessLevel() == AccessLevel.ADMIN).findFirst().get().getID(),
                    new ArrayList<>(mockedOfficials.subList(0, random.nextInt(mockedOfficials.size()))),
                    new ArrayList<>(mockedCitizens.subList(0, random.nextInt(mockedCitizens.size())))
            ));
        }

        HashSet<Integer> adminAssignedDepartments = new HashSet<>();
        for(User admin : mockedAdmins) {
            int numOfDepartments = random.nextInt(mockedDepartments.size() - 1) + 1;
            for(int i = 0; i < numOfDepartments; i++) {
                int index = random.nextInt(mockedDepartments.size());
                adminAssignedDepartments.add(mockedDepartments.get(index).getId());
            }
            if(adminAssignedDepartments.isEmpty()) adminAssignedDepartments.add(mockedDepartments.get(0).getId());
            admin.setAssignedDepartmentsIDs(new HashSet<>(adminAssignedDepartments));
            adminAssignedDepartments.clear();
        }

        for(User user: mockedUsers){
            user.setDepartmentID(
                    mockedDepartments.get(random.nextInt(mockedDepartments.size())).getId()
            );
        }

        for(int i = 0; i < 200; i++){
            mockedApplications.add(new Application(
                    mockedCitizens.get(random.nextInt(mockedCitizens.size())).getId(),
                    mockedDepartments.get(random.nextInt(mockedDepartments.size())).getId(),
                    faker.shakespeare().romeoAndJulietQuote()
            ));
        }

        Database database = new Database();
        database.setAllUsers(mockedUsers);
        database.setAllDepartments(mockedDepartments);
        database.setAllApplications(mockedApplications);
        DBManager.getInstance().setDatabase(database);

        User userToLogIn = mockedOfficials.get(random.nextInt(mockedOfficials.size()));
        activeUser.login(userToLogIn.getEmail(), userToLogIn.getPassword());
        User loggedUser = mockedOfficials.get(random.nextInt(mockedOfficials.size()));
        activeUser.login(loggedUser.getEmail(), loggedUser.getPassword());

        ApplicationsProvider applicationsProvider = new ApplicationsProvider(userToLogIn);
        applicationsProvider.createView();

        activeUser.logout(userToLogIn.getId());

        //loggedUser = new User(faker.name().firstName(), faker.name().lastName(), AccessLevel.ADMIN, "admin@wp.pl", "adminpassword", "admin", -1);
        userToLogIn = mockedAdmins.get(random.nextInt(mockedOfficials.size()));
        activeUser.login(userToLogIn.getEmail(), userToLogIn.getPassword());
        //loggedUser.setAssignedDepartmentsIDs(adminAssignedDepartments);
        CopyProvider copyProvider = new CopyProvider(userToLogIn);
        copyProvider.createView();

        activeUser.logout(userToLogIn.getId());
    }
}