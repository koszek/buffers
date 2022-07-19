package model;

import annotation.GenerateHelper;

@GenerateHelper
public class Order {

    private Side side;

    private OrderType orderType;

    private TimeInForce timeInForce;

    private Capacity capacity;

    private String securityId;

    private long instrumentId;

    private String portfolioCode;

    private long bookId;

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public TimeInForce getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(TimeInForce timeInForce) {
        this.timeInForce = timeInForce;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getPortfolioCode() {
        return portfolioCode;
    }

    public void setPortfolioCode(String portfolioCode) {
        this.portfolioCode = portfolioCode;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "side=" + getSide() +
                ", orderType=" + getOrderType() +
                ", timeInForce=" + getTimeInForce() +
                ", capacity=" + getCapacity() +
                ", instrumentId=" + getInstrumentId() +
                '}';
    }
}
