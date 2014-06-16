package client;

import main.indexer.client.models.QualityChecker;
import main.indexer.server.Server;
import org.junit.*;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ClientUnitTests {

    private static final String KNOWN_GENDER_DATA = "http://localhost:39600/knowndata/genders.txt";
    private static final String KNOWN_FIRSTNAME_DATA = "http://localhost:39600/knowndata/1890_first_names.txt";

    QualityChecker checker;

    @BeforeClass
    public static void init(){
        Server.test();
    }

    @AfterClass
    public static void teardown(){
        Server.endTest();
    }

    @Before
    public void setup(){
        checker = new QualityChecker();
        checker.fieldChange(KNOWN_GENDER_DATA);
        checker.fieldChange(KNOWN_FIRSTNAME_DATA);
    }
	
	@Test
	public void validGenderDataTest(){
        List<String> suggestions = checker.findSuggestions("L",KNOWN_GENDER_DATA);
        Assert.assertEquals(2,suggestions.size());
        Assert.assertTrue(suggestions.get(0).equals("f"));
        Assert.assertTrue(suggestions.get(1).equals("m"));
	}

    @Test
    public void invalidGenderDataTest(){
        List<String> suggestions = checker.findSuggestions("Lazy",KNOWN_GENDER_DATA);
        Assert.assertEquals(0, suggestions.size());
    }

    @Test
    public void checkIfSortedTest(){
        List<String> suggestions = checker.findSuggestions("ez",KNOWN_FIRSTNAME_DATA);
        for(int i = 1; i < suggestions.size(); i++){
            //Checks to see if it's in alphabetical order and that there are no duplicates
            String compareTo = suggestions.get(i-1);
            String compareWith = suggestions.get(i);
            Assert.assertTrue(compareTo.compareTo(compareWith) < 0);
        }
    }

    @Test
    public void checkIfErrorOnPunctuationInput(){
        List<String> suggestions = checker.findSuggestions(".",KNOWN_GENDER_DATA);
        Assert.assertEquals(2,suggestions.size());
        Assert.assertTrue(suggestions.get(0).equals("f"));
        Assert.assertTrue(suggestions.get(1).equals("m"));
    }

    @Test
    public void checkIfErrorOnBlankInput(){
        List<String> suggestions = checker.findSuggestions("",KNOWN_GENDER_DATA);
        Assert.assertEquals(2,suggestions.size());
        Assert.assertTrue(suggestions.get(0).equals("f"));
        Assert.assertTrue(suggestions.get(1).equals("m"));
    }

    @Test
    public void checkSpaceInput(){
        List<String> suggestions = checker.findSuggestions(" ",KNOWN_GENDER_DATA);
        Assert.assertEquals(2,suggestions.size());
        Assert.assertTrue(suggestions.get(0).equals("f"));
        Assert.assertTrue(suggestions.get(1).equals("m"));
    }

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"client.ClientUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

