package model;

import annotation.GenerateHelper;

@GenerateHelper
public class FrontOffice {

    private String executionId;

    private String orderId;

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
        return "FrontOffice{" +
                "executionId='" + getExecutionId() + '\'' +
                ", orderId='" + getOrderId() + '\'' +
                '}';
    }
}
