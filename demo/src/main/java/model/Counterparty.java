package model;

import annotation.GenerateHelper;

@GenerateHelper
public class Counterparty {

    private CounterpartyType counterpartyType;

    private String code;

    private CounterpartyCodeType counterpartyCodeType;

    private String executionId;

    private String orderId;

    public CounterpartyType getCounterpartyType() {
        return counterpartyType;
    }

    public void setCounterpartyType(CounterpartyType counterpartyType) {
        this.counterpartyType = counterpartyType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CounterpartyCodeType getCounterpartyCodeType() {
        return counterpartyCodeType;
    }

    public void setCounterpartyCodeType(CounterpartyCodeType counterpartyCodeType) {
        this.counterpartyCodeType = counterpartyCodeType;
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
}
