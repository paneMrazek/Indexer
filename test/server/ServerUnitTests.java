package server;

import java.util.ArrayList;
import java.util.List;

import main.indexer.server.Server;
import main.indexer.server.importer.DataImporter;
import main.indexer.shared.communication.ClientCommunicator;
import main.indexer.shared.communication.params.DownloadBatch_Params;
import main.indexer.shared.communication.params.GetFields_Params;
import main.indexer.shared.communication.params.GetProjects_Params;
import main.indexer.shared.communication.params.GetSampleImage_Params;
import main.indexer.shared.communication.params.Search_Params;
import main.indexer.shared.communication.params.SubmitBatch_Params;
import main.indexer.shared.communication.params.ValidateUser_Params;
import main.indexer.shared.communication.results.DownloadBatch_Result;
import main.indexer.shared.communication.results.GetFields_Result;
import main.indexer.shared.communication.results.GetProjects_Result;
import main.indexer.shared.communication.results.GetSampleImage_Result;
import main.indexer.shared.communication.results.Search_Result;
import main.indexer.shared.communication.results.SubmitBatch_Result;
import main.indexer.shared.communication.results.ValidateUser_Result;

import org.junit.* ;

import static org.junit.Assert.* ;

public class ServerUnitTests {
	
	@BeforeClass
	public static void allTestsSetup(){
		Server.test();
	}
	
	@Before
	public void setup(){
		new DataImporter().importData("Records/Records.xml");
		ClientCommunicator.getInstance().setHost("localhost");
		ClientCommunicator.getInstance().setPort(39600);
	}
	
	@AfterClass
	public static void teardown() {
		Server.endTest();
	}
	
	@Test
	public void test_1() {
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}
	
	@Test
	public void testValidateUserValidUser(){
		ValidateUser_Params params = new ValidateUser_Params("test1","test1");
		ValidateUser_Result result = ClientCommunicator.getInstance().validateUser(params);
		Assert.assertFalse(result.isError());
		Assert.assertEquals("test1",result.getUser().getUserName());
		Assert.assertEquals("test1",result.getUser().getPassword());
		Assert.assertEquals("One",result.getUser().getLastName());
		Assert.assertEquals("Test",result.getUser().getFirstName());
		Assert.assertEquals("test1@gmail.com",result.getUser().getEmail());
		Assert.assertEquals(0,result.getUser().getIndexedRecords());
	}
	
	@Test
	public void testValidateUserInvalidUser(){
		ValidateUser_Params params = new ValidateUser_Params("test","test");
		ValidateUser_Result result = ClientCommunicator.getInstance().validateUser(params);
		Assert.assertFalse(result.isValid());
		Assert.assertNull(result.getUser());
	}
	
	@Test
	public void testGetProjects(){
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		GetProjects_Result result = ClientCommunicator.getInstance().getProjects(params);
		Assert.assertFalse(result.isError());
		Assert.assertEquals(3,result.getProjects().size());
		Assert.assertEquals("1890 Census",result.getProjects().get(0).getTitle());
		Assert.assertEquals("1900 Census",result.getProjects().get(1).getTitle());
		Assert.assertEquals("Draft Records",result.getProjects().get(2).getTitle());
	}
	
	@Test
	public void testGetSampleImage(){
		GetSampleImage_Params params = new GetSampleImage_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		params.setProjectId(1);
		GetSampleImage_Result result1 = ClientCommunicator.getInstance().getSampleImage(params);
		GetSampleImage_Result result2 = ClientCommunicator.getInstance().getSampleImage(params);
		Assert.assertFalse(result1.isError());
		Assert.assertFalse(result2.isError());
	}
	
	@Test
	public void testDownloadBatch(){
		DownloadBatch_Params params = new DownloadBatch_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		params.setProjectId(1);
		DownloadBatch_Result result1 = ClientCommunicator.getInstance().downloadBatch(params);
		DownloadBatch_Result result2 = ClientCommunicator.getInstance().downloadBatch(params);
		Assert.assertFalse(result1.isError());
		Assert.assertTrue(result2.isError());
		Assert.assertNotNull(result1.getBatch());
	}
	
