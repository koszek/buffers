package model;

import annotation.GenerateDeserializer;
import annotation.GenerateSerializer;

@GenerateSerializer
@GenerateDeserializer
public class UsStreetExecutionReport {

    private FrontOffice frontOffice;

    private Order order;

    private Counterparty counterparty;

    private ExecutionReport executionReport;

    public FrontOffice getFrontOffice() {
        return frontOffice;
    }

    public void setFrontOffice(FrontOffice frontOffice) {
        this.frontOffice = frontOffice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ExecutionReport getExecutionReport() {
        return executionReport;
    }

    public void setExecutionReport(ExecutionReport executionReport) {
        this.executionReport = executionReport;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    @Override
    public String toString() {
        return "UsStreetExecution{" +
                "frontOffice=" + getFrontOffice() +
                ", order=" + getOrder() +
                ", executionReport=" + getExecutionReport() +
                '}';
    }
}
