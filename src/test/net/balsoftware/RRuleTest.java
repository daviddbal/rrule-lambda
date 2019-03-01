package net.balsoftware;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.BeforeClass;
import org.junit.Test;

import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import jfxtras.icalendarfx.properties.component.time.DateTimeStart;
import net.balsoftware.bean.RRule;
import net.balsoftware.service.RRuleService;
import net.balsoftware.util.ConnectionHelper;

public class RRuleTest
{
	protected static Connection connection;
	protected static RRuleService service = new RRuleService();
	
    @BeforeClass
    public static void setupTableName() throws SQLException
    {
    	connection = ConnectionHelper.getConnection();
    }
    
    @Test
    public void canMakeConnection()
    {
    	assertTrue(connection != null);
    }
    
    @Test
    public void canAddRRule() throws SQLException
    {
    	RRule test = new RRule();
    	test.setRruleContent("RRULE:FREQ=WEEKLY");
    	test.setDtstartContent("DTSTART;VALUE=DATE:20170101");
    	test.setMaxRecurrences(10);
    	test.setCreated(LocalDateTime.now());
    	RRule addedRRule = service.addRRule(test);
    	assertEquals(addedRRule, test);
    }
    
    @Test
    public void canCreateUser()
    {
    	String user = "rruleuser";
    	String URL = "%";
    	String password = "rrulepassword";
    	String database = "rrule";
        final String createUser =
                "CREATE USER '" + user + "'@'" + URL + "' IDENTIFIED BY '" + password + "';";
        final String grantPrivilegesToUser =
                "GRANT INSERT, SELECT, DELETE  ON " + database + ".* TO '" + user + "'@'" + URL + "';";
        System.out.println(createUser);
        System.out.println(grantPrivilegesToUser);
        
        RecurrenceRule r = RecurrenceRule.parse("RRULE:FREQ=MONTHLY;INTERVAL=2;COUNT=10;BYDAY=1SU,-1SU");
        DateTimeStart d = DateTimeStart.parse("DTSTART:20170108T090746");
        r.getValue().streamRecurrences(d.getValue()).limit(10).forEach(System.out::println);
    }
    
    
}
