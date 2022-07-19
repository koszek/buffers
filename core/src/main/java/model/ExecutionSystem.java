package model;


import annotation.GenerateHelper;

@GenerateHelper
public class ExecutionSystem {

    private String name;

    private String executionId;

    private String orderId;

    private String clientName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "ExecutionSystem{" +
                "name='" + getName() + '\'' +
                ", executionId='" + getExecutionId() + '\'' +
                ", orderId='" + getOrderId() + '\'' +
                ", clientName='" + getClientName() + '\'' +
                '}';
    }
}
