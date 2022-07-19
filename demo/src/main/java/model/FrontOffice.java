package model;

import annotation.GenerateHelper;

@GenerateHelper
public class FrontOffice {

    private String executionId;

    private String orderId;

    private ExecutionSystem executionSystem;

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ExecutionSystem getExecutionSystem() {
        return executionSystem;
    }

    public void setExecutionSystem(ExecutionSystem executionSystem) {
        this.executionSystem = executionSystem;
    }

    @Override
    public String toString() {
        return "FrontOffice{" +
                "executionId='" + getExecutionId() + '\'' +
                ", orderId='" + getOrderId() + '\'' +
                ", executionSystem=" + getExecutionSystem() +
                '}';
    }
}
