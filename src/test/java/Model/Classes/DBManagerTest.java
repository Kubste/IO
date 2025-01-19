package Model.Classes;

import Model.Data;
import Model.Enums.AccessLevel;
import Model.Enums.ApplicationStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;
import java.util.stream.Stream;
import java.util.logging.Level;
import java.util.logging.Logger;

@ExtendWith(DBManagerTest.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("Classes")
@Tag("DBManager")
public class DBManagerTest implements TestExecutionExceptionHandler {

    private static DBManager dbManager;
    private static Database mockDatabase;
    private static ArrayList<Application> applications;
    private static ArrayList<User> users;
    private static ArrayList<Department> departments;
    private static ArrayList<Copy> copies;
    private static final Logger LOGGER = Logger.getLogger(DBManagerTest.class.getName());

    @BeforeAll
    static void setupAll(){
        dbManager = DBManager.getInstance();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(consoleHandler);
    }

    @BeforeEach
    void setupEach() {
        applications = Data.getSampleApplications();
        users = Data.getSampleUsers();
        departments = Data.getSampleDepartments();
        copies = Data.getSampleCopies();
        mockDatabase = new Database();
        dbManager.setDatabase(mockDatabase);

        for(int i = 0; i < 3; i++) {
            dbManager.save(applications.get(i));
            dbManager.save(users.get(i));
            dbManager.save(departments.get(i));
            dbManager.save(copies.get(i));
        }
    }

    private static class TestModel extends Model {}

    static Stream<Integer> indexProvider() {
        return Stream.of(0, 1, 2); }

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testApplicationSave(int applicationIndex){
        Application app = applications.get(applicationIndex);
        dbManager.save(app);
        assertTrue(mockDatabase.getAllApplications().contains(app));
    }

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testDepartmentSave(int departmentIndex){
        Department dep = departments.get(departmentIndex);
        dbManager.save(dep);
        assertTrue(mockDatabase.getAllDepartments().contains(dep));
    }

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testCopySave(int copyIndex){
        Copy copy = copies.get(copyIndex);
        dbManager.save(copy);
        assertTrue(mockDatabase.getAllCopies().contains(copy));
    }


    @ParameterizedTest()
    @CsvSource({"0", "1", "2"})
    void testUserSave(int userIndex){
        User user = users.get(userIndex);
        dbManager.save(user);
        assertTrue(mockDatabase.getAllUsers().contains(user));
    }

    @Test
    void testExceptionSave(){
        assertThrows(NullPointerException.class, () -> dbManager.save(null));
        assertThrows(ClassCastException.class, () -> dbManager.save((Model) new Object()));
        assertThrows(IllegalArgumentException.class, () -> dbManager.save(new TestModel()));
    }

    @Test
    void testExceptionUpdate(){
        assertThrows(NullPointerException.class, () -> dbManager.update(null));
        assertThrows(ClassCastException.class, () -> dbManager.update((Model) new Object()));
        assertThrows(IllegalArgumentException.class, () -> dbManager.update(new TestModel()));
    }

    @Order(1)
    @Test
    void testProperSetUpEach() {
        for(int i = 0; i < 3; i++) {
            assertTrue(mockDatabase.getAllApplications().contains(applications.get(i)));
            assertTrue(mockDatabase.getAllUsers().contains(users.get(i)));
            assertTrue(mockDatabase.getAllDepartments().contains(departments.get(i)));
            assertTrue(mockDatabase.getAllCopies().contains(copies.get(i)));
        }
    }

    @ParameterizedTest()
    @CsvSource({"0", "1", "2"})
    void testApplicationUpdate(int applicationIndex){
        Application application = applications.get(applicationIndex);

        application.setRejectedDescription("Rejected Description");
        assertTrue(dbManager.update(application));
        assertEquals("Rejected Description", mockDatabase.getAllApplications().get(applicationIndex).getRejectedDescription());

        application.setArchived(true);
        assertTrue(dbManager.update(application));
        assertTrue(mockDatabase.getAllApplications().get(applicationIndex).isArchived());

        application.setStatus(ApplicationStatus.ACCEPTED);
        assertTrue(dbManager.update(application));
        assertEquals(ApplicationStatus.ACCEPTED, mockDatabase.getAllApplications().get(applicationIndex).getStatus());
    }

    @ParameterizedTest()
    @CsvSource({"0", "1", "2"})
    void testDepartmentUpdate(int applicationIndex){
        Application application = applications.get(applicationIndex);

        application.setRejectedDescription("Rejected Description");
        assertTrue(dbManager.update(application));
        assertEquals("Rejected Description", mockDatabase.getAllApplications().get(applicationIndex).getRejectedDescription());

        application.setArchived(true);
        assertTrue(dbManager.update(application));
        assertTrue(mockDatabase.getAllApplications().get(applicationIndex).isArchived());

        application.setStatus(ApplicationStatus.ACCEPTED);
        assertTrue(dbManager.update(application));
        assertEquals(ApplicationStatus.ACCEPTED, mockDatabase.getAllApplications().get(applicationIndex).getStatus());
    }

    @ParameterizedTest()
    @CsvSource({"0", "1", "2"})
    void testUserUpdate(int userIndex){
        User user = users.get(userIndex);

        user.setEmail("test@wp.pl");
        assertTrue(dbManager.update(user));
        assertEquals("test@wp.pl", mockDatabase.getAllUsers().get(userIndex).getEmail());

        user.setAccessLevel(AccessLevel.ADMIN);
        assertTrue(dbManager.update(user));
        assertEquals(AccessLevel.ADMIN, mockDatabase.getAllUsers().get(userIndex).getAccessLevel());

        boolean previousActiveStatus = user.isActive();
        user.setIsActive(!previousActiveStatus);
        assertTrue(dbManager.update(user));
        assertEquals(!previousActiveStatus, mockDatabase.getAllUsers().get(userIndex).isActive());
    }

    @ParameterizedTest()
    @CsvSource({"0", "1", "2"})
    void testCopyUpdate(int copyIndex){
        Copy copy = copies.get(copyIndex);

        copy.setSize(99999);
        assertTrue(dbManager.update(copy));
        assertEquals(99999, mockDatabase.getAllCopies().get(copyIndex).getSize());

        copy.setDepartmentID(99);
        assertTrue(dbManager.update(copy));
        assertEquals(99, mockDatabase.getAllCopies().get(copyIndex).getDepartmentID());
    }

    @Tag("exceptionHandlingTest")
    @Test
    void handleTestExecutionExceptionTest() {
        throw new UnsupportedOperationException();
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