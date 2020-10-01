package com.kazfuku.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PojoLambdaAuthorizer implements RequestHandler<Map<String, Object>, AuthorizerResponse> {
    @Override
    public AuthorizerResponse handleRequest(Map<String, Object> event, Context lambdaContext) {
        AuthorizerResponse response = new AuthorizerResponse();
        response.setPrincipalId("1234");

        Map<String, Object> policyDocument = new HashMap<>();
        policyDocument.put("Version", "2012-10-17");

        Map<String, String> statement = new HashMap<>();
        statement.put("Action", "execute-api:Invoke");
        statement.put("Effect", "Allow");
        statement.put("Resource", (String) event.get("methodArn"));
        policyDocument.put("Statement", Arrays.asList(statement));

        response.setPolicyDocument(policyDocument);

        Map<String, String> context = new HashMap<>();
        context.put("now", new Date().toString());
        response.setContext(context);

        return response;
    }
}
