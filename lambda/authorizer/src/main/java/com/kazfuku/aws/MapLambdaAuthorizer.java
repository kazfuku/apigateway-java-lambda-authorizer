package com.kazfuku.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapLambdaAuthorizer implements RequestHandler<Map<String, Object>, Map<String, Object>> {
    @Override
    public Map<String, Object> handleRequest(Map<String, Object> event, Context lambdaContext) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("principalId", "1234");

        Map<String, Object> policyDocument = new HashMap<String, Object>();
        policyDocument.put("Version", "2012-10-17");

        Map<String, String> statement = new HashMap<>();
        statement.put("Action", "execute-api:Invoke");
        statement.put("Effect", "Allow");
        statement.put("Resource", (String) event.get("methodArn"));
        policyDocument.put("Statement", Arrays.asList(statement));

        response.put("policyDocument", policyDocument);

        Map<String, String> context = new HashMap<String, String>();
        context.put("now", new Date().toString());
        response.put("context", context);

        return response;
    }
}
