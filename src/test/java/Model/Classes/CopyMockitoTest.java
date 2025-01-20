package Model.Classes;

import Model.Data;
import Model.Enums.CopyType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.stream.Stream;
import java.util.logging.Logger;

@ExtendWith(DBManagerTest.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("Classes")
@Tag("CopyMockito")
public class CopyMockitoTest implements TestExecutionExceptionHandler {

    private static DBManager dbManager;
    private static Database mockDatabase;
    private static ArrayList<Copy> copies;
    private static ArrayList<Application> applications;
    private static ArrayList<User> users;
    private ArrayList<Department> departments;
    private static LocalDateTime testTime;
    private static final Logger LOGGER = Logger.getLogger(CopyMockitoTest.class.getName());

    @BeforeAll
    static void setupAll(){
        dbManager = DBManager.getInstance();
        applications = Data.getSampleApplications();
        users = Data.getSampleUsers();
        testTime = LocalDateTime.of(2025, 1, 1, 12, 0);
    }

    @BeforeEach
    void setupEach() {
        copies = Data.getSampleCopies();
        mockDatabase = new Database();
        dbManager.setDatabase(mockDatabase);
        departments = Data.getSampleDepartments();

        Model.resetIdCounters();
        for(int i = 0; i < 3; i++) {
            dbManager.save(departments.get(i));
        }
        for(int i = 0; i < 3; i++) {
            dbManager.save(copies.get(i));
        }
        for(int i = 0; i < 3; i++) {
            dbManager.save(applications.get(i));
        }
        for(int i = 0; i < 3; i++) {
            dbManager.save(users.get(i));
        }
    }

    static Stream<Integer> indexProvider() {return Stream.of(0, 1, 2);}

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testProperSaveCopy(int departmentIndex){
        Copy newCopy = Mockito.spy(new Copy(1000, departmentIndex,"C:\\User\\copies\\copy1.txt", CopyType.FULL));

        Mockito.doReturn(testTime).when(newCopy).getCurrentTime();

        newCopy.save();
        assertEquals(testTime, newCopy.getCreated_at());
        assertTrue(mockDatabase.getAllCopies().contains(newCopy));

        int max_id = mockDatabase.getAllCopies().stream().map(Copy::getId).max(Comparator.naturalOrder()).get().intValue();
        assertEquals(max_id, newCopy.getId());
    }

    @ParameterizedTest
    @MethodSource("indexProvider")
    void testProperCopyUpdate(int copyIndex) {
        Copy copy = mockDatabase.getAllCopies().get(copyIndex);
        copy.setSize(500);

        Copy spyCopy = Mockito.spy(copy);
        Mockito.doReturn(testTime).when(spyCopy).getUpdated_at();

        spyCopy.update();

        assertEquals(testTime, spyCopy.getUpdated_at());
        assertEquals(500, mockDatabase.getAllCopies().get(copyIndex).getSize());
        assertTrue(mockDatabase.getAllCopies().contains(copy));
    }

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testProperApplicationUpdate(int applicationIndex) {
        Application application = mockDatabase.getAllApplications().get(applicationIndex);

        assertFalse(application.isArchived());
        application.setAcceptParams();
        application.setRejectedDescription("Rejected");

        Application spyApplication = Mockito.spy(application);
        Mockito.doReturn(testTime).when(spyApplication).getUpdated_at();

        spyApplication.update();

        assertEquals(testTime, spyApplication.getUpdated_at());
        assertEquals("Rejected", mockDatabase.getAllApplications().get(applicationIndex).getRejectedDescription());
        assertTrue(mockDatabase.getAllApplications().get(applicationIndex).isArchived());
        assertTrue(mockDatabase.getAllApplications().contains(application));
    }

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testProperUserUpdate(int userIndex) {
        User user = mockDatabase.getAllUsers().get(userIndex);
        user.setUsername("testUser");

        User spyUser = Mockito.spy(user);
        Mockito.doReturn(testTime).when(spyUser).getUpdated_at();

        spyUser.update();

        assertEquals(testTime, spyUser.getUpdated_at());
        assertEquals("testUser", mockDatabase.getAllUsers().get(userIndex).getUsername());
        assertTrue(mockDatabase.getAllUsers().contains(user));
    }

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testProperDepartmentUpdate(int departmentIndex) {
        Department department = mockDatabase.getAllDepartments().get(departmentIndex);
        department.setName("testDepartment");

        Department spyDepartment = Mockito.spy(department);
        Mockito.doReturn(testTime).when(spyDepartment).getUpdated_at();

        spyDepartment.update();

        assertEquals(testTime, spyDepartment.getUpdated_at());
        assertEquals("testDepartment", mockDatabase.getAllDepartments().get(departmentIndex).getName());
        assertTrue(mockDatabase.getAllDepartments().contains(department));
    }

    @Order(1)
    @Test
    void testProperSetUpEach() {
        for(int i = 0; i < 3; i++) {
            assertTrue(mockDatabase.getAllDepartments().contains(departments.get(i)));
            assertTrue(mockDatabase.getAllCopies().contains(copies.get(i)));
        }
    }

    @Tag("exceptionHandlingTest")
    @Test
    void handleTestExecutionExceptionTest() {
        throw new UnsupportedOperationException("Wyjatek testowy");
    }

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        String testMethodName = extensionContext.getTestMethod().get().getName();
        String testClassName = extensionContext.getTestClass().get().getSimpleName();

        LOGGER.log(Level.SEVERE, "Test " + testClassName + "." + testMethodName + " wyrzucil wyjatek: " + throwable.getMessage(), throwable);
        LOGGER.log(Level.INFO, "Szczegoly testu: " + extensionContext.getDisplayName());

        if(throwable instanceof UnsupportedOperationException) {
            LOGGER.log(Level.WARNING, "Nastapilo poprawne wykrycie wyjatku");
        }
        else throw throwable;
    }
}