	@Test
	public void testSubmitBatch(){
		DownloadBatch_Params downloadParams = new DownloadBatch_Params();
		downloadParams.setUserName("test1");
		downloadParams.setPassword("test1");
		downloadParams.setProjectId(1);
		DownloadBatch_Result result1 = ClientCommunicator.getInstance().downloadBatch(downloadParams);
		SubmitBatch_Params params = new SubmitBatch_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		params.setBatchId(result1.getBatch().getId());
		String[] recordValues = {"j","j","j","j"};
		String[] recordValues2 = {"k","k","k","k"};
		List<String[]> records = new ArrayList<>();
		records.add(recordValues);
		records.add(recordValues2);
		params.setRecordValues(records);
		SubmitBatch_Result result2 = ClientCommunicator.getInstance().submitBatch(params);
		DownloadBatch_Result result3 = ClientCommunicator.getInstance().downloadBatch(downloadParams);
		Assert.assertFalse(result1.isError());
		Assert.assertFalse(result2.isError());
		Assert.assertFalse(result3.isError());
	}
	
	@Test
	public void testSubmitBatchNotAssignedToUser(){
		SubmitBatch_Params params = new SubmitBatch_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		params.setBatchId(4);
		String[] recordValues = {"j","j","j","j"};
		String[] recordValues2 = {"k","k","k","k"};
		List<String[]> records = new ArrayList<>();
		records.add(recordValues);
		records.add(recordValues2);
		params.setRecordValues(records);
		SubmitBatch_Result result = ClientCommunicator.getInstance().submitBatch(params);
		Assert.assertTrue(result.isError());
	}
	
	@Test
	public void getAllFieldsTest(){
		GetFields_Params params = new GetFields_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		GetFields_Result result = ClientCommunicator.getInstance().getFields(params);
		Assert.assertFalse(result.isError());
		Assert.assertEquals(13,result.getFields().size());
	}
	
	@Test
	public void getFieldsForProjectTest(){
		GetFields_Params params = new GetFields_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		params.setProjectId(1);
		GetFields_Result result = ClientCommunicator.getInstance().getFields(params);
		Assert.assertFalse(result.isError());
		Assert.assertEquals(4,result.getFields().size());
	}
	
	@Test
	public void searchTestLowerCase(){
		Search_Params params = new Search_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		String[] fieldIds = {"10"};
		params.setFieldId(fieldIds);
		String[] searchValues = {"fox"};
		params.setSearchValues(searchValues);
		Search_Result result = ClientCommunicator.getInstance().search(params);
		Assert.assertFalse(result.isError());
		Assert.assertEquals(1,result.getResults().size());
	}
	
	@Test
	public void searchTestUpperCase(){
		Search_Params params = new Search_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		String[] fieldIds = {"10"};
		params.setFieldId(fieldIds);
		String[] searchValues = {"FOX"};
		params.setSearchValues(searchValues);
		Search_Result result = ClientCommunicator.getInstance().search(params);
		Assert.assertFalse(result.isError());
		Assert.assertEquals(1,result.getResults().size());
	}
	
	@Test
	public void searchTestDifferentCase(){
		Search_Params params = new Search_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		String[] fieldIds = {"10"};
		params.setFieldId(fieldIds);
		String[] searchValues = {"fOx"};
		params.setSearchValues(searchValues);
		Search_Result result = ClientCommunicator.getInstance().search(params);
		Assert.assertFalse(result.isError());
		Assert.assertEquals(1,result.getResults().size());
	}
	
	@Test
	public void searchNonWordTest(){
		Search_Params params = new Search_Params();
		params.setUserName("test1");
		params.setPassword("test1");
		String[] fieldIds = {"10"};
		params.setFieldId(fieldIds);
		String[] searchValues = {"jklejskdl"};
		params.setSearchValues(searchValues);
		Search_Result result = ClientCommunicator.getInstance().search(params);
		Assert.assertFalse(result.isError());
		Assert.assertEquals(0,result.getResults().size());
	}
	
	@Test
	public void testDownloadFile(){
		byte[] result = ClientCommunicator.getInstance().downloadFile("/test.txt");
		Assert.assertTrue(new String(result).equals("Hello"));
	}

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.ServerUnitTests",
				"indexer.server.daos.BatchDAOTest",
				"indexer.server.daos.FieldDAOTest",
				"indexer.server.daos.ProjectDAOTest",
				"indexer.server.daos.RecordDAOTest",
				"indexer.server.daos.UserDAOTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

