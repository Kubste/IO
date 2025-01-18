import Model.Classes.*;
import Model.Enums.AccessLevel;
import Model.Enums.CopyType;
import Model.Facades.manageCopies;
import Model.Facades.manageDepartments;
import Model.Facades.exposeApplications;
import Provider.Facades.makeCopy;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@Tag("Facades")
public class MakeCopyFacadeTest {

    private manageDepartments mockManageDepartments;
    private manageCopies mockManageCopies;
    private exposeApplications mockExposeApplications;

    private static MockedStatic<manageDepartments> mockedManageDepartments;
    private static MockedStatic<makeCopy> mockedMakeCopy;

    private static DBManager dbManager;
    private static Database mockDatabase;
    private static ArrayList<User> users;
    private static ArrayList<Copy> copies;
    private ArrayList<Department> departments;

    private static class MakeCopyConcrete extends makeCopy {}


    @BeforeAll
    static void setupAll() {
        dbManager = DBManager.getInstance();

        mockedManageDepartments = Mockito.mockStatic(manageDepartments.class);
        mockedMakeCopy = Mockito.mockStatic(makeCopy.class);
    }

    @BeforeEach
    void setupEach() {
        mockedManageDepartments.reset();
        mockedMakeCopy.reset();

        Model.resetIdCounters();
        copies = Data.getSampleCopies();
        mockDatabase = new Database();
        dbManager.setDatabase(mockDatabase);
        departments = Data.getSampleDepartments();
        users = Data.getSampleUsers();

        Model.resetIdCounters();
        for (int i = 0; i < 3; i++) {
            dbManager.save(departments.get(i));
        }
        for (int i = 0; i < 3; i++) {
            dbManager.save(copies.get(i));
        }
        for (int i = 0; i < 3; i++) {
            dbManager.save(users.get(i));
        }
    }

    @AfterEach
    void tearDownEach() {
        mockedManageDepartments.reset();
        mockedMakeCopy.reset();
    }

    @AfterAll
    static void tearDownAll() {
        mockedManageDepartments.close();
        mockedMakeCopy.close();
    }

    @Test
    void testCheckFreeSpace() {
        mockedMakeCopy.when(makeCopy::checkFreeSpace).thenCallRealMethod();
        boolean isFreeSpace = makeCopy.checkFreeSpace();
        assertTrue(isFreeSpace, "The disk should have sufficient space.");
    }

    @Test
    void testSendMail() {
        int userID = 1;
        String message = "Test Message";


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try(MockedStatic<MakeCopyFacadeTest.MakeCopyConcrete> newMock  = Mockito.mockStatic(MakeCopyConcrete.class)){
            newMock.when(() -> makeCopy.sendMail(userID, message)).thenCallRealMethod();
            try {
                MakeCopyConcrete.sendMail(userID, message);
                String output = outputStream.toString();
                assertTrue(output.contains(message), "The message should be printed to System.out.");
            } finally {
                System.setOut(originalSystemOut);
            }

        }

    }

    @Test
    @Order(1)
    void noAssignedDepartmentsTest() throws Exception {
        int userId = 1;
        int selectedDepartmentId = 3;
        CopyType selectedCopyType = CopyType.FULL;

        mockedManageDepartments.when(() -> manageDepartments.getAssignedDepartments(userId))
                .thenReturn(new ArrayList<>());

        mockedMakeCopy.when(() -> makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType))
                .thenCallRealMethod();

        Exception exception = assertThrows(Exception.class, () -> {
            makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType);
        });

        assertEquals("Provided department is not assigned to this user!", exception.getMessage());
    }

    @Test
    @Order(2)
    void noFreeSpaceTest() throws Exception {
        int userId = 1;
        int selectedDepartmentId = 3;
        CopyType selectedCopyType = CopyType.FULL;

        ArrayList<Department> assignedDepartments = new ArrayList<>();
        assignedDepartments.add(new Department("test", "addr", 1, new ArrayList<>(), new ArrayList<>()));

        mockedManageDepartments.when(() -> manageDepartments.getAssignedDepartments(userId))
                .thenReturn(assignedDepartments);

        mockedMakeCopy.when(makeCopy::checkFreeSpace).thenReturn(false);
        mockedMakeCopy.when(() -> makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType))
                .thenCallRealMethod();

        Exception exception = assertThrows(Exception.class, () -> {
            makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType);
        });

        assertEquals("No free space left", exception.getMessage());
    }

    @Test
    @Order(3)
    void testStartCreateCopyFullSuccess() throws Exception {
        int userId = 1;
        int selectedDepartmentId = 3;
        CopyType selectedCopyType = CopyType.FULL;
        Department department = new Department("Dept1", "Addr1", 1, new ArrayList<>(), new ArrayList<>());
        ArrayList<Department> assignedDepartments = new ArrayList<>();
        assignedDepartments.add(department);

        mockedManageDepartments.when(() -> manageDepartments.getAssignedDepartments(userId))
                .thenReturn(assignedDepartments);

        ArrayList<User> citizens = new ArrayList<>();
        citizens.add(new User("citizen", "11", AccessLevel.CITIZEN, "1@wp.pl", "password", "user1", 3));
        ArrayList<User> officials = new ArrayList<>();
        officials.add(new User("official", "11", AccessLevel.OFFICIAL, "1@wp.pl", "password", "user2", 3));
        ArrayList<Application> applications = new ArrayList<>();
        applications.add(new Application(2, 3, "description"));

        mockedManageDepartments.when(() -> manageDepartments.getAllCitizens(selectedDepartmentId))
                .thenReturn(citizens);
        mockedManageDepartments.when(() -> manageDepartments.getAllOfficials(selectedDepartmentId))
                .thenReturn(officials);

        MockedStatic<exposeApplications> mockedExposeApplications = Mockito.mockStatic(exposeApplications.class);
        mockedExposeApplications.when(() -> exposeApplications.getApplicationsFromDepartment(selectedDepartmentId))
                .thenReturn(applications);

        mockedMakeCopy.when(makeCopy::checkFreeSpace).thenReturn(true);
        mockedMakeCopy.when(() -> makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType))
                .thenCallRealMethod();

        boolean result = makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType);
        assertTrue(result, "The method should return true on successful copy creation.");

        mockedExposeApplications.close();
    }
}
