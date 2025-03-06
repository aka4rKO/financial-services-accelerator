package org.wso2.financial.services.accelerator.identity.service.extensions.endpoint.api.model;

public class Operation {
    private String op;
    private String path;
    private Attribute attribute;

    // Getters and Setters
    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
