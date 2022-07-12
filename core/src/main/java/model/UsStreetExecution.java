package model;

import annotation.GenerateDeserializer;
import annotation.GenerateSerializer;

@GenerateSerializer
@GenerateDeserializer
public class UsStreetExecution {

    private FrontOffice frontOffice;

    private SourceSystem sourceSystem;

    private Order order;

    private Exchange exchange;

    private Execution execution;

    public FrontOffice getFrontOffice() {
        return frontOffice;
    }

    public void setFrontOffice(FrontOffice frontOffice) {
        this.frontOffice = frontOffice;
    }

    public SourceSystem getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(SourceSystem sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    @Override
    public String toString() {
        return "UsStreetExecution{" +
                "frontOffice=" + getFrontOffice() +
                ", sourceSystem=" + getSourceSystem() +
                ", order=" + getOrder() +
                ", exchange=" + getExchange() +
                ", execution=" + getExecution() +
                '}';
    }
}
