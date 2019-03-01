package net.balsoftware.resources;

import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import jfxtras.icalendarfx.properties.component.time.DateTimeStart;
import net.balsoftware.bean.RRule;
import net.balsoftware.service.RRuleService;

/**
 * Servlet implementation
 */
@Path("/parse")
@Deprecated
public class RRuleResource {
	private static final String LS = "<br>";
	private RRuleService service = new RRuleService();
	
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.TEXT_PLAIN)
	public String doPost(RRule request)
	{
		DateTimeStart dateTimeStart = DateTimeStart.parse(request.getDtstartContent());
		int limit = request.getMaxRecurrences();
		String recurrences;
		try {
		RecurrenceRule rrule = RecurrenceRule.parse(request.getRruleContent());
		recurrences = rrule.getValue().streamRecurrences(dateTimeStart.getValue())
				.limit(limit)
				.map(t -> t.toString())
				.collect(Collectors.joining(","));
		} catch (Exception e)
		{
			recurrences = "Invalid";
		}
		service.addRRule(request);
		return recurrences;
		
//		List<String> recurrences;
//		try {
//		RecurrenceRule rrule = RecurrenceRule.parse(request.getRruleContent());
//		recurrences = rrule.getValue().streamRecurrences(dateTimeStart.getValue())
//				.limit(limit)
//				.map(t -> t.toString())
//				.collect(Collectors.toList());
//		} catch (Exception e)
//		{
//			recurrences = null;
//		}
//		System.out.println("created:" + request);
//		service.addRRule(request);
//		return recurrences;
	}
}
