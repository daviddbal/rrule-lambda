package net.balsoftware.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class AWSLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log("Loading Java Lambda handler of Proxy");

        JSONParser parser = new JSONParser();

        String name = "you";
        String city = "World";
        String time = "day";
        String day = null;

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        try {
            Map<String, String> qps = event.getQueryStringParameters();
            if (qps != null) {
                if (qps.get("name") != null) {
                    name = qps.get("name");
                }
            }
            Map<String, String> pps = event.getPathParameters();
            if (pps != null) {
                if (pps.get("proxy") != null) {
                    city = pps.get("proxy");
                }
            }

            Map<String, String> hps = event.getHeaders();
            if (hps != null) {
                day = hps.get("Day");
            }

            String bodyStr = event.getBody();
            if (bodyStr != null) {
                JSONObject body;

                body = (JSONObject) parser.parse(bodyStr);

                if (body.get("time") != null) {
                    time = (String) body.get("time");
                }
            }

            String greeting = "Good " + time + ", " + name + " of " + city + ".";
            if (day != null && day != "")
                greeting += " Happy " + day + "!";

            response.setHeaders(Collections.singletonMap("x-custom-header", "my custom header value"));
            response.setStatusCode(200);

            Map<String, String> responseBody = new HashMap<String, String>();
            responseBody.put("input", event.toString());
            responseBody.put("message", greeting);
            String responseBodyString = new JSONObject(responseBody).toJSONString();

            response.setBody(responseBodyString);

        } catch (ParseException pex) {
            response.setStatusCode(400);

            Map<String, String> responseBody = Collections.singletonMap("message", pex.toString());
            String responseBodyString = new JSONObject(responseBody).toJSONString();
            response.setBody(responseBodyString);
        }

        logger.log(response.toString());
        return response;
    }
}
