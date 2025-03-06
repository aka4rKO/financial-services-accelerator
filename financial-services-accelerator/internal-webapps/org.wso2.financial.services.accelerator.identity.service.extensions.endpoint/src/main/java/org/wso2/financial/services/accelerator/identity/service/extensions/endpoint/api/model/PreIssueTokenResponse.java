package org.wso2.financial.services.accelerator.identity.service.extensions.endpoint.api.model;

import java.util.List;

public class PreIssueTokenResponse {

    private String actionStatus;
    private List<Operation> operations;

    // Getters and Setters
    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

}
