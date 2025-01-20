package Model.Facades;

import Model.Classes.DBManager;
import Model.Data;
import Model.Classes.Database;
import Model.Classes.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("Facades")
@Tag("activeUser")
public class ActiveUserTest {

    private static ArrayList<User> users;

    @BeforeAll
    static void setupAll() {
        DBManager dbManager = DBManager.getInstance();
        Database mockDatabase = new Database();
        dbManager.setDatabase(mockDatabase);
        users = Data.getSampleUsers();

        for(int i = 0; i < 3; i++) {
            dbManager.save(users.get(i));
        }
    }

    @Order(1)
    @ParameterizedTest
    @CsvSource({"0", "1", "2"})
    void loginTest(int index) {
        User user = users.get(index);

        assertFalse(user.isActive());
        assertEquals(user, activeUser.login(user.getEmail(), user.getPassword()));
        assertTrue(user.isActive());
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({"0", "1", "2"})
    void logoutTest(int index) {
        User user = users.get(index);

        assertTrue(user.isActive());
        assertTrue(activeUser.logout(user.getID()));
        assertFalse(user.isActive());
    }

    @ParameterizedTest
    @CsvSource({"0", "1", "2"})
    void loginWrongEmailTest(int index) {
        User user = users.get(index);

        assertFalse(user.isActive());
        assertThrows(RuntimeException.class, () -> activeUser.login("testUsername", user.getPassword()));
    }

    @ParameterizedTest
    @CsvSource({"0", "1", "2"})
    void loginWrongPasswordTest(int index) {
        User user = users.get(index);

        assertFalse(user.isActive());
        assertThrows(RuntimeException.class, () -> activeUser.login(user.getEmail(), "testPassword"));
    }
}