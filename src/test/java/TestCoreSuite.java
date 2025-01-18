import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.IncludeTags;

@Suite
@SelectPackages("Model.Classes")
@IncludeTags("Core")

public class TestCoreSuite {
}
