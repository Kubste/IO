package Model.Classes;

import Model.Enums.AccessLevel;
import Model.Enums.ApplicationStatus;
import Model.Enums.CopyType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("Core")
@Tag("Models")
public class CopyTest {

    private static DBManager dbManager;
    private static Database mockDatabase;
    private static ArrayList<Copy> copies;
    private ArrayList<Department> departments;

    @BeforeAll
    static void setupAll(){
        dbManager = DBManager.getInstance();
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

    }

    static Stream<Integer> indexProvider() {
        return Stream.of(0, 1, 2); }

    @ParameterizedTest()
    @MethodSource("indexProvider")
    void testProperSave(int departmentIndex){

        Copy newCopy = Mockito.spy( new Copy(1000, departmentIndex,"C:\\User\\copies\\copy1.txt", CopyType.FULL));
        LocalDateTime testTime = LocalDateTime.of(2025, 1, 1, 12, 0);

        Mockito.doReturn(testTime).when(newCopy).getCurrentTime();

        newCopy.save();
        assertEquals(testTime, newCopy.getCreated_at());
        assertTrue(mockDatabase.getAllCopies().contains(newCopy));

        int max_id = mockDatabase.getAllCopies().stream().map(Copy::getId).max(Comparator.naturalOrder()).get().intValue();
        assertEquals(max_id, newCopy.getId());

    }



    @Order(1)
    @Test
    void testProperSetUpEach() {
        for(int i = 0; i < 3; i++) {
            assertTrue(mockDatabase.getAllDepartments().contains(departments.get(i)));
            assertTrue(mockDatabase.getAllCopies().contains(copies.get(i)));
        }
    }

}