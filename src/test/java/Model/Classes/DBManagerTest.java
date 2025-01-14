package Model.Classes;

import Model.Enums.AccessLevel;
import Model.Enums.ApplicationStatus;
import Model.Enums.CopyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class DBManagerTest implements TestExecutionExceptionHandler {

    private DBManager dbManager;
    private Database mockDatabase;
    private ArrayList<Application> applications;
    private ArrayList<User> users;
    private ArrayList<Department> departments;
    private ArrayList<Copy> copies;

    @BeforeEach
    void setUp() {
        applications = Data.getSampleApplications();
        users = Data.getSampleUsers();
        departments = Data.getSampleDepartments();
        copies = Data.getSampleCopies();

        mockDatabase = new Database();
        dbManager = DBManager.getInstance();
        dbManager.setDatabase(mockDatabase);

        for(int i = 0; i < 3; i++) {
            dbManager.save(applications.get(i));
            dbManager.save(users.get(i));
            dbManager.save(departments.get(i));
            dbManager.save(copies.get(i));
        }

    }

    private static class TestModel extends Model {}

    @Test
    void testSave() {
        for(int i = 0; i < 3; i++) {
            assertTrue(mockDatabase.getAllApplications().contains(applications.get(i)));
            assertTrue(mockDatabase.getAllUsers().contains(users.get(i)));
            assertTrue(mockDatabase.getAllDepartments().contains(departments.get(i)));
            assertTrue(mockDatabase.getAllCopies().contains(copies.get(i)));
        }
        assertThrows(NullPointerException.class, () -> dbManager.save(null));
        assertThrows(ClassCastException.class, () -> dbManager.save((Model) new Object()));
        assertThrows(IllegalArgumentException.class, () -> dbManager.save(new TestModel()));
    }

    @Test
    void testUpdate() {
        assertNull(mockDatabase.getAllApplications().getFirst().getRejectedDescription());
        Application application = applications.getFirst();
        application.setRejectedDescription("Rejected Description");
        assertTrue(dbManager.update(application));
        assertEquals("Rejected Description", mockDatabase.getAllApplications().getFirst().getRejectedDescription());
        application = new Application(99, 99, "description");
        assertFalse(dbManager.update(application));

        assertFalse(mockDatabase.getAllUsers().getFirst().isActive());
        User user = users.getFirst();
        user.setIsActive(true);
        assertTrue(dbManager.update(user));
        assertTrue(mockDatabase.getAllUsers().getFirst().isActive());
        user = new User("First Name", "Last Name", AccessLevel.CITIZEN, "email", "pass", "username", 99);
        assertFalse(dbManager.update(user));

        assertEquals(12345, mockDatabase.getAllCopies().getFirst().getSize());
        Copy copy = copies.getFirst();
        copy.setSize(54321);
        assertTrue(dbManager.update(copy));
        assertEquals(54321, mockDatabase.getAllCopies().getFirst().getSize());
        copy = new Copy(99999, 99, "path", CopyType.INCREMENTAL);
        assertFalse(dbManager.update(copy));

        assertEquals("Department1", mockDatabase.getAllDepartments().getFirst().getName());
        Department department = departments.getFirst();
        department.setName("New Department");
        assertTrue(dbManager.update(department));
        assertEquals("New Department", mockDatabase.getAllDepartments().getFirst().getName());
        department = new Department("Name", "Address", 99, new ArrayList<>(), new ArrayList<>());
        assertFalse(dbManager.update(department));

        assertThrows(NullPointerException.class, () -> dbManager.save(null));
        assertThrows(ClassCastException.class, () -> dbManager.save((Model) new Object()));
        assertThrows(IllegalArgumentException.class, () -> dbManager.save(new TestModel()));
    }

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {

    }
}