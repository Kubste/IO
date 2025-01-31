package Model.Classes;

import Model.Data;
import Model.Enums.CopyType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DBManagerTest.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("Classes")
@Tag("Copy")
public class CopyTest implements TestExecutionExceptionHandler {

    private static DBManager dbManager;
    private static Database mockDatabase;
    private static ArrayList<Copy> copies;
    private static ArrayList<Application> applications;
    private static ArrayList<User> users;
    private ArrayList<Department> departments;
    private static LocalDateTime testTime;
    private static final Logger LOGGER = Logger.getLogger(CopyTest.class.getName());

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
        Copy copy = new Copy(1000, departmentIndex, "C:\\User\\copies\\copy1.txt", CopyType.FULL);

        testTime = LocalDateTime.now();
        copy.save();
        assertTrue(Duration.between(testTime, copy.getUpdated_at()).toMillis() < 10);
        assertTrue(mockDatabase.getAllCopies().contains(copy));

        int max_id = mockDatabase.getAllCopies().stream().map(Copy::getId).max(Comparator.naturalOrder()).get().intValue();
        assertEquals(max_id, copy.getId());
    }

    @ParameterizedTest
    @MethodSource("indexProvider")
    void testProperCopyUpdate(int copyIndex) {
        Copy copy = mockDatabase.getAllCopies().get(copyIndex);
        copy.setSize(500);

        testTime = LocalDateTime.now();
        copy.update();
        assertTrue(Duration.between(testTime, copy.getUpdated_at()).toMillis() < 10);
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

        testTime = LocalDateTime.now();
        application.update();

        assertTrue(Duration.between(testTime, application.getUpdated_at()).toMillis() < 10);
        assertEquals("Rejected", mockDatabase.getAllApplications().get(applicationIndex).getRejectedDescription());
        assertTrue(mockDatabase.getAllApplications().get(applicationIndex).isArchived());
        assertTrue(mockDatabase.getAllApplications().contains(application));
    }

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testProperUserUpdate(int userIndex) {
        User user = mockDatabase.getAllUsers().get(userIndex);
        user.setUsername("testUser");

        testTime = LocalDateTime.now();
        user.update();

        assertTrue(Duration.between(testTime, user.getUpdated_at()).toMillis() < 10);
        assertEquals("testUser", mockDatabase.getAllUsers().get(userIndex).getUsername());
        assertTrue(mockDatabase.getAllUsers().contains(user));
    }

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testProperDepartmentUpdate(int departmentIndex) {
        Department department = mockDatabase.getAllDepartments().get(departmentIndex);
        department.setName("testDepartment");

        testTime = LocalDateTime.now();
        department.update();

        assertTrue(Duration.between(testTime, department.getUpdated_at()).toMillis() < 10);
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