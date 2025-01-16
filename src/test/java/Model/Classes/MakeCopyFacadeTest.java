package Model.Classes;

import Model.Classes.*;
import Model.Enums.AccessLevel;
import Model.Enums.CopyType;
import Model.Facades.manageCopies;
import Model.Facades.manageDepartments;
import Model.Facades.exposeApplications;
import Provider.Facades.makeCopy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MakeCopyFacadeTest {

    private manageDepartments mockManageDepartments;
    private manageCopies mockManageCopies;
    private exposeApplications mockExposeApplications;


    private class MakeCopyConcrete extends makeCopy {}


    private MakeCopyConcrete makeCopyConcrete;

    private static DBManager dbManager;
    private static Database mockDatabase;
    private static ArrayList<User> users;
    private static ArrayList<Copy> copies;
    private ArrayList<Department> departments;

    private static MockedStatic<manageDepartments> mockedManageDepartments;


    @BeforeAll
    static void setupAll(){
        dbManager = DBManager.getInstance();
        mockedManageDepartments = Mockito.mockStatic(manageDepartments.class);
    }

    @BeforeEach
    void setupEach() {

        Model.resetIdCounters();
        copies = Data.getSampleCopies();
        mockDatabase = new Database();
        dbManager.setDatabase(mockDatabase);
        departments = Data.getSampleDepartments();
        users  = Data.getSampleUsers();

        Model.resetIdCounters();
        for(int i = 0; i < 3; i++) {
            dbManager.save(departments.get(i));
        }
        for(int i = 0; i < 3; i++) {
            dbManager.save(copies.get(i));
        }
        for(int i = 0; i < 3; i++) {
            dbManager.save(users.get(i));
        }

    }

    @Test
    void testCheckFreeSpace() {
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

        makeCopy.sendMail(userID, message);

        String output = outputStream.toString();
        assertTrue(output.contains(message), "The message should be printed to System.out.");

        System.setOut(originalSystemOut);
    }

    @Test
    @Order(1)
    void noAssignedDepartmentsTest(){

        mockedManageDepartments.when(() -> manageDepartments.getAssignedDepartments(1))
                .thenReturn(new ArrayList<>());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            makeCopy.startCreateCopy(1, 1, CopyType.FULL);
        });

        assertEquals("Provided department is not assigned to this user!", exception.getMessage());
    }

    @Test
    @Order(2)
    void noFreeSpaceTest() throws Exception {

        int userId = 1;
        int selectedDepartmentId = 3;
        CopyType selectedCopyType = CopyType.FULL;

        // Mock manageDepartments.getAssignedDepartments to return valid departments
        ArrayList<Department> assignedDepartments = new ArrayList<>();
        assignedDepartments.add(new Department("test","addr",1,new ArrayList<>(),new ArrayList<>()));
        mockedManageDepartments.when(() -> manageDepartments.getAssignedDepartments(userId))
                .thenReturn(assignedDepartments);

        mockStatic(makeCopy.class);
        when(makeCopy.checkFreeSpace()).thenReturn(false);
        when(makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType)).thenCallRealMethod();

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
        citizens.add(new User("citizne","11", AccessLevel.CITIZEN,"1@wp.pl","password","user1",3));
        ArrayList<User> officials = new ArrayList<>();
        officials.add(new User("citizne","11", AccessLevel.OFFICIAL,"1@wp.pl","password","user1",3));
        ArrayList<Application> applications = new ArrayList<>();
        applications.add(new Application(2,3,"description"));

        mockedManageDepartments.when(() -> manageDepartments.getAllCitizens(selectedDepartmentId))
                .thenReturn(citizens);
        mockedManageDepartments.when(() -> manageDepartments.getAllOfficials(selectedDepartmentId))
                .thenReturn(officials);
        mockStatic(exposeApplications.class);
        when(exposeApplications.getApplicationsFromDepartment(selectedDepartmentId))
                .thenReturn(applications);

        mockStatic(makeCopy.class);
        when(makeCopy.checkFreeSpace()).thenReturn(true);
        when(makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType)).thenCallRealMethod();
        boolean result = makeCopy.startCreateCopy(userId, selectedDepartmentId, selectedCopyType);

        assertTrue(result, "The method should return true on successful copy creation.");
    }



}
