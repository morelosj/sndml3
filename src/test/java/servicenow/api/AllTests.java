package servicenow.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	DateTimeTest.class, 
	ParametersTest.class,
	FieldNamesTest.class,
	InstanceTest.class, 
	SessionIDTest.class,
	SessionVerificationTest.class,
	TableWSDLTest.class,
	TableSchemaTest.class,
	RestTableReaderTest.class,
	SetFieldsTest.class,
	CRUDTest.class,
	DatePartTest.class,
	})

public class AllTests {

}
