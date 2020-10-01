package com.kazfuku.aws;

import java.util.Map;

public class AuthorizerResponse {
    private String principalId;

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getPrincipalId() {
        return this.principalId;
    }

    private Map<String, Object> policyDocument;

    public void setPolicyDocument(Map<String, Object> policyDocument) {
        this.policyDocument = policyDocument;
    }

    public Map<String, Object> getPolicyDocument() {
        return this.policyDocument;
    }

    private Map<String, String> context;

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }
}
