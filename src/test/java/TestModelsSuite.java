import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"Model.Classes", "Model.Facades"})
@ExcludeTags({"exceptionHandlingTest", "Classes"})
public class TestModelsSuite {
}