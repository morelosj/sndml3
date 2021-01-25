package servicenow.api;

import org.junit.Test;

import sndml.servicenow.NullWriter;
import sndml.servicenow.Session;
import sndml.servicenow.SoapKeySetTableReader;
import sndml.servicenow.Table;

public class KeyedTableReaderTest {

	Session session;
	
	public KeyedTableReaderTest() {
		TestingManager.setDefaultProfile(this.getClass());
		session = TestingManager.getProfile().getSession();
	}
	
	@Test
	public void test() throws Exception {
		Table table = session.table("incident");
		SoapKeySetTableReader reader = new SoapKeySetTableReader(table);
		reader.setQuery(table.getQuery(null));
		reader.setWriter(new NullWriter());
		reader.setPageSize(20).initialize();
		reader.call();
	}

}
