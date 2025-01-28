package FitNesse;

import Model.Enums.AccessLevel;
import fit.ColumnFixture;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RejectApplicationProviderTest extends ColumnFixture {

    public int applicationID;
    public String rejectedDescription;
    public String accessLevel;
    private String mailOutput;
    private String logOutput;

    public boolean rejectApplicationProvider() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));

        Logger logger = Logger.getLogger(SetUp.applicationsProvider.getClass().getName());
        ByteArrayOutputStream logStream = new ByteArrayOutputStream();
        Handler customHandler = new ConsoleHandler() {
            @Override
            public void publish(java.util.logging.LogRecord record) {
                try {
                    logStream.write((record.getLevel() + ": " + record.getMessage() + "\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        logger.setLevel(Level.ALL);
        logger.addHandler(customHandler);

        try {
            SetUp.applicationsProvider.setLoggedUserAccess(AccessLevel.valueOf(accessLevel));
            SetUp.applicationsProvider.rejectApplication(applicationID, rejectedDescription);
            mailOutput = outputStream.toString();
            logOutput = logStream.toString();

            return isAccessLevelCorrect() && isMailCorrect() && areLogsCorrect();

        } catch (IllegalAccessException | NullPointerException | IllegalArgumentException ex) {
            mailOutput = "Error";
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
        if(mailOutput == null || rejectedDescription == null) return false;
        return mailOutput.contains(rejectedDescription) && !mailOutput.isEmpty();
    }

    public boolean areLogsCorrect() {
        return logOutput.contains("Odrzucenie wniosku zakonczone niepowodzeniem") || logOutput.contains("Odrzucenie wniosku zakonczone powodzeniem");
    }
}