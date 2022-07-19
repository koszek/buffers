package model;

import annotation.GenerateHelper;

@GenerateHelper
public class SourceSystem {

    private String id;

    private String executionId;

    private String orderId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "SourceSystem{" +
                "id='" + getId() + '\'' +
                ", executionId='" + getExecutionId() + '\'' +
                ", orderId='" + getOrderId() + '\'' +
                '}';
    }
}
