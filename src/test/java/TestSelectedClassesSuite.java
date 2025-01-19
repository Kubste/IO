import Model.Classes.DBManagerTest;
import Model.Facades.MakeCopyFacadeTest;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({DBManagerTest.class, MakeCopyFacadeTest.class})
@ExcludeTags("exceptionHandlingTest")
public class TestSelectedClassesSuite {
}