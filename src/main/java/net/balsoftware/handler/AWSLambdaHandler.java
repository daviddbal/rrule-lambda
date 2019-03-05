package net.balsoftware.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import jfxtras.icalendarfx.properties.component.time.DateTimeStart;
import net.balsoftware.bean.RRule;
import net.balsoftware.service.RRuleService;

public class AWSLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private RRuleService service = new RRuleService();
	
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Loading Java Lambda handler of Proxy");

        String rruleContent = null;
        int maxRecurrences = 0;
        String dtstartContent = null;
        DateTimeStart dateTimeStart = null;
        String ipAddress = null;
        String recurrences;

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try {
            Map<String, String> qps = event.getQueryStringParameters();
            if (qps != null) {
                if (qps.get("rrule") != null) {
                    rruleContent = qps.get("rrule");
                }
                if (qps.get("maxRecurrences") != null) {
                    maxRecurrences = Integer.parseInt(qps.get("maxRecurrences"));
                }
                dtstartContent = qps.get("dtstart");
              	dateTimeStart = DateTimeStart.parse(dtstartContent);
            }

            Map<String, String> hps = event.getHeaders();
            if (hps != null) {
                ipAddress = hps.get("X-Forwarded-For");
            }

    		try {
    			RecurrenceRule rrule = RecurrenceRule.parse(rruleContent);
    			recurrences = rrule.getValue().streamRecurrences(dateTimeStart.getValue())
    					.limit(maxRecurrences)
    					.map(t -> t.toString())
    					.collect(Collectors.joining(","));
    		} catch (Exception e)
    		{
    			recurrences = "Invalid";
    		}
            response.setHeaders(Collections.singletonMap("Content-Type", "text/plain"));
            response.setStatusCode(200);
            response.setBody(recurrences);

        } catch (Exception pex) {
            response.setStatusCode(400);

            Map<String, String> responseBody = Collections.singletonMap("message", pex.toString());
            String responseBodyString = new JSONObject(responseBody).toJSONString();
            response.setBody(responseBodyString);
        }
        
		// Store request in database if ip is not null
		try
		{
			if ((ipAddress != null) && ! ipAddress.equals("null"))
			{
				RRule r = new RRule(rruleContent, dtstartContent, maxRecurrences, ipAddress);
				service.addRRule(r);
			}
		} catch (Exception e)
		{
			// No database access - just display results
//			System.out.println("do nothing");
		}

        logger.log(response.toString());
        return response;
    }
}
