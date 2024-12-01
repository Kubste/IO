import Model.Classes.*;
import Model.Enums.AccessLevel;
import Provider.Classes.ApplicationsProvider;
import Provider.Classes.CopyProvider;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

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
                    "test@wp.pl",
                    "password",
                    faker.funnyName().toString(),
                    -1
            ));
        }

        ArrayList<User> mockedOfficials = mockedUsers.stream()
                .filter(user -> user.getAccessLevel() == AccessLevel.OFFICIAL).distinct().collect(Collectors.toCollection(ArrayList::new));

        ArrayList<User> mockedCitizens = mockedUsers.stream()
                .filter(user -> user.getAccessLevel() == AccessLevel.CITIZEN).distinct().collect(Collectors.toCollection(ArrayList::new));

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
        int numOfDepartments = random.nextInt(mockedDepartments.size() - 1) + 1;
        for(int i = 0; i < numOfDepartments; i++) {
            int index = random.nextInt(mockedDepartments.size());
            adminAssignedDepartments.add(mockedDepartments.get(index).getId());
        }
        if(adminAssignedDepartments.isEmpty()) adminAssignedDepartments.add(mockedDepartments.get(0).getId());

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

        // TODO: change that to the activeUser.login
        User loggedUser = mockedOfficials.get(random.nextInt(mockedOfficials.size()));
        loggedUser.login(loggedUser.getEmail(), loggedUser.getPassword());

        ApplicationsProvider applicationsProvider = new ApplicationsProvider(loggedUser);
        applicationsProvider.createView();

        loggedUser.logout();
        loggedUser = null;

        loggedUser = new User(faker.name().firstName(), faker.name().lastName(), AccessLevel.ADMIN, "admin@wp.pl", "adminpassword", "admin", -1);
        loggedUser.setAssignedDepartmentsIDs(adminAssignedDepartments);
        CopyProvider copyProvider = new CopyProvider(loggedUser);
        copyProvider.createView();

        loggedUser.logout();
        loggedUser = null;
    }
}