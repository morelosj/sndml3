package servicenow.datamart;

import servicenow.api.*;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class PruneTest {

	@Parameters(name = "{index}:{0}")
	public static TestingProfile[] profiles() {
		return TestingManager.getDatamartProfiles();
	}

	@AfterClass
	public static void clear() throws Exception {
		TestingManager.clearAll();
	}
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PruneTest(TestingProfile profile) throws Exception {
		TestingManager.setProfile(this.getClass(), profile);
	}
	
	@Test
	public void testPrune() throws Exception {
		TestingManager.bannerStart("testPrune");
		Session session = TestingManager.getProfile().getSession();
		DateTime t0 = DateTime.now();
		Table tbl = session.table("incident");
		TableAPI api = tbl.api();
		TestingManager.banner(logger, "Insert");
	    FieldValues values = new FieldValues();
	    String descr1 = "This is a test " + t0.toString();
	    values.put("short_description", descr1);
	    values.put("cmdb_ci",  TestingManager.getProperty("some_ci"));
	    Key key = api.insertRecord(values).getKey();
	    assertNotNull(api.getRecord(key));
	    TestingManager.sleep(2);
	    TestingManager.banner(logger,  "Delete");
	    api.deleteRecord(key);
		assertNull(api.getRecord(key));
	    TestingManager.sleep(2);
	    TestingManager.banner(logger,  "Prune");
		JobConfig config = new JobConfig(tbl);
		config.setAction(LoaderAction.PRUNE);
		config.setSince(t0);
		logger.info(Log.TEST, "PRUNE " + config.getName());
		LoaderJob loader = new LoaderJob(config, null);
		loader.call();
	}

}
