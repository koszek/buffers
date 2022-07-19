package model;

import annotation.GenerateHelper;

@GenerateHelper
public class ExecutionReport {

    private long id;

    private ExecutionType executionType;

    private double lastQty;

    private double lastPrice;

    private long transactTime;

    private String lastMarket;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExecutionType getExecutionType() {
        return executionType;
    }

    public void setExecutionType(ExecutionType executionType) {
        this.executionType = executionType;
    }

    public double getLastQty() {
        return lastQty;
    }

    public void setLastQty(double lastQty) {
        this.lastQty = lastQty;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public long getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(long transactTime) {
        this.transactTime = transactTime;
    }

    public String getLastMarket() {
        return lastMarket;
    }

    public void setLastMarket(String lastMarket) {
        this.lastMarket = lastMarket;
    }

    @Override
    public String toString() {
        return "Execution{" +
                "id=" + getId() +
                ", executionType=" + getExecutionType() +
                ", lastQty=" + getLastQty() +
                ", lastPrice=" + getLastPrice() +
                ", transactTime=" + getTransactTime() +
                '}';
    }
}
