package model;

import annotation.GenerateHelper;

@GenerateHelper
public class Exchange {

    private String name;

    private String executionId;

    private String orderId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Exchange{" +
                "name='" + getName() + '\'' +
                ", executionId='" + getExecutionId() + '\'' +
                ", orderId='" + getOrderId() + '\'' +
                '}';
    }
}
