package FitNesse;

import Model.Enums.AccessLevel;
import fit.ColumnFixture;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RejectApplicationProviderTest extends ColumnFixture {

    public int applicationID;
    public String rejectedDescription;
    public String accessLevel;
    private String output;

    public boolean rejectApplicationProvider() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {

            SetUp.applicationsProvider.setLoggedUserAccess(AccessLevel.valueOf(accessLevel));
            SetUp.applicationsProvider.rejectApplication(applicationID, rejectedDescription);

            output = outputStream.toString();

            return isAccessLevelCorrect() && isMailCorrect();


        } catch (IllegalAccessException | NullPointerException | IllegalArgumentException ex) {
            return false;
        } finally {
            System.setOut(originalSystemOut);
        }
    }

    public boolean isAccessLevelCorrect() {
        AccessLevel accessLevel = SetUp.applicationsProvider.getLoggedUserAccessLevel();
        return accessLevel == AccessLevel.OFFICIAL;
    }

    public boolean isMailCorrect() {
        return output.contains(rejectedDescription);
    }

    public boolean areLogsCorrect() {
        return output.contains("Odrzucenie wniosku zakonczone niepowodzeniem") || output.contains("Odrzucenie wniosku zakonczone powodzeniem");
    }
}
